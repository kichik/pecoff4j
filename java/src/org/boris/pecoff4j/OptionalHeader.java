/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j;

public class OptionalHeader
{
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
