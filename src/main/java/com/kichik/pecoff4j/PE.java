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
import com.kichik.pecoff4j.constant.SectionFlag;
import com.kichik.pecoff4j.io.DataEntry;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.DataWriter;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.PaddingType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.kichik.pecoff4j.util.Alignment.align;

public class PE implements WritableStructure {
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

	/**
	 * Rebuild the resources section and update the various size fields and the PE checksum.
	 */
	public int rebuild(PaddingType paddingType) {
		// rebuild resource table data
		ResourceDirectory resourceTable = imageData.getResourceTable();
		if (resourceTable != null) {
			ImageDataDirectory resTable = optionalHeader.getDataDirectory(ImageDataDirectoryType.RESOURCE_TABLE);
			int resBaseAddress = resTable.getVirtualAddress();

			int resSize = resourceTable.rebuild(resBaseAddress);
			byte[] resData = resourceTable.toByteArray(paddingType, optionalHeader.getFileAlignment());

			// update resource data section
			for (int i = 0; i < sectionTable.getNumberOfSections(); i++) {
				SectionHeader header = sectionTable.getHeader(i);
				SectionData section = sectionTable.getSection(i);
				if (header.getVirtualAddress() == resTable.getVirtualAddress()) {
					if (header.getVirtualSize() == resTable.getSize()) {
						header.setVirtualSize(resSize);
						header.setSizeOfRawData(align(resSize, optionalHeader.getFileAlignment()));
						resTable.setSize(resSize);
						section.setData(resData);
						break;
					} else {
						throw new UnsupportedOperationException("Partial update of section is not supported");
					}
				}
			}

			// update virtual address and raw data pointer
			int virtualAddress = 0;
			int fileAddress = 0;
			for (SectionHeader header : sectionTable.getHeadersPointerSorted()) {
				if (header.getVirtualAddress() > resTable.getVirtualAddress()) {
					for (ImageDataDirectory dataDirectory : optionalHeader.getDataDirectories()) {
						if (dataDirectory.getVirtualAddress() == header.getVirtualAddress()) {
							dataDirectory.setVirtualAddress(virtualAddress);
							break;
						}
					}
					header.setVirtualAddress(virtualAddress);
					header.setPointerToRawData(fileAddress);
				}
				virtualAddress = align(header.getVirtualAddress() + header.getVirtualSize(), optionalHeader.getSectionAlignment());
				fileAddress = align(header.getPointerToRawData() + header.getSizeOfRawData(), optionalHeader.getFileAlignment());
			}
		}

		// update size fields
		optionalHeader.setSizeOfInitializedData(
				calculateSizeOfInitializedData(sectionTable, optionalHeader.getFileAlignment()));
		optionalHeader.setSizeOfImage(calculateSizeOfImage(sectionTable, optionalHeader.getSectionAlignment()));

		int length = updateChecksum(paddingType);

		return length;
	}

	private int calculateSizeOfInitializedData(SectionTable sectionTable, int fileAlignment) {
		int sum = 0;
		for (int i = 0; i < sectionTable.getNumberOfSections(); i++) {
			SectionHeader header = sectionTable.getHeader(i);
			if ((header.getCharacteristics() & SectionFlag.IMAGE_SCN_CNT_INITIALIZED_DATA) != 0) {
				sum += Math.max(header.getSizeOfRawData(), align(header.getVirtualSize(), fileAlignment));
			}
		}
		return sum;
	}

	private int calculateSizeOfImage(SectionTable sectionTable, int sectionAlignment) {
		// find the highest address used in any section (virtual address space)
		int max = 0;
		for (int i = 0; i < sectionTable.getNumberOfSections(); i++) {
			SectionHeader header = sectionTable.getHeader(i);
			max = Math.max(max, header.getVirtualAddress() + header.getVirtualSize());
		}
		return align(max, sectionAlignment);
	}

	/**
	 * Update Checksum and return the length of the executable file.
	 */
	private int updateChecksum(PaddingType paddingType) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataWriter dataWriter = new DataWriter(baos);
			dataWriter.setPaddingMode(paddingType);
			write(dataWriter);
			dataWriter.close();

			int length = baos.size();
			// align to DWORD
			baos.write(new byte[(4 - (baos.size() % 4)) % 4]);
			byte[] data = baos.toByteArray();

			long checksum = 0;

			try (DataReader reader = new DataReader(data)) {
				while (reader.hasMore()) {
					// skip data where checksum is stored
					if (reader.getPosition() == (dosHeader.getAddressOfNewExeHeader() + 0x58)) {
						reader.readDoubleWord();
						continue;
					}
					long doubleWord = ((long) reader.readDoubleWord()) & 0xffffffffL;
					checksum = (checksum & 0xffffffffL) + (doubleWord & 0xffffffffL);

					if (checksum >= 1L << 32) {
						checksum = (checksum & 0xffffffffL) + (checksum >> 32);
					}
				}
			}

			checksum = (checksum & 0xffff) + (checksum >> 16);
			checksum = checksum + (checksum >> 16);
			checksum = checksum & 0xffff;
			checksum += length; // must be original data length

			optionalHeader.setCheckSum((int) checksum);
			return length;
		} catch (IOException e) {
			// cannot happen due to in-memory implementation of writer and reader
			return 0;
		}
	}

	@Override
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
