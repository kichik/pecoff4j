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

public class ResourceDirectoryTable {
	private int characteristics;
	private int timeDateStamp;
	private int majorVersion;
	private int minVersion;
	private int numNameEntries;
	private int numIdEntries;

	public static ResourceDirectoryTable read(IDataReader dr) throws IOException {
		ResourceDirectoryTable t = new ResourceDirectoryTable();
		t.setCharacteristics(dr.readDoubleWord());
		t.setTimeDateStamp(dr.readDoubleWord());
		t.setMajorVersion(dr.readWord());
		t.setMinVersion(dr.readWord());
		t.setNumNameEntries(dr.readWord());
		t.setNumIdEntries(dr.readWord());

		return t;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeDoubleWord(getCharacteristics());
		dw.writeDoubleWord(getTimeDateStamp());
		dw.writeWord(getMajorVersion());
		dw.writeWord(getMinVersion());
		dw.writeWord(getNumNameEntries());
		dw.writeWord(getNumIdEntries());
	}

	public int getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(int characteristics) {
		this.characteristics = characteristics;
	}

	public int getTimeDateStamp() {
		return timeDateStamp;
	}

	public void setTimeDateStamp(int timeDateStamp) {
		this.timeDateStamp = timeDateStamp;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getMinVersion() {
		return minVersion;
	}

	public void setMinVersion(int minVersion) {
		this.minVersion = minVersion;
	}

	public int getNumNameEntries() {
		return numNameEntries;
	}

	public void setNumNameEntries(int numNameEntries) {
		this.numNameEntries = numNameEntries;
	}

	public int getNumIdEntries() {
		return numIdEntries;
	}

	public void setNumIdEntries(int numIdEntries) {
		this.numIdEntries = numIdEntries;
	}
}
