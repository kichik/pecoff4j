/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j;

import com.kichik.pecoff4j.constant.ImageDataDirectoryType;
import com.kichik.pecoff4j.io.DataEntry;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;

import java.io.IOException;

public class PE {
	private DOSHeader dosHeader;
	private DOSStub stub;
	private PESignature signature;
	private COFFHeader coffHeader;
	private OptionalHeader optionalHeader;
	private ImageData imageData;
	private SectionTable sectionTable;
	private boolean is64bit;

	public static PE read(IDataReader dr) throws IOException {
		PE pe = new PE();
		pe.setDosHeader(DOSHeader.read(dr));

		// Check if we have an old file type
		if (pe.getDosHeader().getAddressOfNewExeHeader() == 0
				|| pe.getDosHeader().getAddressOfNewExeHeader() > 8192) {
			return pe;
		}

		pe.setStub(DOSStub.read(pe.getDosHeader(), dr));
		pe.setSignature(PESignature.read(dr));

		// Check signature to ensure we have a pe/coff file
		if (!pe.getSignature().isValid()) {
			return pe;
		}

		pe.setCoffHeader(COFFHeader.read(dr));
		pe.setOptionalHeader(OptionalHeader.read(dr));
		pe.setSectionTable(SectionTable.read(pe, dr));

		pe.set64(pe.getOptionalHeader().isPE32plus());

		// Now read the rest of the file
		DataEntry entry = null;
		while ((entry = pe.findNextEntry(dr.getPosition())) != null) {
			if (entry.isSection) {
				SectionData.read(pe, entry, dr);
			} else if (entry.isDebugRawData) {
				readDebugRawData(pe, entry, dr);
			} else {
				pe.getImageData().read(pe, entry, dr);
			}
		}

		// Read any trailing data
		byte[] tb = dr.readAll();
		if (tb.length > 0) {
			pe.getImageData().setTrailingData(tb);
		}

		return pe;
	}

	public DataEntry findNextEntry(int pos) {
		DataEntry de = new DataEntry();

		// Check sections first
		int ns = getCoffHeader().getNumberOfSections();
		for (int i = 0; i < ns; i++) {
			SectionHeader sh = getSectionTable().getHeader(i);
			if (sh.getSizeOfRawData() > 0
					&& sh.getPointerToRawData() >= pos
					&& (de.pointer == 0 || sh.getPointerToRawData() < de.pointer)) {
				de.pointer = sh.getPointerToRawData();
				de.index = i;
				de.isSection = true;
			}
		}

		// Now check image data directories
		RVAConverter rvc = getSectionTable().getRVAConverter();
		int dc = getOptionalHeader().getDataDirectoryCount();
		for (int i = 0; i < dc; i++) {
			ImageDataDirectory idd = getOptionalHeader().getDataDirectory(i);
			if (idd.getSize() > 0) {
				int prd = idd.getVirtualAddress();
				// Assume certificate live outside section ?
				if (i != ImageDataDirectoryType.CERTIFICATE_TABLE
						&& isInsideSection(idd)) {
					prd = rvc.convertVirtualAddressToRawDataPointer(idd
							.getVirtualAddress());
				}
				if (prd >= pos && (de.pointer == 0 || prd < de.pointer)) {
					de.pointer = prd;
					de.index = i;
					de.isSection = false;
				}
			}
		}

		// Check debug
		ImageData id = getImageData();
		DebugDirectory dd = null;
		if (id != null)
			dd = id.getDebug();
		if (dd != null) {
			int prd = dd.getPointerToRawData();
			if (prd >= pos && (de.pointer == 0 || prd < de.pointer)) {
				de.pointer = prd;
				de.index = -1;
				de.isDebugRawData = true;
				de.isSection = false;
				de.baseAddress = prd;
			}
		}

		if (de.pointer == 0)
			return null;

		return de;
	}

	private boolean isInsideSection(ImageDataDirectory idd) {
		int prd = idd.getVirtualAddress();
		int pex = prd + idd.getSize();
		SectionTable st = getSectionTable();
		int ns = st.getNumberOfSections();
		for (int i = 0; i < ns; i++) {
			SectionHeader sh = st.getHeader(i);
			int vad = sh.getVirtualAddress();
			int vex = vad + sh.getVirtualSize();
			if (prd >= vad && prd < vex && pex <= vex)
				return true;
		}
		return false;
	}

	private static void readDebugRawData(PE pe, DataEntry entry, IDataReader dr)
			throws IOException {
		// Read any preamble data
		ImageData id = pe.getImageData();
		byte[] pa = dr.readNonZeroOrNull(entry.pointer);
		if (pa != null)
			id.setDebugRawDataPreamble(pa);
		DebugDirectory dd = id.getDebug();
		byte[] b = new byte[dd.getSizeOfData()];
		dr.read(b);
		id.setDebugRawData(b);
	}

	public void write(IDataWriter dw) throws IOException {
		getDosHeader().write(dw);
		getStub().write(dw);
		getSignature().write(dw);
		getCoffHeader().write(dw);
		getOptionalHeader().write(dw);
		getSectionTable().write(dw);

		// Now write out the rest
		DataEntry entry = null;
		while ((entry = findNextEntry(dw.getPosition())) != null) {
			if (entry.isSection) {
				writeSection(entry, dw);
			} else if (entry.isDebugRawData) {
				writeDebugRawData(entry, dw);
			} else {
				writeImageData(entry, dw);
			}
		}

		// Dump out any trailing data - TODO find out what this is
		byte[] tb = getImageData().getTrailingData();
		if (tb != null)
			dw.writeBytes(tb);
	}

	private void writeImageData(DataEntry entry, IDataWriter dw)
			throws IOException {
		ImageDataDirectory idd = getOptionalHeader().getDataDirectory(
				entry.index);
		RVAConverter rvc = getSectionTable().getRVAConverter();
		int prd = idd.getVirtualAddress();
		if (entry.index != ImageDataDirectoryType.CERTIFICATE_TABLE)
			prd = rvc.convertVirtualAddressToRawDataPointer(idd
					.getVirtualAddress());
		if (prd > dw.getPosition()) {
			byte[] pa = getImageData().getPreamble(entry.index);
			if (pa != null)
				dw.writeBytes(pa);
			else
				dw.writeByte(0, prd - dw.getPosition());
		}

		ImageData id = getImageData();

		switch (entry.index) {
			case ImageDataDirectoryType.EXPORT_TABLE:
				dw.writeBytes(id.getExportTable().get());
				break;
			case ImageDataDirectoryType.IMPORT_TABLE:
				dw.writeBytes(id.getImportTable().get());
				break;
			case ImageDataDirectoryType.RESOURCE_TABLE:
				dw.writeBytes(id.getResourceTable().get());
				break;
			case ImageDataDirectoryType.EXCEPTION_TABLE:
				dw.writeBytes(id.getExceptionTable());
				break;
			case ImageDataDirectoryType.CERTIFICATE_TABLE:
				dw.writeBytes(id.getCertificateTable().get());
				break;
			case ImageDataDirectoryType.BASE_RELOCATION_TABLE:
				dw.writeBytes(id.getBaseRelocationTable());
				break;
			case ImageDataDirectoryType.DEBUG:
				dw.writeBytes(id.getDebug().get());
				break;
			case ImageDataDirectoryType.ARCHITECTURE:
				dw.writeBytes(id.getArchitecture());
				break;
			case ImageDataDirectoryType.GLOBAL_PTR:
				dw.writeBytes(id.getGlobalPtr());
				break;
			case ImageDataDirectoryType.TLS_TABLE:
				dw.writeBytes(id.getTlsTable());
				break;
			case ImageDataDirectoryType.LOAD_CONFIG_TABLE:
				break;
			case ImageDataDirectoryType.BOUND_IMPORT:
				id.getBoundImports().write(this, dw);
				break;
			case ImageDataDirectoryType.IAT:
				dw.writeBytes(id.getIat());
				break;
			case ImageDataDirectoryType.DELAY_IMPORT_DESCRIPTOR:
				dw.writeBytes(id.getDelayImportDescriptor());
				break;
			case ImageDataDirectoryType.CLR_RUNTIME_HEADER:
				dw.writeBytes(id.getClrRuntimeHeader().get());
				break;
			case ImageDataDirectoryType.RESERVED:
				dw.writeBytes(id.getReserved());
				break;
		}
	}

	private void writeDebugRawData(DataEntry entry, IDataWriter dw)
			throws IOException {
		if (entry.pointer > dw.getPosition()) {
			byte[] pa = getImageData().getDebugRawDataPreamble();
			if (pa != null)
				dw.writeBytes(pa);
			else
				dw.writeByte(0, entry.pointer - dw.getPosition());
		}
		dw.writeBytes(getImageData().getDebugRawData());
	}

	private void writeSection(DataEntry entry, IDataWriter dw)
			throws IOException {
		SectionTable st = getSectionTable();
		SectionHeader sh = st.getHeader(entry.index);
		SectionData sd = st.getSection(entry.index);
		int prd = sh.getPointerToRawData();
		if (prd > dw.getPosition()) {
			byte[] pa = sd.getPreamble();
			if (pa != null) {
				dw.writeBytes(pa);
			} else {
				dw.writeByte(0, prd - dw.getPosition());
			}
		}

		byte[] b = sd.getData();
		dw.writeBytes(b);
	}

	public DOSHeader getDosHeader() {
		return dosHeader;
	}

	public DOSStub getStub() {
		return stub;
	}

	public PESignature getSignature() {
		return signature;
	}

	public COFFHeader getCoffHeader() {
		return coffHeader;
	}

	public OptionalHeader getOptionalHeader() {
		return optionalHeader;
	}
	
	public boolean is64()
    {
        return is64bit;
    }

	public SectionTable getSectionTable() {
		return sectionTable;
	}

	public void setDosHeader(DOSHeader dosHeader) {
		this.dosHeader = dosHeader;
	}

	public void setStub(DOSStub stub) {
		this.stub = stub;
	}

	public void setSignature(PESignature signature) {
		this.signature = signature;
	}

	public void setCoffHeader(COFFHeader coffHeader) {
		this.coffHeader = coffHeader;
	}

	public void setOptionalHeader(OptionalHeader optionalHeader) {
		this.optionalHeader = optionalHeader;
	}
	
	public void set64(boolean is64bit)
    {
        this.is64bit = is64bit;
    }

	public void setSectionTable(SectionTable sectionTable) {
		this.sectionTable = sectionTable;
	}

	public ImageData getImageData() {
		if (imageData == null)
			imageData = new ImageData();
		return imageData;
	}

	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
	}
}
