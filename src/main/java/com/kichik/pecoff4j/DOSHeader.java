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

public class DOSHeader {
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

	public static DOSHeader read(IDataReader dr) throws IOException {
		DOSHeader dh = new DOSHeader();
		dh.setMagic(dr.readWord());
		dh.setUsedBytesInLastPage(dr.readWord());
		dh.setFileSizeInPages(dr.readWord());
		dh.setNumRelocationItems(dr.readWord());
		dh.setHeaderSizeInParagraphs(dr.readWord());
		dh.setMinExtraParagraphs(dr.readWord());
		dh.setMaxExtraParagraphs(dr.readWord());
		dh.setInitialSS(dr.readWord());
		dh.setInitialSP(dr.readWord());
		dh.setChecksum(dr.readWord());
		dh.setInitialIP(dr.readWord());
		dh.setInitialRelativeCS(dr.readWord());
		dh.setAddressOfRelocationTable(dr.readWord());
		dh.setOverlayNumber(dr.readWord());
		int[] reserved = new int[4];
		for (int i = 0; i < reserved.length; i++) {
			reserved[i] = dr.readWord();
		}
		dh.setReserved(reserved);
		dh.setOemId(dr.readWord());
		dh.setOemInfo(dr.readWord());
		int[] reserved2 = new int[10];
		for (int i = 0; i < reserved2.length; i++) {
			reserved2[i] = dr.readWord();
		}
		dh.setReserved2(reserved2);
		dh.setAddressOfNewExeHeader(dr.readDoubleWord());

		// calc stub size
		int stubSize = dh.getFileSizeInPages() * 512
				- (512 - dh.getUsedBytesInLastPage());
		if (stubSize > dh.getAddressOfNewExeHeader())
			stubSize = dh.getAddressOfNewExeHeader();
		stubSize -= dh.getHeaderSizeInParagraphs() * 16;
		dh.setStubSize(stubSize);

		return dh;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(getMagic());
		dw.writeWord(getUsedBytesInLastPage());
		dw.writeWord(getFileSizeInPages());
		dw.writeWord(getNumRelocationItems());
		dw.writeWord(getHeaderSizeInParagraphs());
		dw.writeWord(getMinExtraParagraphs());
		dw.writeWord(getMaxExtraParagraphs());
		dw.writeWord(getInitialSS());
		dw.writeWord(getInitialSP());
		dw.writeWord(getChecksum());
		dw.writeWord(getInitialIP());
		dw.writeWord(getInitialRelativeCS());
		dw.writeWord(getAddressOfRelocationTable());
		dw.writeWord(getOverlayNumber());
		int[] res = getReserved();
		for (int i = 0; i < res.length; i++) {
			dw.writeWord(res[i]);
		}
		dw.writeWord(getOemId());
		dw.writeWord(getOemInfo());
		int[] res2 = getReserved2();
		for (int i = 0; i < res2.length; i++) {
			dw.writeWord(res2[i]);
		}
		dw.writeDoubleWord(getAddressOfNewExeHeader());
	}

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
