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

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Reflection;
import com.kichik.pecoff4j.util.Strings;

import java.io.IOException;

public class StringPair {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private String value;
	private int padding;

	public static StringPair read(IDataReader dr) throws IOException {
		int initialPos = dr.getPosition();

		StringPair sp = new StringPair();
		sp.setLength(dr.readWord());
		sp.setValueLength(dr.readWord());
		sp.setType(dr.readWord());
		sp.setKey(dr.readUnicode());
		sp.setPadding(dr.align(4));

		int remainingWords = (sp.getLength() - (dr.getPosition() - initialPos)) / 2;
		int valueLength = sp.getValueLength();
		if (sp.getType() == 0) // wType == 0 => binary; wLength is in bytes
			valueLength /= 2;
		if (valueLength > remainingWords)
			valueLength = remainingWords;
		sp.setValue(dr.readUnicode(valueLength).trim());

		int remainingBytes = (sp.getLength() - (dr.getPosition() - initialPos));
		dr.skipBytes(remainingBytes);
		dr.align(4);
		return sp;
	}

	public void write(IDataWriter dw) throws IOException {
		int initialPos = dw.getPosition();

		dw.writeWord(getLength());
		dw.writeWord(getValueLength());
		dw.writeWord(getType());
		dw.writeUnicode(getKey());
		dw.align(4);

		int remainingWords = (getLength() - (dw.getPosition() - initialPos)) / 2;
		int valueLength = getValueLength();
		if (getType() == 0) // wType == 0 => binary; wLength is in bytes
			valueLength /= 2;
		if (valueLength > remainingWords)
			valueLength = remainingWords;
		dw.writeUnicode(getValue(), valueLength);

		int remainingBytes = (getLength() - (dw.getPosition() - initialPos));
		dw.writeByte(0, remainingBytes);
		dw.align(4);
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Reflection.toString(this);
	}

	public int sizeOf() {
		return 6 + padding + Strings.getUtf16Length(key)
				+ Strings.getUtf16Length(value);
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}
}
