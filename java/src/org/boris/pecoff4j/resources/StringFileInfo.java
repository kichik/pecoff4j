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
package org.boris.pecoff4j.resources;

import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.util.Strings;

public class StringFileInfo {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private int padding;
	private List<StringTable> tables = new ArrayList<StringTable>();

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
