/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *	 Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.IDataReader;

import java.io.IOException;

public class ResourceEntry {
	private int id;
	private String name;
	private int offset;
	private byte[] data;
	private ResourceDirectory directory;
	private int dataRVA;
	private int codePage;
	private int reserved;

	public static ResourceEntry read(IDataReader dr, int baseAddress) throws IOException {
		ResourceEntry re = new ResourceEntry();
		int id = dr.readDoubleWord();
		int offset = dr.readDoubleWord();
		re.setOffset(offset);
		int pos = dr.getPosition();
		if ((id & 0x80000000) != 0) {
			dr.jumpTo(id & 0x7fffffff);
			re.setName(dr.readUnicode(dr.readWord()));
		} else {
			re.setId(id);
		}
		if ((offset & 0x80000000) != 0) {
			dr.jumpTo(offset & 0x7fffffff);
			re.setDirectory(ResourceDirectory.read(dr, baseAddress));
		} else {
			dr.jumpTo(offset);
			int rva = dr.readDoubleWord();
			int size = dr.readDoubleWord();
			int cp = dr.readDoubleWord();
			int res = dr.readDoubleWord();
			re.setDataRVA(rva);
			re.setCodePage(cp);
			re.setReserved(res);
			dr.jumpTo(rva - baseAddress);
			byte[] b = new byte[size];
			dr.read(b);
			re.setData(b);
		}
		dr.jumpTo(pos);
		return re;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public ResourceDirectory getDirectory() {
		return directory;
	}

	public void setDirectory(ResourceDirectory directory) {
		this.directory = directory;
	}

	public int getDataRVA() {
		return dataRVA;
	}

	public void setDataRVA(int dataRVA) {
		this.dataRVA = dataRVA;
	}

	public int getCodePage() {
		return codePage;
	}

	public void setCodePage(int codePage) {
		this.codePage = codePage;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}
}
