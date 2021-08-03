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

import java.util.ArrayList;
import java.util.List;

import com.kichik.pecoff4j.util.Strings;

public class StringTable {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private int padding;
	private final List<StringPair> strings = new ArrayList<>();

	public void add(StringPair string) {
		strings.add(string);
	}

	public int getCount() {
		return strings.size();
	}

	public StringPair getString(int index) {
		return strings.get(index);
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

	public int getPadding() {
		return padding;
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

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public int sizeOf() {
		int actualLength = 6 + padding + Strings.getUtf16Length(key);
		for (StringPair s : strings)
			actualLength += s.sizeOf();
		return actualLength;
	}
}
