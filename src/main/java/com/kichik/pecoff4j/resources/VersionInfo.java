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

import com.kichik.pecoff4j.RebuildableStructure;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Strings;

import java.io.IOException;

import static com.kichik.pecoff4j.util.Alignment.*;

/**
 * File version information structure.
 *
 * See <a href="https://learn.microsoft.com/en-us/windows/win32/menurc/vs-versioninfo">VS_VERSIONINFO structure</a> for details.
 */
public class VersionInfo implements RebuildableStructure {
	/** The length of this structure without padding at the end */
	private int length;

	/** The length of the FixedFileInfo or 0 if it does not exist */
	private int valueLength;

	/** 1 for text data, 0 for binary data */
	private int type;

	/** Must be "VS_VERSION_INFO" */
	private String key;

	/** The optional FixedFileInfo structure */
	private FixedFileInfo fixedFileInfo;

	/** The optional StringFileInfo structure */
	private StringFileInfo stringFileInfo;

	/** The optional VarFileInfo structure */
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
		if (vi.getValueLength() != 0) {
			vi.setFixedFileInfo(FixedFileInfo.read(dr));
			dr.align(4);
		}

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

	@Override
	public int rebuild() {
		int sum = alignDword(6 + Strings.getUtf16Length(key));

		if (fixedFileInfo != null) {
			valueLength = FixedFileInfo.sizeOf();
			sum += valueLength;
		} else {
			valueLength = 0;
		}

		if (stringFileInfo != null) {
			sum = alignDword(sum);
			sum += stringFileInfo.rebuild();
		}
		if (varFileInfo != null) {
			sum = alignDword(sum);
			sum += varFileInfo.rebuild();
		}
		length = sum;
		return length;
	}

	@Override
	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(length);
		dw.writeWord(valueLength);
		dw.writeWord(type);
		dw.writeUnicode(key);
		dw.align(4);
		if (fixedFileInfo != null) {
			fixedFileInfo.write(dw);
		}

		if (stringFileInfo != null) {
			dw.align(4);
			stringFileInfo.write(dw);
		}
		if (varFileInfo != null) {
			dw.align(4);
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
