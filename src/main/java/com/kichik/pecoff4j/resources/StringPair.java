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

import com.kichik.pecoff4j.WritableStructure;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Reflection;
import com.kichik.pecoff4j.util.Strings;

import java.io.IOException;

import static com.kichik.pecoff4j.util.Alignment.*;

/**
 * A string pair.
 *
 * See <a href="https://learn.microsoft.com/en-us/windows/win32/menurc/string-str">String structure</a> for details.
 */
public class StringPair implements WritableStructure {
	/** The length of this structure (in bytes) */
	private int length;

	/** The length of this value String (in words) */
	private int valueLength;

	/** 1 for text data, 0 for binary data */
	private int type;
	private String key;
	private String value;

	public static StringPair read(IDataReader dr) throws IOException {
		int initialPos = dr.getPosition();

		StringPair sp = new StringPair();
		sp.setLength(dr.readWord());
		sp.setValueLength(dr.readWord());
		sp.setType(dr.readWord());
		sp.setKey(dr.readUnicode());
		dr.align(4);

		int remainingWords = (sp.getLength() - (dr.getPosition() - initialPos)) / 2;
		int valueLength = sp.getValueLength();
		if (sp.getType() == 0) { // wType == 0 => binary; wLength is in bytes
			valueLength /= 2;
		}
		if (valueLength > remainingWords) {
			valueLength = remainingWords;
		}
		sp.setValue(dr.readUnicode(valueLength - 1));

		int remainingBytes = (sp.getLength() - (dr.getPosition() - initialPos));
		dr.skipBytes(remainingBytes);
		dr.align(4);
		return sp;
	}

	public int rebuild() {
		if (value.isEmpty()) {
			// sometimes single null-byte is stored, so valueLength could also be 1 or 2 (depending on type)
			valueLength = 0;
		}
		else if (type == 0) { // wType == 0 => binary; wLength is in bytes
			valueLength = Strings.getUtf16Length(value);
		} else {
			valueLength = Strings.getUtf16Length(value) / 2;
		}
		length = sizeOf();
		return length;
	}

	@Override
	public void write(IDataWriter dw) throws IOException {
		int initialPos = dw.getPosition();

		dw.writeWord(length);
		dw.writeWord(valueLength);
		dw.writeWord(type);
		dw.writeUnicode(key);
		dw.align(4);

		int remainingWords = (length - (dw.getPosition() - initialPos)) / 2;
		int valueLengthInBytes = valueLength;
		if (getType() == 0) {// wType == 0 => binary; wLength is in bytes
			valueLengthInBytes /= 2;
		}
		if (valueLengthInBytes > remainingWords) {
			valueLengthInBytes = remainingWords;
		}
		dw.writeUnicode(value, valueLengthInBytes);

		int remainingBytes = (length - (dw.getPosition() - initialPos));
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
		return alignDword(6 + Strings.getUtf16Length(key))
				+ (value.isEmpty() ? 0 : Strings.getUtf16Length(value));
	}

}
