package com.kichik.pecoff4j.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of language and code page identifier pairs.
 *
 * See <a href="https://learn.microsoft.com/en-us/windows/win32/menurc/var-str">Var structure</a> for details.
 */
public class Var {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private final List<Integer> values = new ArrayList<>();

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
