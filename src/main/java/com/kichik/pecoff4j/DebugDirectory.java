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

import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.util.DataObject;

import java.io.IOException;

/**
 * Encapsulates the Debug Directory (Image Only). Section 6.1.1 of the PE/COFF
 * spec v8.
 */
public class DebugDirectory extends DataObject {
	private int characteristics;
	private int timeDateStamp;
	private int majorVersion;
	private int type;
	private int sizeOfData;
	private int addressOfRawData;
	private int pointerToRawData;

	public static DebugDirectory read(byte[] b) throws IOException {
		DebugDirectory dd = new DebugDirectory();
		dd.set(b);
		DataReader dr = new DataReader(b);
		dd.setCharacteristics(dr.readDoubleWord());
		dd.setTimeDateStamp(dr.readDoubleWord());
		dd.setMajorVersion(dr.readWord());
		dd.setMajorVersion(dr.readWord());
		dd.setType(dr.readDoubleWord());
		dd.setSizeOfData(dr.readDoubleWord());
		dd.setAddressOfRawData(dr.readDoubleWord());
		dd.setPointerToRawData(dr.readDoubleWord());
		return dd;
	}

	public int getCharacteristics() {
		return characteristics;
	}

	public int getTimeDateStamp() {
		return timeDateStamp;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getType() {
		return type;
	}

	public int getSizeOfData() {
		return sizeOfData;
	}

	public int getAddressOfRawData() {
		return addressOfRawData;
	}

	public int getPointerToRawData() {
		return pointerToRawData;
	}

	public void setCharacteristics(int characteristics) {
		this.characteristics = characteristics;
	}

	public void setTimeDateStamp(int timeDateStamp) {
		this.timeDateStamp = timeDateStamp;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setSizeOfData(int sizeOfData) {
		this.sizeOfData = sizeOfData;
	}

	public void setAddressOfRawData(int addressOfRawData) {
		this.addressOfRawData = addressOfRawData;
	}

	public void setPointerToRawData(int pointerToRawData) {
		this.pointerToRawData = pointerToRawData;
	}
}
