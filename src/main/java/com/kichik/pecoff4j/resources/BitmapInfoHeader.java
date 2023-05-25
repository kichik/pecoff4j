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

public class BitmapInfoHeader {
	private int size;
	private int width;
	private int height;
	private int planes;
	private int bitCount;
	private int compression;
	private int sizeImage;
	private int xpelsPerMeter;
	private int ypelsPerMeter;
	private int clrUsed;
	private int clrImportant;

	public static BitmapInfoHeader read(IDataReader dr) throws IOException {
		BitmapInfoHeader bh = new BitmapInfoHeader();
		bh.setSize(dr.readDoubleWord());
		bh.setWidth(dr.readDoubleWord());
		bh.setHeight(dr.readDoubleWord());
		bh.setPlanes(dr.readWord());
		bh.setBitCount(dr.readWord());
		bh.setCompression(dr.readDoubleWord());
		bh.setSizeImage(dr.readDoubleWord());
		bh.setXpelsPerMeter(dr.readDoubleWord());
		bh.setYpelsPerMeter(dr.readDoubleWord());
		bh.setClrUsed(dr.readDoubleWord());
		bh.setClrImportant(dr.readDoubleWord());

		return bh;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeDoubleWord(getSize());
		dw.writeDoubleWord(getWidth());
		dw.writeDoubleWord(getHeight());
		dw.writeWord(getPlanes());
		dw.writeWord(getBitCount());
		dw.writeDoubleWord(getCompression());
		dw.writeDoubleWord(getSizeImage());
		dw.writeDoubleWord(getXpelsPerMeter());
		dw.writeDoubleWord(getYpelsPerMeter());
		dw.writeDoubleWord(getClrUsed());
		dw.writeDoubleWord(getClrImportant());
	}

	public int getSize() {
		return size;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPlanes() {
		return planes;
	}

	public int getBitCount() {
		return bitCount;
	}

	public int getCompression() {
		return compression;
	}

	public int getSizeImage() {
		return sizeImage;
	}

	public int getXpelsPerMeter() {
		return xpelsPerMeter;
	}

	public int getYpelsPerMeter() {
		return ypelsPerMeter;
	}

	public int getClrUsed() {
		return clrUsed;
	}

	public int getClrImportant() {
		return clrImportant;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setPlanes(int planes) {
		this.planes = planes;
	}

	public void setBitCount(int bitCount) {
		this.bitCount = bitCount;
	}

	public void setCompression(int compression) {
		this.compression = compression;
	}

	public void setSizeImage(int sizeImage) {
		this.sizeImage = sizeImage;
	}

	public void setXpelsPerMeter(int xpelsPerMeter) {
		this.xpelsPerMeter = xpelsPerMeter;
	}

	public void setYpelsPerMeter(int ypelsPerMeter) {
		this.ypelsPerMeter = ypelsPerMeter;
	}

	public void setClrUsed(int clrUsed) {
		this.clrUsed = clrUsed;
	}

	public void setClrImportant(int clrImportant) {
		this.clrImportant = clrImportant;
	}
}
