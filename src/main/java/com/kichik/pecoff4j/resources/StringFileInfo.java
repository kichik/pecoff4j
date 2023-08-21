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

import com.kichik.pecoff4j.WritableStructure;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Strings;

import static com.kichik.pecoff4j.util.Alignment.alignDword;

/**
 * File information structure.
 *
 * See <a href="https://learn.microsoft.com/en-us/windows/win32/menurc/stringfileinfo">StringFileInfo structure</a> for details.
 */
public class StringFileInfo implements WritableStructure {
	/** The length of this structure (in bytes) */
	private int length;

	/** Always 0 */
	private int valueLength;

	/** 1 for text data, 0 for binary data */
	private int type;

	/** Must be "StringFileInfo" */
	private String key;

	private final List<StringTable> tables = new ArrayList<>();

	public static StringFileInfo read(IDataReader dr) throws IOException {
		int initialPos = dr.getPosition();

		StringFileInfo sfi = new StringFileInfo();

		sfi.setLength(dr.readWord());
		sfi.setValueLength(dr.readWord());
		sfi.setType(dr.readWord());
		sfi.setKey(dr.readUnicode());
		dr.align(4);

		while (dr.getPosition() - initialPos < sfi.getLength()) {
			sfi.add(StringTable.read(dr));
		}

		return sfi;
	}

	public static StringFileInfo readPartial(IDataReader dr, int initialPos, int length, int valueLength, int type, String key) throws IOException {
		StringFileInfo sfi = new StringFileInfo();

		sfi.setLength(length);
		sfi.setValueLength(valueLength);
		sfi.setType(type);
		sfi.setKey(key);
		dr.align(4);
		while (dr.getPosition() - initialPos < sfi.getLength()) {
			sfi.add(StringTable.read(dr));
		}
		return sfi;
	}

	public int rebuild() {
		valueLength = 0;

		int sum = alignDword(6 + Strings.getUtf16Length(key));
		for (StringTable table : tables) {
			sum += table.rebuild();
		}
		length = sum;
		return length;
	}

	@Override
	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(length);
		if (length == 0) {
			return;
		}
		dw.writeWord(valueLength);
		dw.writeWord(type);
		dw.writeUnicode(key);
		dw.align(4);
		for (StringTable table : tables) {
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

	public List<StringTable> getTables() {
		return tables;
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

	public int sizeOf() {
		int actualLength = alignDword(6 + Strings.getUtf16Length(key));
		for (StringTable t : tables) {
			actualLength += t.sizeOf();
		}
		return actualLength;
	}
}
