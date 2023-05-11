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

import java.io.IOException;

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Reflection;

public class GroupIconDirectoryEntry {
	private int width;
	private int height;
	private int colorCount;
	private int reserved;
	private int planes;
	private int bitCount;
	private int bytesInRes;
	private int id;

	public static GroupIconDirectoryEntry read(IDataReader dr)
			throws IOException {
		GroupIconDirectoryEntry ge = new GroupIconDirectoryEntry();
		ge.width = dr.readByte();
		ge.height = dr.readByte();
		ge.colorCount = dr.readByte();
		ge.reserved = dr.readByte();
		ge.planes = dr.readWord();
		ge.bitCount = dr.readWord();
		ge.bytesInRes = dr.readDoubleWord();
		ge.id = dr.readWord();

		return ge;
	}

	@Override
	public String toString() {
		return Reflection.toString(this);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight(){
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getColorCount() {
		return colorCount;
	}

	public void setColorCount(int colorCount) {
		this.colorCount = colorCount;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public int getPlanes() {
		return planes;
	}

	public void setPlanes(int planes) {
		this.planes = planes;
	}

	public int getBitCount() {
		return bitCount;
	}

	public void setBitCount(int bitCount) {
		this.bitCount = bitCount;
	}

	public int getBytesInRes() {
		return bytesInRes;
	}

	public void setBytesInRes(int bytesInRes) {
		this.bytesInRes = bytesInRes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeByte(width);
		dw.writeByte(height);
		dw.writeByte(colorCount);
		dw.writeByte(reserved);
		dw.writeWord(planes);
		dw.writeWord(bitCount);
		dw.writeDoubleWord(bytesInRes);
		dw.writeWord(id);
	}
}
