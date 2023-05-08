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

import java.util.ArrayList;
import java.util.List;

public class VarFileInfo {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private final List<Var> vars = new ArrayList<>();

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
