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
import com.kichik.pecoff4j.io.ResourceAssembler;

import java.io.IOException;

public class VersionInfo {
	private int length;
	private int valueLength;
	private int type;
	private String key;
	private FixedFileInfo fixedFileInfo;
	private StringFileInfo stringFileInfo;
	private VarFileInfo varFileInfo;

	public static VersionInfo read(IDataReader dr)
			throws IOException {
		int versionInfoPos = dr.getPosition();
		VersionInfo vi = new VersionInfo();
		vi.setLength(dr.readWord());
		vi.setValueLength(dr.readWord());
		vi.setType(dr.readWord());
		vi.setKey(dr.readUnicode());
		dr.align(4);
		vi.setFixedFileInfo(FixedFileInfo.read(dr));
		dr.align(4);

		while (dr.getPosition() < versionInfoPos + vi.getLength()) {
			int initialPos = dr.getPosition();

			int length = dr.readWord();
			if (length == 0) {
				break;
			}
			int valueLength = dr.readWord();
			int type = dr.readWord();
			String key = dr.readUnicode();
			if ("VarFileInfo".equals(key)) {
				vi.setVarFileInfo(VarFileInfo.readPartial(dr, initialPos, length, valueLength, type, key));
			} else if ("StringFileInfo".equals(key)) {
				vi.setStringFileInfo(StringFileInfo.readPartial(dr, initialPos, length, valueLength, type, key));
			} else {
				dr.jumpTo(initialPos + length);
				break;
			}
		}

		return vi;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(getLength());
		dw.writeWord(getValueLength());
		dw.writeWord(getType());
		dw.writeUnicode(getKey());
		dw.align(4);
		ResourceAssembler.write(getFixedFileInfo(), dw);

		StringFileInfo stringFileInfo = getStringFileInfo();
		if (stringFileInfo != null) {
			stringFileInfo.write(dw);
		}
		VarFileInfo varFileInfo = getVarFileInfo();
		if (varFileInfo != null) {
			varFileInfo.write(dw);
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

	public FixedFileInfo getFixedFileInfo() {
		return fixedFileInfo;
	}

	public void setFixedFileInfo(FixedFileInfo fixedFileInfo) {
		this.fixedFileInfo = fixedFileInfo;
	}

	public StringFileInfo getStringFileInfo() {
		return stringFileInfo;
	}

	public void setStringFileInfo(StringFileInfo stringFileInfo) {
		this.stringFileInfo = stringFileInfo;
	}

	public VarFileInfo getVarFileInfo() {
		return varFileInfo;
	}

	public void setVarFileInfo(VarFileInfo varFileInfo) {
		this.varFileInfo = varFileInfo;
	}
}
