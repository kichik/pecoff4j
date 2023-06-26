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

public class IconDirectory {
	private int reserved;
	private int type;
	private ArrayList entries = new ArrayList();

	public static IconDirectory read(IDataReader dr) throws IOException {
		IconDirectory gi = new IconDirectory();
		gi.setReserved(dr.readWord());
		gi.setType(dr.readWord());
		int count = dr.readWord();
		for (int i = 0; i < count; i++) {
			gi.add(IconDirectoryEntry.read(dr));
		}

		return gi;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(getReserved());
		dw.writeWord(getType());
		dw.writeWord(getCount());
		for (int i = 0; i < getCount(); i++) {
			getEntry(i).write(dw);
		}
	}

	public void add(IconDirectoryEntry entry) {
		entries.add(entry);
	}

	public int getCount() {
		return entries.size();
	}

	public IconDirectoryEntry getEntry(int index) {
		return (IconDirectoryEntry) entries.get(index);
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getReserved() {
		return reserved;
	}

	public int getType() {
		return type;
	}

	public int sizeOf() {
		return 6 + entries.size() * IconDirectoryEntry.sizeOf();
	}
}
