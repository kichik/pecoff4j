/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *     Amir Szekely
 *******************************************************************************/
package com.kichik.pecoff4j.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Strings;

public class StringFileInfo {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private int padding;
	private List<StringTable> tables = new ArrayList<StringTable>();

	public static StringFileInfo read(IDataReader dr) throws IOException {
		int initialPos = dr.getPosition();

		StringFileInfo sfi = new StringFileInfo();

		sfi.setLength(dr.readWord());
		sfi.setValueLength(dr.readWord());
		sfi.setType(dr.readWord());
		sfi.setKey(dr.readUnicode());
		sfi.setPadding(dr.align(4));

		while (dr.getPosition() - initialPos < sfi.getLength())
			sfi.add(StringTable.read(dr));

		return sfi;
	}

	public static StringFileInfo readPartial(IDataReader dr, int initialPos, int length, int valueLength, int type, String key) throws IOException {
		StringFileInfo sfi = new StringFileInfo();

		sfi.setLength(length);
		sfi.setValueLength(valueLength);
		sfi.setType(type);
		sfi.setKey(key);
		sfi.setPadding(dr.align(4));
		while (dr.getPosition() - initialPos < sfi.getLength()) {
			sfi.add(StringTable.read(dr));
		}
		return sfi;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(getLength());
		if (getLength() == 0) {
			return;
		}
		dw.writeWord(getValueLength());
		dw.writeWord(getType());
		dw.writeUnicode(getKey());
		dw.align(4);
		for (int i = 0; i < getCount(); i++) {
			StringTable table = getTable(i);
			table.write(dw);
		}
	}

	public void add(StringTable table) {
		tables.add(table);
	}

	public int getCount() {
		return tables.size();
	}

	public StringTable getTable(int index) {
		return tables.get(index);
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getValueLength() {
		return valueLength;
	}

	public void setValueLength(int valueLength) {
		this.valueLength = valueLength;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public int sizeOf() {
		int actualLength = 6 + padding + Strings.getUtf16Length(key);
		for (StringTable t : tables)
			actualLength += t.sizeOf();
		return actualLength;
	}
}
