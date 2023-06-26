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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;
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

	public void write(IDataWriter dw) throws IOException {
		List<ResourceDirectory> dirs = new ArrayList<>();
		List<ResourceEntry> entries = new ArrayList<>();
		flattenInto(dirs, entries);

		for (ResourceDirectory dir : dirs) {
			dir.getTable().write(dw);
			for (int i = 0; i < dir.size(); i++) {
				dir.get(i).writeNonLeaf(dw);
			}
		}
		for (ResourceEntry re : entries) {
			re.writeLeaf(dw);
		}
		Collections.sort(entries, Comparator.comparing(ResourceEntry::getDataRVA));
		for (ResourceDirectory dir : dirs) {
			for (int i = 0; i < dir.size(); i++) {
				dir.get(i).writeName(dw);
			}
		}
		for (ResourceEntry re : entries) {
			re.writeName(dw);
		}
		dw.align(4);
		for (ResourceEntry re : entries) {
			re.writeData(dw);
			dw.align(4);
		}
	}

	/**
	 * Traverse tree in breath-first order and fill directories and entries into given lists.
	 */
	private void flattenInto(List<ResourceDirectory> dirs, List<ResourceEntry> entries) {
		Queue<ResourceDirectory> toProcess = new ArrayDeque<>();
		toProcess.add(this);
		dirs.add(this);

		while (!toProcess.isEmpty()) {
			ResourceDirectory dir = toProcess.poll();
			for (int i = 0; i < dir.size(); i++) {
				ResourceEntry entry = dir.get(i);
				if (entry.getDirectory() != null) {
					toProcess.add(entry.getDirectory());
					dirs.add(entry.getDirectory());
				} else {
					entries.add(entry);
				}
			}
		}
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