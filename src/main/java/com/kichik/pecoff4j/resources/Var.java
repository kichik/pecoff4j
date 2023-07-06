package com.kichik.pecoff4j.resources;

import com.kichik.pecoff4j.RebuildableStructure;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Strings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.kichik.pecoff4j.util.Alignment.*;

/**
 * A list of language and code page identifier pairs.
 *
 * See <a href="https://learn.microsoft.com/en-us/windows/win32/menurc/var-str">Var structure</a> for details.
 */
public class Var implements RebuildableStructure {
	/** The length of this structure (in bytes) */
	private int length;

	/** The length of the values (in bytes) */
	private int valueLength;

	/** 1 for text data, 0 for binary data */
	private int type;
	private String key;
	private final List<Integer> values = new ArrayList<>();

	public static Var read(IDataReader dr) throws IOException {
		Var v = new Var();
		int initialPos = dr.getPosition();
		v.setLength(dr.readWord());
		v.setValueLength(dr.readWord());
		v.setType(dr.readWord());
		v.setKey(dr.readUnicode());
		dr.align(4);
		while (dr.getPosition() < initialPos + v.getLength()) {
			v.addValue(dr.readDoubleWord());
		}
		return v;
	}

	@Override
	public int rebuild() {
		valueLength = values.size() * 4;
		length = alignDword(6 + Strings.getUtf16Length(key)) + valueLength;
		return length;
	}

	@Override
	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(length);
		dw.writeWord(valueLength);
		dw.writeWord(type);
		dw.writeUnicode(key);
		dw.align(4);
		for (Integer value : values) {
			dw.writeDoubleWord(value);
		}
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

	public List<Integer> getValues() {
		return values;
	}

	public void addValue(int value) {
		values.add(value);
	}
}
