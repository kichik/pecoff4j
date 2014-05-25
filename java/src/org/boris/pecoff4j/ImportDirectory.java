/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j;

import java.util.ArrayList;
import java.util.List;

import org.boris.pecoff4j.util.DataObject;

public class ImportDirectory extends DataObject
{
    private List<ImportDirectoryEntry> entries = new ArrayList();
    private List<String> names = new ArrayList();
    private List<ImportDirectoryTable> nameTables = new ArrayList();
    private List<ImportDirectoryTable> addressTables = new ArrayList();

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
