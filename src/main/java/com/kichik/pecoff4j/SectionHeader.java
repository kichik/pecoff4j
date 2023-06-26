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

public class SectionHeader {
	private String name;
	private int virtualSize;
	private int virtualAddress;
	private int sizeOfRawData;
	private int pointerToRawData;
	private int pointerToRelocations;
	private int pointerToLineNumbers;
	private int numberOfRelocations;
	private int numberOfLineNumbers;
	private int characteristics;

	public static SectionHeader read(IDataReader dr) throws IOException {
		SectionHeader sh = new SectionHeader();
		sh.setName(dr.readUtf(8));
		sh.setVirtualSize(dr.readDoubleWord());
		sh.setVirtualAddress(dr.readDoubleWord());
		sh.setSizeOfRawData(dr.readDoubleWord());
		sh.setPointerToRawData(dr.readDoubleWord());
		sh.setPointerToRelocations(dr.readDoubleWord());
		sh.setPointerToLineNumbers(dr.readDoubleWord());
		sh.setNumberOfRelocations(dr.readWord());
		sh.setNumberOfLineNumbers(dr.readWord());
		sh.setCharacteristics(dr.readDoubleWord());
		return sh;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeUtf(getName(), 8);
		dw.writeDoubleWord(getVirtualSize());
		dw.writeDoubleWord(getVirtualAddress());
		dw.writeDoubleWord(getSizeOfRawData());
		dw.writeDoubleWord(getPointerToRawData());
		dw.writeDoubleWord(getPointerToRelocations());
		dw.writeDoubleWord(getPointerToLineNumbers());
		dw.writeWord(getNumberOfRelocations());
		dw.writeWord(getNumberOfLineNumbers());
		dw.writeDoubleWord(getCharacteristics());
	}

	public String getName() {
		return name;
	}

	public int getVirtualSize() {
		return virtualSize;
	}

	public int getVirtualAddress() {
		return virtualAddress;
	}

	public int getSizeOfRawData() {
		return sizeOfRawData;
	}

	public int getPointerToRawData() {
		return pointerToRawData;
	}

	public int getPointerToRelocations() {
		return pointerToRelocations;
	}

	public int getPointerToLineNumbers() {
		return pointerToLineNumbers;
	}

	public int getNumberOfRelocations() {
		return numberOfRelocations;
	}

	public int getNumberOfLineNumbers() {
		return numberOfLineNumbers;
	}

	public int getCharacteristics() {
		return characteristics;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVirtualSize(int virtualSize) {
		this.virtualSize = virtualSize;
	}

	public void setVirtualAddress(int virtualAddress) {
		this.virtualAddress = virtualAddress;
	}

	public void setSizeOfRawData(int sizeOfRawData) {
		this.sizeOfRawData = sizeOfRawData;
	}

	public void setPointerToRawData(int pointerToRawData) {
		this.pointerToRawData = pointerToRawData;
	}

	public void setPointerToRelocations(int pointerToRelocations) {
		this.pointerToRelocations = pointerToRelocations;
	}

	public void setPointerToLineNumbers(int pointerToLineNumbers) {
		this.pointerToLineNumbers = pointerToLineNumbers;
	}

	public void setNumberOfRelocations(int numberOfRelocations) {
		this.numberOfRelocations = numberOfRelocations;
	}

	public void setNumberOfLineNumbers(int numberOfLineNumbers) {
		this.numberOfLineNumbers = numberOfLineNumbers;
	}

	public void setCharacteristics(int characteristics) {
		this.characteristics = characteristics;
	}
}
