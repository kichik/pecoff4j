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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kichik.pecoff4j.WritableStructure;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
import com.kichik.pecoff4j.util.Reflection;

public class GroupIconDirectory implements WritableStructure {
	private int reserved;
	private int type;
	private List<GroupIconDirectoryEntry> entries = new ArrayList<>();

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCount() {
		return entries.size();
	}

	public GroupIconDirectoryEntry getEntry(int index) {
		return entries.get(index);
	}

	public List<GroupIconDirectoryEntry> getEntries() {
		return entries;
	}

	@Override
	public String toString() {
		return Reflection.toString(this);
	}

	public static GroupIconDirectory read(byte[] data) throws IOException {
		return read(new DataReader(data));
	}

	public static GroupIconDirectory read(IDataReader dr) throws IOException {
		GroupIconDirectory gi = new GroupIconDirectory();
		gi.reserved = dr.readWord();
		gi.type = dr.readWord();
		int count = dr.readWord();
		for (int i = 0; i < count; i++) {
			gi.entries.add(GroupIconDirectoryEntry.read(dr));
		}

		return gi;
	}

	@Override
	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(reserved);
		dw.writeWord(type);
		dw.writeWord(entries.size());
		for (GroupIconDirectoryEntry entry : entries) {
			entry.write(dw);
		}
	}
}
