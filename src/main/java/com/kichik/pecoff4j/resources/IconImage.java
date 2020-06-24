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

public class IconImage {
	private BitmapInfoHeader header;
	private RGBQuad[] colors;
	private byte[] xorMask;
	private byte[] andMask;
	private byte[] pngData;

	public BitmapInfoHeader getHeader() {
		return header;
	}

	public RGBQuad[] getColors() {
		return colors;
	}

	public byte[] getXorMask() {
		return xorMask;
	}

	public byte[] getAndMask() {
		return andMask;
	}

	public byte[] getPNG() {
		return pngData;
	}

	public void setHeader(BitmapInfoHeader header) {
		this.header = header;
	}

	public void setColors(RGBQuad[] colors) {
		this.colors = colors;
	}

	public void setXorMask(byte[] xorMask) {
		this.xorMask = xorMask;
	}

	public void setAndMask(byte[] andMask) {
		this.andMask = andMask;
	}

	public void setPngData(byte[] pngData) {
		this.pngData = pngData;
	}

	public int sizeOf() {
		return header == null ? pngData.length : header.getSize()
				+ (colors == null ? 0 : colors.length * 4) + xorMask.length
				+ andMask.length;
	}
}
