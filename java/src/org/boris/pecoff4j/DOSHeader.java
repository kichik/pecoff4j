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

public class DOSHeader
{
    public static final int DOS_MAGIC = 0;

    private int magic;
    private int usedBytesInLastPage;
    private int fileSizeInPages;
    private int numRelocationItems;
    private int headerSizeInParagraphs;
    private int minExtraParagraphs;
    private int maxExtraParagraphs;
    private int initialSS;
    private int initialSP;
    private int checksum;
    private int initialIP;
    private int initialRelativeCS;
    private int addressOfRelocationTable;
    private int overlayNumber;
    private int[] reserved;
    private int[] reserved2;
    private int oemId;
    private int oemInfo;
    private int addressOfNewExeHeader;
    private int stubSize;

    public int getMagic() {
        return magic;
    }

    public boolean isValidMagic() {
        return magic == DOS_MAGIC;
    }

    public int getUsedBytesInLastPage() {
        return usedBytesInLastPage;
    }

    public int getFileSizeInPages() {
        return fileSizeInPages;
    }

    public int getNumRelocationItems() {
        return numRelocationItems;
    }

    public int getHeaderSizeInParagraphs() {
        return headerSizeInParagraphs;
    }

    public int getMinExtraParagraphs() {
        return minExtraParagraphs;
    }

    public int getMaxExtraParagraphs() {
        return maxExtraParagraphs;
    }

    public int getInitialSS() {
        return initialSS;
    }

    public int getInitialSP() {
        return initialSP;
    }

    public int getChecksum() {
        return checksum;
    }

    public int getInitialIP() {
        return initialIP;
    }

    public int getInitialRelativeCS() {
        return initialRelativeCS;
    }

    public int getAddressOfRelocationTable() {
        return addressOfRelocationTable;
    }

    public int getOverlayNumber() {
        return overlayNumber;
    }

    public int getOemId() {
        return oemId;
    }

    public int getOemInfo() {
        return oemInfo;
    }

    public int getAddressOfNewExeHeader() {
        return addressOfNewExeHeader;
    }

    public int[] getReserved() {
        return reserved;
    }

    public int[] getReserved2() {
        return reserved2;
    }

    public int getStubSize() {
        return stubSize;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setUsedBytesInLastPage(int usedBytesInLastPage) {
        this.usedBytesInLastPage = usedBytesInLastPage;
    }

    public void setFileSizeInPages(int fileSizeInPages) {
        this.fileSizeInPages = fileSizeInPages;
    }

    public void setNumRelocationItems(int numRelocationItems) {
        this.numRelocationItems = numRelocationItems;
    }

    public void setHeaderSizeInParagraphs(int headerSizeInParagraphs) {
        this.headerSizeInParagraphs = headerSizeInParagraphs;
    }

    public void setMinExtraParagraphs(int minExtraParagraphs) {
        this.minExtraParagraphs = minExtraParagraphs;
    }

    public void setMaxExtraParagraphs(int maxExtraParagraphs) {
        this.maxExtraParagraphs = maxExtraParagraphs;
    }

    public void setInitialSS(int initialSS) {
        this.initialSS = initialSS;
    }

    public void setInitialSP(int initialSP) {
        this.initialSP = initialSP;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    public void setInitialIP(int initialIP) {
        this.initialIP = initialIP;
    }

    public void setInitialRelativeCS(int initialRelativeCS) {
        this.initialRelativeCS = initialRelativeCS;
    }

    public void setAddressOfRelocationTable(int addressOfRelocationTable) {
        this.addressOfRelocationTable = addressOfRelocationTable;
    }

    public void setOverlayNumber(int overlayNumber) {
        this.overlayNumber = overlayNumber;
    }

    public void setReserved(int[] reserved) {
        this.reserved = reserved;
    }

    public void setReserved2(int[] reserved2) {
        this.reserved2 = reserved2;
    }

    public void setOemId(int oemId) {
        this.oemId = oemId;
    }

    public void setOemInfo(int oemInfo) {
        this.oemInfo = oemInfo;
    }

    public void setAddressOfNewExeHeader(int addressOfNewExeHeader) {
        this.addressOfNewExeHeader = addressOfNewExeHeader;
    }

    public void setStubSize(int stubSize) {
        this.stubSize = stubSize;
    }
}
