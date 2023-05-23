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

import com.kichik.pecoff4j.constant.ImageDataDirectoryType;
import com.kichik.pecoff4j.io.ByteArrayDataReader;
import com.kichik.pecoff4j.io.DataEntry;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;

import java.io.IOException;

public class SectionData {
	private byte[] data;
	private byte[] preamble;

	public static SectionData read(PE pe, DataEntry entry, IDataReader dr)
			throws IOException {
		SectionTable st = pe.getSectionTable();
		SectionHeader sh = st.getHeader(entry.index);
		SectionData sd = new SectionData();

		// Read any preamble - store if non-zero
		byte[] pa = dr.readNonZeroOrNull(sh.getPointerToRawData());
		if (pa != null)
			sd.setPreamble(pa);

		// Read in the raw data block
		dr.jumpTo(sh.getPointerToRawData());
		byte[] b = new byte[sh.getSizeOfRawData()];
		dr.read(b);
		sd.setData(b);
		st.put(entry.index, sd);

		// Check for an image directory within this section
		int ddc = pe.getOptionalHeader().getDataDirectoryCount();
		for (int i = 0; i < ddc; i++) {
			if (i == ImageDataDirectoryType.CERTIFICATE_TABLE)
				continue;
			ImageDataDirectory idd = pe.getOptionalHeader().getDataDirectory(i);
			if (idd.getSize() > 0) {
				int vad = sh.getVirtualAddress();
				int vex = vad + sh.getVirtualSize();
				int dad = idd.getVirtualAddress();
				if (dad >= vad && dad < vex) {
					int off = dad - vad;
					IDataReader idr = new ByteArrayDataReader(b, off,
							idd.getSize());
					DataEntry de = new DataEntry(i, 0);
					de.baseAddress = sh.getVirtualAddress();
					pe.getImageData().read(pe, de, idr);
				}
			}
		}
		return sd;
	}

	public void write(PE pe, DataEntry entry, IDataWriter dw)
			throws IOException {
		SectionTable st = pe.getSectionTable();
		SectionHeader sh = st.getHeader(entry.index);
		SectionData sd = st.getSection(entry.index);
		int prd = sh.getPointerToRawData();
		if (prd > dw.getPosition()) {
			byte[] pa = sd.getPreamble();
			if (pa != null) {
				dw.writeBytes(pa);
			} else {
				dw.writeByte(0, prd - dw.getPosition());
			}
		}

		byte[] b = sd.getData();
		dw.writeBytes(b);
	}

	public byte[] getPreamble() {
		return preamble;
	}

	public void setPreamble(byte[] preamble) {
		this.preamble = preamble;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
