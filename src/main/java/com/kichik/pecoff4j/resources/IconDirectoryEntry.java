/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.resources;

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;

import java.io.IOException;

public class IconDirectoryEntry {
	private int width;
	private int height;
	private int colorCount;
	private int reserved;
	private int planes;
	private int bitCount;
	private int bytesInRes;
	private int offset;

	public static IconDirectoryEntry read(IDataReader dr) throws IOException {
		IconDirectoryEntry ge = new IconDirectoryEntry();
		ge.setWidth(dr.readByte());
		ge.setHeight(dr.readByte());
		ge.setColorCount(dr.readByte());
		ge.setReserved(dr.readByte());
		ge.setPlanes(dr.readWord());
		ge.setBitCount(dr.readWord());
		ge.setBytesInRes(dr.readDoubleWord());
		ge.setOffset(dr.readDoubleWord());

		return ge;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeByte(getWidth());
		dw.writeByte(getHeight());
		dw.writeByte(getColorCount());
		dw.writeByte(getReserved());
		dw.writeWord(getPlanes());
		dw.writeWord(getBitCount());
		dw.writeDoubleWord(getBytesInRes());
		dw.writeDoubleWord(getOffset());
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getColorCount() {
		return colorCount;
	}

	public int getReserved() {
		return reserved;
	}

	public int getPlanes() {
		return planes;
	}

	public int getBitCount() {
		return bitCount;
	}

	public int getBytesInRes() {
		return bytesInRes;
	}

	public int getOffset() {
		return offset;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setColorCount(int colorCount) {
		this.colorCount = colorCount;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public void setPlanes(int planes) {
		this.planes = planes;
	}

	public void setBitCount(int bitCount) {
		this.bitCount = bitCount;
	}

	public void setBytesInRes(int bytesInRes) {
		this.bytesInRes = bytesInRes;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void copyFrom(GroupIconDirectoryEntry gide) {
		width = gide.getWidth();
		height = gide.getHeight();
		colorCount = gide.getColorCount();
		reserved = 0;
		planes = gide.getPlanes();
		bitCount = gide.getBitCount();
		bytesInRes = gide.getBitCount();
		offset = 0;
	}

	public static int sizeOf() {
		return 16;
	}
}
