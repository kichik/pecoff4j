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

public class BitmapFileHeader {
	private int type;
	private int size;
	private int reserved1;
	private int reserved2;
	private int offBits;

	public static BitmapFileHeader read(IDataReader dr) throws IOException {
		BitmapFileHeader bfh = new BitmapFileHeader();
		bfh.type = dr.readWord();
		bfh.size = dr.readDoubleWord();
		bfh.reserved1 = dr.readWord();
		bfh.reserved2 = dr.readWord();
		bfh.offBits = dr.readDoubleWord();

		return bfh;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(getType());
		dw.writeDoubleWord(getSize());
		dw.writeWord(getReserved1());
		dw.writeWord(getReserved2());
		dw.writeDoubleWord(getOffBits());
	}

	public int getType() {
		return type;
	}

	public int getSize() {
		return size;
	}

	public int getReserved1() {
		return reserved1;
	}

	public int getReserved2() {
		return reserved2;
	}

	public int getOffBits() {
		return offBits;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setReserved1(int reserved1) {
		this.reserved1 = reserved1;
	}

	public void setReserved2(int reserved2) {
		this.reserved2 = reserved2;
	}

	public void setOffBits(int offBits) {
		this.offBits = offBits;
	}
}
