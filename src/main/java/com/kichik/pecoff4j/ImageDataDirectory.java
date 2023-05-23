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

public class ImageDataDirectory {
	private int virtualAddress;
	private int size;

	public static ImageDataDirectory read(IDataReader dr)
			throws IOException {
		ImageDataDirectory idd = new ImageDataDirectory();
		idd.setVirtualAddress(dr.readDoubleWord());
		idd.setSize(dr.readDoubleWord());
		return idd;
	}

	public void write(IDataWriter dw)
			throws IOException {
		dw.writeDoubleWord(getVirtualAddress());
		dw.writeDoubleWord(getSize());
	}

	public int getVirtualAddress() {
		return virtualAddress;
	}

	public int getSize() {
		return size;
	}

	public void setVirtualAddress(int virtualAddress) {
		this.virtualAddress = virtualAddress;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
