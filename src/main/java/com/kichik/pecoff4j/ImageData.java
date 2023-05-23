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
import com.kichik.pecoff4j.io.ByteArrayDataReader;
import com.kichik.pecoff4j.io.DataEntry;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.IntMap;

import java.io.IOException;

public class ImageData {
	private byte[] headerPadding; // TODO find out what this is

	// Data members that correspond to image data directories
	private ExportDirectory exportTable;
	private ImportDirectory importTable;
	private ResourceDirectory resourceTable;
	private byte[] exceptionTable;
	private AttributeCertificateTable certificateTable;
	private byte[] baseRelocationTable;
	private DebugDirectory debug;
	private byte[] architecture;
	private byte[] globalPtr;
	private byte[] tlsTable;
	private LoadConfigDirectory loadConfigTable;
	private BoundImportDirectoryTable boundImports;
	private byte[] iat;
	private byte[] delayImportDescriptor;
	private CLRRuntimeHeader clrRuntimeHeader;
	private byte[] reserved;

	// Debug type-specific data
	private byte[] debugRawDataPreamble;
	private byte[] debugRawData;

	// Any image data preambes
	private IntMap preambles = new IntMap();

	// Any trailing data
	private byte[] trailingData;

	public void read(PE pe, DataEntry entry, IDataReader dr)
			throws IOException {

		// Read any preamble data
		byte[] pa = dr.readNonZeroOrNull(entry.pointer);
		if (pa != null)
			put(entry.index, pa);

		// Read the image data
		ImageDataDirectory idd = pe.getOptionalHeader().getDataDirectory(
				entry.index);
		byte[] b = new byte[idd.getSize()];
		dr.read(b);

		switch (entry.index) {
			case ImageDataDirectoryType.EXPORT_TABLE:
				setExportTable(ExportDirectory.read(b));
				break;
			case ImageDataDirectoryType.IMPORT_TABLE:
				setImportTable(ImportDirectory.read(b, entry.baseAddress));
				break;
			case ImageDataDirectoryType.RESOURCE_TABLE:
				setResourceTable(ResourceDirectory.read(new ByteArrayDataReader(b), entry.baseAddress));
				break;
			case ImageDataDirectoryType.EXCEPTION_TABLE:
				setExceptionTable(b);
				break;
			case ImageDataDirectoryType.CERTIFICATE_TABLE:
				setCertificateTable(AttributeCertificateTable.read(b));
				break;
			case ImageDataDirectoryType.BASE_RELOCATION_TABLE:
				setBaseRelocationTable(b);
				break;
			case ImageDataDirectoryType.DEBUG:
				setDebug(DebugDirectory.read(b));
				break;
			case ImageDataDirectoryType.ARCHITECTURE:
				setArchitecture(b);
				break;
			case ImageDataDirectoryType.GLOBAL_PTR:
				setGlobalPtr(b);
				break;
			case ImageDataDirectoryType.TLS_TABLE:
				setTlsTable(b);
				break;
			case ImageDataDirectoryType.LOAD_CONFIG_TABLE:
				setLoadConfigTable(LoadConfigDirectory.read(pe, b));
				break;
			case ImageDataDirectoryType.BOUND_IMPORT:
				setBoundImports(BoundImportDirectoryTable.read(new DataReader(b)));
				break;
			case ImageDataDirectoryType.IAT:
				setIat(b);
				break;
			case ImageDataDirectoryType.DELAY_IMPORT_DESCRIPTOR:
				setDelayImportDescriptor(b);
				break;
			case ImageDataDirectoryType.CLR_RUNTIME_HEADER:
				setClrRuntimeHeader(CLRRuntimeHeader.read(b));
				break;
			case ImageDataDirectoryType.RESERVED:
				setReserved(b);
				break;
		}
	}

	public void write(PE pe, DataEntry entry, IDataWriter dw)
			throws IOException {
		ImageDataDirectory idd = pe.getOptionalHeader().getDataDirectory(
				entry.index);
		RVAConverter rvc = pe.getSectionTable().getRVAConverter();
		int prd = idd.getVirtualAddress();
		if (entry.index != ImageDataDirectoryType.CERTIFICATE_TABLE)
			prd = rvc.convertVirtualAddressToRawDataPointer(idd
					.getVirtualAddress());
		if (prd > dw.getPosition()) {
			byte[] pa = pe.getImageData().getPreamble(entry.index);
			if (pa != null)
				dw.writeBytes(pa);
			else
				dw.writeByte(0, prd - dw.getPosition());
		}

		switch (entry.index) {
			case ImageDataDirectoryType.EXPORT_TABLE:
				dw.writeBytes(getExportTable().get());
				break;
			case ImageDataDirectoryType.IMPORT_TABLE:
				dw.writeBytes(getImportTable().get());
				break;
			case ImageDataDirectoryType.RESOURCE_TABLE:
				dw.writeBytes(getResourceTable().get());
				break;
			case ImageDataDirectoryType.EXCEPTION_TABLE:
				dw.writeBytes(getExceptionTable());
				break;
			case ImageDataDirectoryType.CERTIFICATE_TABLE:
				dw.writeBytes(getCertificateTable().get());
				break;
			case ImageDataDirectoryType.BASE_RELOCATION_TABLE:
				dw.writeBytes(getBaseRelocationTable());
				break;
			case ImageDataDirectoryType.DEBUG:
				dw.writeBytes(getDebug().get());
				break;
			case ImageDataDirectoryType.ARCHITECTURE:
				dw.writeBytes(getArchitecture());
				break;
			case ImageDataDirectoryType.GLOBAL_PTR:
				dw.writeBytes(getGlobalPtr());
				break;
			case ImageDataDirectoryType.TLS_TABLE:
				dw.writeBytes(getTlsTable());
				break;
			case ImageDataDirectoryType.LOAD_CONFIG_TABLE:
				break;
			case ImageDataDirectoryType.BOUND_IMPORT:
				getBoundImports().write(pe, dw);
				break;
			case ImageDataDirectoryType.IAT:
				dw.writeBytes(getIat());
				break;
			case ImageDataDirectoryType.DELAY_IMPORT_DESCRIPTOR:
				dw.writeBytes(getDelayImportDescriptor());
				break;
			case ImageDataDirectoryType.CLR_RUNTIME_HEADER:
				dw.writeBytes(getClrRuntimeHeader().get());
				break;
			case ImageDataDirectoryType.RESERVED:
				dw.writeBytes(getReserved());
				break;
		}
	}

	public void writeDebugRawData(DataEntry entry, IDataWriter dw)
			throws IOException {
		if (entry.pointer > dw.getPosition()) {
			byte[] pa = getDebugRawDataPreamble();
			if (pa != null)
				dw.writeBytes(pa);
			else
				dw.writeByte(0, entry.pointer - dw.getPosition());
		}
		dw.writeBytes(getDebugRawData());
	}

	public byte[] getHeaderPadding() {
		return headerPadding;
	}

	public void setHeaderPadding(byte[] headerPadding) {
		this.headerPadding = headerPadding;
	}

	public byte[] getPreamble(int directory) {
		return (byte[]) preambles.get(directory);
	}

	public void put(int directory, byte[] preamble) {
		preambles.put(directory, preamble);
	}

	public ExportDirectory getExportTable() {
		return exportTable;
	}

	public void setExportTable(ExportDirectory exportTable) {
		this.exportTable = exportTable;
	}

	public ImportDirectory getImportTable() {
		return importTable;
	}

	public void setImportTable(ImportDirectory importTable) {
		this.importTable = importTable;
	}

	public ResourceDirectory getResourceTable() {
		return resourceTable;
	}

	public void setResourceTable(ResourceDirectory resourceTable) {
		this.resourceTable = resourceTable;
	}

	public byte[] getExceptionTable() {
		return exceptionTable;
	}

	public void setExceptionTable(byte[] exceptionTable) {
		this.exceptionTable = exceptionTable;
	}

	public AttributeCertificateTable getCertificateTable() {
		return certificateTable;
	}

	public void setCertificateTable(AttributeCertificateTable certificateTable) {
		this.certificateTable = certificateTable;
	}

	public byte[] getBaseRelocationTable() {
		return baseRelocationTable;
	}

	public void setBaseRelocationTable(byte[] baseRelocationTable) {
		this.baseRelocationTable = baseRelocationTable;
	}

	public DebugDirectory getDebug() {
		return debug;
	}

	public void setDebug(DebugDirectory debug) {
		this.debug = debug;
	}

	public byte[] getArchitecture() {
		return architecture;
	}

	public void setArchitecture(byte[] architecture) {
		this.architecture = architecture;
	}

	public byte[] getGlobalPtr() {
		return globalPtr;
	}

	public void setGlobalPtr(byte[] globalPtr) {
		this.globalPtr = globalPtr;
	}

	public byte[] getTlsTable() {
		return tlsTable;
	}

	public void setTlsTable(byte[] tlsTable) {
		this.tlsTable = tlsTable;
	}

	public LoadConfigDirectory getLoadConfigTable() {
		return loadConfigTable;
	}

	public void setLoadConfigTable(LoadConfigDirectory loadConfigTable) {
		this.loadConfigTable = loadConfigTable;
	}

	public BoundImportDirectoryTable getBoundImports() {
		return boundImports;
	}

	public void setBoundImports(BoundImportDirectoryTable boundImports) {
		this.boundImports = boundImports;
	}

	public byte[] getIat() {
		return iat;
	}

	public void setIat(byte[] iat) {
		this.iat = iat;
	}

	public byte[] getDelayImportDescriptor() {
		return delayImportDescriptor;
	}

	public void setDelayImportDescriptor(byte[] delayImportDescriptor) {
		this.delayImportDescriptor = delayImportDescriptor;
	}

	public CLRRuntimeHeader getClrRuntimeHeader() {
		return clrRuntimeHeader;
	}

	public void setClrRuntimeHeader(CLRRuntimeHeader clrRuntimeHeader) {
		this.clrRuntimeHeader = clrRuntimeHeader;
	}

	public byte[] getReserved() {
		return reserved;
	}

	public void setReserved(byte[] reserved) {
		this.reserved = reserved;
	}

	public byte[] getDebugRawData() {
		return debugRawData;
	}

	public void setDebugRawData(byte[] debugRawData) {
		this.debugRawData = debugRawData;
	}

	public byte[] getTrailingData() {
		return trailingData;
	}

	public void setTrailingData(byte[] trailingData) {
		this.trailingData = trailingData;
	}

	public byte[] getDebugRawDataPreamble() {
		return debugRawDataPreamble;
	}

	public void setDebugRawDataPreamble(byte[] debugRawDataPreamble) {
		this.debugRawDataPreamble = debugRawDataPreamble;
	}
}
