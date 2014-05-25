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

public class COFFHeader
{
    private int machine;
    private int numberOfSections;
    private int timeDateStamp;
    private int pointerToSymbolTable;
    private int numberOfSymbols;
    private int sizeOfOptionalHeader;
    private int characteristics;

    public int getMachine() {
        return machine;
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public int getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getPointerToSymbolTable() {
        return pointerToSymbolTable;
    }

    public int getNumberOfSymbols() {
        return numberOfSymbols;
    }

    public int getSizeOfOptionalHeader() {
        return sizeOfOptionalHeader;
    }

    public int getCharacteristics() {
        return characteristics;
    }

    public void setMachine(int machine) {
        this.machine = machine;
    }

    public void setNumberOfSections(int numberOfSections) {
        this.numberOfSections = numberOfSections;
    }

    public void setTimeDateStamp(int timeDateStamp) {
        this.timeDateStamp = timeDateStamp;
    }

    public void setPointerToSymbolTable(int pointerToSymbolTable) {
        this.pointerToSymbolTable = pointerToSymbolTable;
    }

    public void setNumberOfSymbols(int numberOfSymbols) {
        this.numberOfSymbols = numberOfSymbols;
    }

    public void setSizeOfOptionalHeader(int sizeOfOptionalHeader) {
        this.sizeOfOptionalHeader = sizeOfOptionalHeader;
    }

    public void setCharacteristics(int characteristics) {
        this.characteristics = characteristics;
    }
}
