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

import com.kichik.pecoff4j.io.IDataReader;

import java.io.IOException;
import java.util.ArrayList;

public class ImportDirectoryTable {
	private ArrayList imports = new ArrayList();

	public static ImportDirectoryTable read(IDataReader dr, int baseAddress) throws IOException {
		ImportDirectoryTable idt = new ImportDirectoryTable();
		ImportEntry ie = null;
		while ((ie = ImportEntry.read(dr)) != null) {
			idt.add(ie);
		}

		for (int i = 0; i < idt.size(); i++) {
			ImportEntry iee = idt.getEntry(i);
			if ((iee.getVal() & 0x80000000) != 0) {
				iee.setOrdinal(iee.getVal() & 0x7fffffff);
			} else {
				dr.jumpTo(iee.getVal() - baseAddress);
				dr.readWord(); // FIXME this is an index into the export table
				iee.setName(dr.readUtf());
			}
		}
		return idt;
	}

	public void add(ImportEntry entry) {
		imports.add(entry);
	}

	public int size() {
		return imports.size();
	}

	public ImportEntry getEntry(int index) {
		return (ImportEntry) imports.get(index);
	}
}
