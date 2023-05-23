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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.util.DataObject;

public class ImportDirectory extends DataObject {
	private List<ImportDirectoryEntry> entries = new ArrayList();
	private List<String> names = new ArrayList();
	private List<ImportDirectoryTable> nameTables = new ArrayList();
	private List<ImportDirectoryTable> addressTables = new ArrayList();

	public static ImportDirectory read(byte[] b, int baseAddress)
			throws IOException {
		DataReader dr = new DataReader(b);
		ImportDirectory id = new ImportDirectory();
		ImportDirectoryEntry ide = null;
		while ((ide = ImportDirectoryEntry.read(dr)) != null) {
			id.add(ide);
		}

		/*
		 * FIXME - name table refer to data outside image directory for (int i =
		 * 0; i < id.size(); i++) { ImportDirectoryEntry e = id.getEntry(i);
		 * dr.jumpTo(e.getNameRVA() - baseAddress); String name = dr.readUtf();
		 * dr.jumpTo(e.getImportLookupTableRVA() - baseAddress);
		 * ImportDirectoryTable nt = readImportDirectoryTable(dr, baseAddress);
		 * dr.jumpTo(e.getImportAddressTableRVA() - baseAddress);
		 * ImportDirectoryTable at = null; // readImportDirectoryTable(dr, //
		 * baseAddress); id.add(name, nt, at); }
		 */

		return id;
	}

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
}
