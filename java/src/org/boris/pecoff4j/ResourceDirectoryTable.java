/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j;

public class ResourceDirectoryTable {
	private int characteristics;
	private int timeDateStamp;
	private int majorVersion;
	private int minVersion;
	private int numNameEntries;
	private int numIdEntries;

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
