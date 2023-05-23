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

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;

import java.io.IOException;

public class OptionalHeader {
	public static final int MAGIC_PE32 = 0x10b;
	public static final int MAGIC_PE32plus = 0x20b;

	// Basic data structure
	private int magic;
	private int majorLinkerVersion;
	private int minorLinkerVersion;
	private int sizeOfCode;
	private int sizeOfInitializedData;
	private int sizeOfUninitializedData;
	private int addressOfEntryPoint;
	private int baseOfCode;
	private int baseOfData;
	private long imageBase;
	private int sectionAlignment;
	private int fileAlignment;
	private int majorOperatingSystemVersion;
	private int minorOperatingSystemVersion;
	private int majorImageVersion;
	private int minorImageVersion;
	private int majorSubsystemVersion;
	private int minorSubsystemVersion;
	private int win32VersionValue;
	private int sizeOfImage;
	private int sizeOfHeaders;
	private int checkSum;
	private int subsystem;
	private int dllCharacteristics;
	private long sizeOfStackReserve;
	private long sizeOfStackCommit;
	private long sizeOfHeapReserve;
	private long sizeOfHeapCommit;
	private int loaderFlags;
	private int numberOfRvaAndSizes;

	// The data directories
	private ImageDataDirectory[] dataDirectories;

	public static OptionalHeader read(IDataReader dr)
			throws IOException {
		OptionalHeader oh = new OptionalHeader();
		oh.setMagic(dr.readWord());
		boolean is64 = oh.isPE32plus();
		oh.setMajorLinkerVersion(dr.readByte());
		oh.setMinorLinkerVersion(dr.readByte());
		oh.setSizeOfCode(dr.readDoubleWord());
		oh.setSizeOfInitializedData(dr.readDoubleWord());
		oh.setSizeOfUninitializedData(dr.readDoubleWord());
		oh.setAddressOfEntryPoint(dr.readDoubleWord());
		oh.setBaseOfCode(dr.readDoubleWord());

		if (!is64)
			oh.setBaseOfData(dr.readDoubleWord());

		// NT additional fields.
		oh.setImageBase(is64 ? dr.readLong() : dr.readDoubleWord());
		oh.setSectionAlignment(dr.readDoubleWord());
		oh.setFileAlignment(dr.readDoubleWord());
		oh.setMajorOperatingSystemVersion(dr.readWord());
		oh.setMinorOperatingSystemVersion(dr.readWord());
		oh.setMajorImageVersion(dr.readWord());
		oh.setMinorImageVersion(dr.readWord());
		oh.setMajorSubsystemVersion(dr.readWord());
		oh.setMinorSubsystemVersion(dr.readWord());
		oh.setWin32VersionValue(dr.readDoubleWord());
		oh.setSizeOfImage(dr.readDoubleWord());
		oh.setSizeOfHeaders(dr.readDoubleWord());
		oh.setCheckSum(dr.readDoubleWord());
		oh.setSubsystem(dr.readWord());
		oh.setDllCharacteristics(dr.readWord());
		oh.setSizeOfStackReserve(is64 ? dr.readLong() : dr.readDoubleWord());
		oh.setSizeOfStackCommit(is64 ? dr.readLong() : dr.readDoubleWord());
		oh.setSizeOfHeapReserve(is64 ? dr.readLong() : dr.readDoubleWord());
		oh.setSizeOfHeapCommit(is64 ? dr.readLong() : dr.readDoubleWord());
		oh.setLoaderFlags(dr.readDoubleWord());
		oh.setNumberOfRvaAndSizes(dr.readDoubleWord());

		// Data directories
		ImageDataDirectory[] dds = new ImageDataDirectory[16];
		for (int i = 0; i < dds.length; i++) {
			dds[i] = ImageDataDirectory.read(dr);
		}
		oh.setDataDirectories(dds);

		return oh;
	}

	public void write(IDataWriter dw)
			throws IOException {
		boolean is64 = isPE32plus();

		dw.writeWord(getMagic());
		dw.writeByte(getMajorLinkerVersion());
		dw.writeByte(getMinorLinkerVersion());
		dw.writeDoubleWord(getSizeOfCode());
		dw.writeDoubleWord(getSizeOfInitializedData());
		dw.writeDoubleWord(getSizeOfUninitializedData());
		dw.writeDoubleWord(getAddressOfEntryPoint());
		dw.writeDoubleWord(getBaseOfCode());
		if (!is64)
			dw.writeDoubleWord(getBaseOfData());

		// NT additional fields.
		if (is64)
			dw.writeLong(getImageBase());
		else
			dw.writeDoubleWord((int) getImageBase());

		dw.writeDoubleWord(getSectionAlignment());
		dw.writeDoubleWord(getFileAlignment());
		dw.writeWord(getMajorOperatingSystemVersion());
		dw.writeWord(getMinorOperatingSystemVersion());
		dw.writeWord(getMajorImageVersion());
		dw.writeWord(getMinorImageVersion());
		dw.writeWord(getMajorSubsystemVersion());
		dw.writeWord(getMinorSubsystemVersion());
		dw.writeDoubleWord(getWin32VersionValue());
		dw.writeDoubleWord(getSizeOfImage());
		dw.writeDoubleWord(getSizeOfHeaders());
		dw.writeDoubleWord(getCheckSum());
		dw.writeWord(getSubsystem());
		dw.writeWord(getDllCharacteristics());
		if (is64) {
			dw.writeLong(getSizeOfStackReserve());
			dw.writeLong(getSizeOfStackCommit());
			dw.writeLong(getSizeOfHeapReserve());
			dw.writeLong(getSizeOfHeapCommit());
		} else {
			dw.writeDoubleWord((int) getSizeOfStackReserve());
			dw.writeDoubleWord((int) getSizeOfStackCommit());
			dw.writeDoubleWord((int) getSizeOfHeapReserve());
			dw.writeDoubleWord((int) getSizeOfHeapCommit());
		}

		dw.writeDoubleWord(getLoaderFlags());
		dw.writeDoubleWord(getNumberOfRvaAndSizes());

		// Data directories
		int ddc = getDataDirectoryCount();
		for (int i = 0; i < ddc; i++) {
			getDataDirectory(i).write(dw);
		}
	}

	public int getMagic() {
		return magic;
	}

	public boolean isValid() {
		return magic == MAGIC_PE32 || magic == MAGIC_PE32plus;
	}

	public boolean isPE32plus() {
		return magic == MAGIC_PE32plus;
	}

	public int getMajorLinkerVersion() {
		return majorLinkerVersion;
	}

	public int getMinorLinkerVersion() {
		return minorLinkerVersion;
	}

	public int getSizeOfCode() {
		return sizeOfCode;
	}

	public int getSizeOfInitializedData() {
		return sizeOfInitializedData;
	}

	public int getSizeOfUninitializedData() {
		return sizeOfUninitializedData;
	}

	public int getAddressOfEntryPoint() {
		return addressOfEntryPoint;
	}

	public int getBaseOfCode() {
		return baseOfCode;
	}

	public int getBaseOfData() {
		return baseOfData;
	}

	public long getImageBase() {
		return imageBase;
	}

	public int getSectionAlignment() {
		return sectionAlignment;
	}

	public int getFileAlignment() {
		return fileAlignment;
	}

	public int getMajorOperatingSystemVersion() {
		return majorOperatingSystemVersion;
	}

	public int getMinorOperatingSystemVersion() {
		return minorOperatingSystemVersion;
	}

	public int getMajorImageVersion() {
		return majorImageVersion;
	}

	public int getMinorImageVersion() {
		return minorImageVersion;
	}

	public int getMajorSubsystemVersion() {
		return majorSubsystemVersion;
	}

	public int getMinorSubsystemVersion() {
		return minorSubsystemVersion;
	}

	public int getWin32VersionValue() {
		return win32VersionValue;
	}

	public int getSizeOfImage() {
		return sizeOfImage;
	}

	public int getSizeOfHeaders() {
		return sizeOfHeaders;
	}

	public int getCheckSum() {
		return checkSum;
	}

	public int getSubsystem() {
		return subsystem;
	}

	public int getDllCharacteristics() {
		return dllCharacteristics;
	}

	public long getSizeOfStackReserve() {
		return sizeOfStackReserve;
	}

	public long getSizeOfStackCommit() {
		return sizeOfStackCommit;
	}

	public long getSizeOfHeapReserve() {
		return sizeOfHeapReserve;
	}

	public long getSizeOfHeapCommit() {
		return sizeOfHeapCommit;
	}

	public int getLoaderFlags() {
		return loaderFlags;
	}

	public int getNumberOfRvaAndSizes() {
		return numberOfRvaAndSizes;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	public void setMajorLinkerVersion(int majorLinkerVersion) {
		this.majorLinkerVersion = majorLinkerVersion;
	}

	public void setMinorLinkerVersion(int minorLinkerVersion) {
		this.minorLinkerVersion = minorLinkerVersion;
	}

	public void setSizeOfCode(int sizeOfCode) {
		this.sizeOfCode = sizeOfCode;
	}

	public void setSizeOfInitializedData(int sizeOfInitializedData) {
		this.sizeOfInitializedData = sizeOfInitializedData;
	}

	public void setSizeOfUninitializedData(int sizeOfUninitializedData) {
		this.sizeOfUninitializedData = sizeOfUninitializedData;
	}

	public void setAddressOfEntryPoint(int addressOfEntryPoint) {
		this.addressOfEntryPoint = addressOfEntryPoint;
	}

	public void setBaseOfCode(int baseOfCode) {
		this.baseOfCode = baseOfCode;
	}

	public void setBaseOfData(int baseOfData) {
		this.baseOfData = baseOfData;
	}

	public void setImageBase(long imageBase) {
		this.imageBase = imageBase;
	}

	public void setSectionAlignment(int sectionAlignment) {
		this.sectionAlignment = sectionAlignment;
	}

	public void setFileAlignment(int fileAlignment) {
		this.fileAlignment = fileAlignment;
	}

	public void setMajorOperatingSystemVersion(int majorOperatingSystemVersion) {
		this.majorOperatingSystemVersion = majorOperatingSystemVersion;
	}

	public void setMinorOperatingSystemVersion(int minorOperatingSystemVersion) {
		this.minorOperatingSystemVersion = minorOperatingSystemVersion;
	}

	public void setMajorImageVersion(int majorImageVersion) {
		this.majorImageVersion = majorImageVersion;
	}

	public void setMinorImageVersion(int minorImageVersion) {
		this.minorImageVersion = minorImageVersion;
	}

	public void setMajorSubsystemVersion(int majorSubsystemVersion) {
		this.majorSubsystemVersion = majorSubsystemVersion;
	}

	public void setMinorSubsystemVersion(int minorSubsystemVersion) {
		this.minorSubsystemVersion = minorSubsystemVersion;
	}

	public void setWin32VersionValue(int win32VersionValue) {
		this.win32VersionValue = win32VersionValue;
	}

	public void setSizeOfImage(int sizeOfImage) {
		this.sizeOfImage = sizeOfImage;
	}

	public void setSizeOfHeaders(int sizeOfHeaders) {
		this.sizeOfHeaders = sizeOfHeaders;
	}

	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

	public void setSubsystem(int subsystem) {
		this.subsystem = subsystem;
	}

	public void setDllCharacteristics(int dllCharacteristics) {
		this.dllCharacteristics = dllCharacteristics;
	}

	public void setSizeOfStackReserve(long sizeOfStackReserve) {
		this.sizeOfStackReserve = sizeOfStackReserve;
	}

	public void setSizeOfStackCommit(long sizeOfStackCommit) {
		this.sizeOfStackCommit = sizeOfStackCommit;
	}

	public void setSizeOfHeapReserve(long sizeOfHeapReserve) {
		this.sizeOfHeapReserve = sizeOfHeapReserve;
	}

	public void setSizeOfHeapCommit(long sizeOfHeapCommit) {
		this.sizeOfHeapCommit = sizeOfHeapCommit;
	}

	public void setLoaderFlags(int loaderFlags) {
		this.loaderFlags = loaderFlags;
	}

	public void setNumberOfRvaAndSizes(int numberOfRvaAndSizes) {
		this.numberOfRvaAndSizes = numberOfRvaAndSizes;
	}

	public int getDataDirectoryCount() {
		return dataDirectories.length;
	}

	public ImageDataDirectory[] getDataDirectories() {
		return dataDirectories;
	}

	public ImageDataDirectory getDataDirectory(int index) {
		return dataDirectories[index];
	}

	public void setDataDirectories(ImageDataDirectory[] dataDirectories) {
		this.dataDirectories = dataDirectories;
	}
}
