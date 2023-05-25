/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.resources;

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VarFileInfo {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private final List<Var> vars = new ArrayList<>();

	public static VarFileInfo readPartial(IDataReader dr, int initialPos, int length, int valueLength, int type, String key) throws IOException {
		VarFileInfo vfi = new VarFileInfo();
		vfi.setLength(length);
		vfi.setValueLength(valueLength);
		vfi.setType(type);
		vfi.setKey(key);
		dr.align(4);

		while (dr.getPosition() < initialPos + length) {
			vfi.addVar(Var.read(dr));
		}
		return vfi;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(getLength());
		dw.writeWord(getValueLength());
		dw.writeWord(getType());
		dw.writeUnicode(getKey());
		dw.align(4);
		for (Var v : getVars()) {
			v.write(dw);
		}
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void setValueLength(int valueLength) {
		this.valueLength = valueLength;
	}

	public int getValueLength() {
		return valueLength;
	}

	public void setType(int type) {
		this.type = type;
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

	public void addVar(Var v) {
		vars.add(v);
	}

	public List<Var> getVars() {
		return vars;
	}

}
