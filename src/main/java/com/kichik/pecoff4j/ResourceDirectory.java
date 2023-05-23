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

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.util.DataObject;

public class ResourceDirectory extends DataObject {
	private ResourceDirectoryTable table;
	private List<ResourceEntry> entries = new ArrayList();

	public static ResourceDirectory read(IDataReader dr, int baseAddress) throws IOException {
		ResourceDirectory d = new ResourceDirectory();
		d.setTable(ResourceDirectoryTable.read(dr));
		int ne = d.getTable().getNumNameEntries()
				+ d.getTable().getNumIdEntries();
		for (int i = 0; i < ne; i++) {
			d.add(ResourceEntry.read(dr, baseAddress));
		}

		return d;
	}

	public ResourceDirectoryTable getTable() {
		return table;
	}

	public void setTable(ResourceDirectoryTable table) {
		this.table = table;
	}

	public void add(ResourceEntry entry) {
		this.entries.add(entry);
	}

	public ResourceEntry get(int index) {
		return entries.get(index);
	}

	public int size() {
		return entries.size();
	}
}