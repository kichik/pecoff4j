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

public class IconImage {
	private BitmapInfoHeader header;
	private RGBQuad[] colors;
	private byte[] xorMask;
	private byte[] andMask;
	private byte[] pngData;

	public static IconImage readIcon(IDataReader dr, int bytesInRes)
			throws IOException {
		IconImage ii = new IconImage();
		int quadSize = 0;
		ii.setHeader(BitmapInfoHeader.read(dr));
		if (ii.getHeader().getClrUsed() != 0) {
			quadSize = ii.getHeader().getClrUsed();
		} else {
			if (ii.getHeader().getBitCount() <= 8) {
				quadSize = 1 << ii.getHeader().getBitCount();
			} else {
				quadSize = 0;
			}
		}

		int numBytesPerLine = ((((ii.getHeader().getWidth()
				* ii.getHeader().getPlanes() * ii.getHeader().getBitCount()) + 31) >> 5) << 2);
		int xorSize = numBytesPerLine * ii.getHeader().getHeight() / 2;
		int andSize = bytesInRes - (quadSize * 4) - ii.getHeader().getSize()
				- xorSize;

		if (quadSize > 0) {
			RGBQuad[] colors = new RGBQuad[quadSize];
			for (int i = 0; i < quadSize; i++) {
				colors[i] = RGBQuad.read(dr);
			}
			ii.setColors(colors);
		}

		byte[] xorMask = new byte[xorSize];
		dr.read(xorMask);
		ii.setXorMask(xorMask);

		byte[] andMask = new byte[andSize];
		dr.read(andMask);
		ii.setAndMask(andMask);

		return ii;
	}

	public static IconImage readPNG(byte[] data) {
		IconImage ii = new IconImage();
		ii.setPngData(data);
		return ii;
	}

	public void write(IDataWriter dw) throws IOException {
		if (getHeader() != null) {
			getHeader().write(dw);
			RGBQuad[] colors = getColors();
			if (colors != null) {
				for (int i = 0; i < colors.length; i++) {
					colors[i].write(dw);
				}
			}
			dw.writeBytes(getXorMask());
			dw.writeBytes(getAndMask());
		} else {
			dw.writeBytes(getPNG());
		}
	}

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
