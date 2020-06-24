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

import com.kichik.pecoff4j.util.Reflection;
import com.kichik.pecoff4j.util.Strings;

public class StringPair {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private String value;
	private int padding;

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
