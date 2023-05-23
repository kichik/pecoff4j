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
import com.kichik.pecoff4j.util.Reflection;

import java.io.IOException;

/**
 * The export directory table. See section 6.3.1 of the PE/COFF specification
 * v8.
 */
public class ExportDirectory extends DataObject {
	private long exportFlags;
	private long timeDateStamp;
	private int majorVersion;
	private int minorVersion;
	private long nameRVA;
	private long ordinalBase;
	private long addressTableEntries;
	private long numberOfNamePointers;
	private long exportAddressTableRVA;
	private long namePointerRVA;
	private long ordinalTableRVA;

	public static ExportDirectory read(byte[] b) throws IOException {
		DataReader dr = new DataReader(b);
		ExportDirectory edt = new ExportDirectory();
		edt.set(b);
		edt.setExportFlags(dr.readDoubleWord());
		edt.setTimeDateStamp(dr.readDoubleWord());
		edt.setMajorVersion(dr.readWord());
		edt.setMinorVersion(dr.readWord());
		edt.setNameRVA(dr.readDoubleWord());
		edt.setOrdinalBase(dr.readDoubleWord());
		edt.setAddressTableEntries(dr.readDoubleWord());
		edt.setNumberOfNamePointers(dr.readDoubleWord());
		edt.setExportAddressTableRVA(dr.readDoubleWord());
		edt.setNamePointerRVA(dr.readDoubleWord());
		edt.setOrdinalTableRVA(dr.readDoubleWord());
		return edt;
	}

	public long getExportFlags() {
		return exportFlags;
	}

	public long getTimeDateStamp() {
		return timeDateStamp;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public long getNameRVA() {
		return nameRVA;
	}

	public long getOrdinalBase() {
		return ordinalBase;
	}

	public long getAddressTableEntries() {
		return addressTableEntries;
	}

	public long getNumberOfNamePointers() {
		return numberOfNamePointers;
	}

	public long getExportAddressTableRVA() {
		return exportAddressTableRVA;
	}

	public long getNamePointerRVA() {
		return namePointerRVA;
	}

	public long getOrdinalTableRVA() {
		return ordinalTableRVA;
	}

	@Override
	public String toString() {
		return Reflection.toString(this);
	}

	public void setExportFlags(long exportFlags) {
		this.exportFlags = exportFlags;
	}

	public void setTimeDateStamp(long timeDateStamp) {
		this.timeDateStamp = timeDateStamp;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public void setNameRVA(long nameRVA) {
		this.nameRVA = nameRVA;
	}

	public void setOrdinalBase(long ordinalBase) {
		this.ordinalBase = ordinalBase;
	}

	public void setAddressTableEntries(long addressTableEntries) {
		this.addressTableEntries = addressTableEntries;
	}

	public void setNumberOfNamePointers(long numberOfNamePointers) {
		this.numberOfNamePointers = numberOfNamePointers;
	}

	public void setExportAddressTableRVA(long exportAddressTableRVA) {
		this.exportAddressTableRVA = exportAddressTableRVA;
	}

	public void setNamePointerRVA(long namePointerRVA) {
		this.namePointerRVA = namePointerRVA;
	}

	public void setOrdinalTableRVA(long ordinalTableRVA) {
		this.ordinalTableRVA = ordinalTableRVA;
	}
}
