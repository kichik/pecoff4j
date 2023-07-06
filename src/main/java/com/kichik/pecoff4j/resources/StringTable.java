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

import com.kichik.pecoff4j.RebuildableStructure;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Strings;

import static com.kichik.pecoff4j.util.Alignment.*;

/**
 * A string table. Holds language dependent string pairs.
 */
public class StringTable implements RebuildableStructure {
	/** The length of this structure (in bytes) */
	private int length;

	/** Always 0 */
	private int valueLength;

	/** 1 for text data, 0 for binary data */
	private int type;

	/** string containing language identifier (first half) and code page (second half) as 8 digit hex value */
	private String key;
	private final List<StringPair> strings = new ArrayList<>();

	public static StringTable read(IDataReader dr) throws IOException {
		int initialPos = dr.getPosition();

		StringTable vfi = new StringTable();
		vfi.setLength(dr.readWord());
		if (vfi.getLength() == 0) {
			return null;
		}
		vfi.setValueLength(dr.readWord());
		vfi.setType(dr.readWord());
		vfi.setKey(dr.readUnicode());
		dr.align(4);

		while (dr.getPosition() - initialPos < vfi.getLength()) {
			vfi.add(StringPair.read(dr));
		}

		return vfi;
	}

	@Override
	public int rebuild() {
		valueLength = 0;

		for (StringPair stringPair : strings) {
			stringPair.rebuild();
		}
		length = sizeOf();
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

		for (StringPair stringPair : strings) {
			stringPair.write(dw);
		}
	}

	@Deprecated
	public void add(StringPair string) {
		strings.add(string);
	}

	@Deprecated
	public int getCount() {
		return strings.size();
	}

	@Deprecated
	public StringPair getString(int index) {
		return strings.get(index);
	}

	public List<StringPair> getStrings() {
		return strings;
	}

	public int getLength() {
		return length;
	}

	public int getValueLength() {
		return valueLength;
	}

	public int getType() {
		return type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setValueLength(int valueLength) {
		this.valueLength = valueLength;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int sizeOf() {
		int actualLength = alignDword(6 + Strings.getUtf16Length(key));
		for (StringPair s : strings) {
			actualLength = alignDword(actualLength) + s.sizeOf();
		}
		return actualLength;
	}
}
