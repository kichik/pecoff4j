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
import com.kichik.pecoff4j.io.IDataWriter;

import java.io.IOException;

public class COFFHeader {
	private int machine;
	private int numberOfSections;
	private int timeDateStamp;
	private int pointerToSymbolTable;
	private int numberOfSymbols;
	private int sizeOfOptionalHeader;
	private int characteristics;

	public static COFFHeader read(IDataReader dr) throws IOException {
		COFFHeader h = new COFFHeader();
		h.setMachine(dr.readWord());
		h.setNumberOfSections(dr.readWord());
		h.setTimeDateStamp(dr.readDoubleWord());
		h.setPointerToSymbolTable(dr.readDoubleWord());
		h.setNumberOfSymbols(dr.readDoubleWord());
		h.setSizeOfOptionalHeader(dr.readWord());
		h.setCharacteristics(dr.readWord());
		return h;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeWord(getMachine());
		dw.writeWord(getNumberOfSections());
		dw.writeDoubleWord(getTimeDateStamp());
		dw.writeDoubleWord(getPointerToSymbolTable());
		dw.writeDoubleWord(getNumberOfSymbols());
		dw.writeWord(getSizeOfOptionalHeader());
		dw.writeWord(getCharacteristics());
	}

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
