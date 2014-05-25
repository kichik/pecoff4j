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

public class ImportDirectoryEntry
{
    private int importLookupTableRVA;
    private int timeDateStamp;
    private int forwarderChain;
    private int nameRVA;
    private int importAddressTableRVA;

    public int getImportLookupTableRVA() {
        return importLookupTableRVA;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getForwarderChain() {
        return forwarderChain;
    }

    public int getNameRVA() {
        return nameRVA;
    }

    public int getImportAddressTableRVA() {
        return importAddressTableRVA;
    }

    public void setImportLookupTableRVA(int importLookupTableRVA) {
        this.importLookupTableRVA = importLookupTableRVA;
    }

    public void setTimeDateStamp(int timeDateStamp) {
        this.timeDateStamp = timeDateStamp;
    }

    public void setForwarderChain(int forwarderChain) {
        this.forwarderChain = forwarderChain;
    }

    public void setNameRVA(int nameRVA) {
        this.nameRVA = nameRVA;
    }

    public void setImportAddressTableRVA(int importAddressTableRVA) {
        this.importAddressTableRVA = importAddressTableRVA;
    }
}
