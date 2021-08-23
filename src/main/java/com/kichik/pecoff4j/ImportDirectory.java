/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kichik.pecoff4j.util.DataObject;

public class ImportDirectory extends DataObject implements Iterable<ImportDirectoryEntry> {
	private final List<ImportDirectoryEntry> entries = new ArrayList<>();
	private final List<String> names = new ArrayList<>();
	private final List<ImportDirectoryTable> nameTables = new ArrayList<>();
	private final List<ImportDirectoryTable> addressTables = new ArrayList<>();

	public void add(ImportDirectoryEntry entry) {
		entries.add(entry);
	}

	public void add(String name, ImportDirectoryTable names,
			ImportDirectoryTable addresses) {
		this.names.add(name);
		nameTables.add(names);
		addressTables.add(addresses);
	}

	public int size() {
		return entries.size();
	}

	public String getName(int index) {
		return names.get(index);
	}

	public ImportDirectoryTable getNameTable(int index) {
		return nameTables.get(index);
	}

	public ImportDirectoryTable getAddressTable(int index) {
		return addressTables.get(index);
	}

	public ImportDirectoryEntry getEntry(int index) {
		return entries.get(index);
	}

	@Override
	public Iterator<ImportDirectoryEntry> iterator() {
		return entries.iterator();
	}
}
