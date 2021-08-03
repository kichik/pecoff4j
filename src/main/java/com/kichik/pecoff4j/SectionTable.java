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
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.kichik.pecoff4j.util.IntMap;

public class SectionTable {
	// Known section names
	public static final String RESOURCE_TABLE = ".rsrc";
	public static final String EXPORT_TABLE = ".edata";
	public static final String IMPORT_TABLE = ".idata";
	public static final String LOAD_CONFIG_TABLE = ".rdata";

	// Data
	private final List<SectionHeader> headers = new ArrayList<>();
	private final IntMap sections = new IntMap();
	private RVAConverter rvaConverter;

	public void add(SectionHeader header) {
		headers.add(header);
	}

	public int getNumberOfSections() {
		return headers.size();
	}

	public SectionHeader getHeader(int index) {
		return headers.get(index);
	}

	public SectionData getSection(int index) {
		return (SectionData) sections.get(index);
	}

	public void put(int index, SectionData data) {
		sections.put(index, data);
	}

	public RVAConverter getRVAConverter() {
		return rvaConverter;
	}

	public void setRvaConverter(RVAConverter rvaConverter) {
		this.rvaConverter = rvaConverter;
	}

	public int getFirstSectionRawDataPointer() {
		int pointer = 0;
		for (SectionHeader sh : headers)
		{
			if (sh.getVirtualSize() > 0
					&& (pointer == 0 || sh.getPointerToRawData() < pointer))
			{
				pointer = sh.getPointerToRawData();
			}
		}

		return pointer;
	}

	public SectionHeader getLastSectionRawPointerSorted() {
		SectionHeader[] headers = getHeadersPointerSorted();
		if (headers == null || headers.length == 0)
			return null;
		return headers[headers.length - 1];
	}

	public SectionHeader[] getHeadersPointerSorted() {
		List<SectionHeader> headers = new ArrayList<>();
		for (int i = 0; i < getNumberOfSections(); i++) {
			headers.add(getHeader(i));
		}

		SectionHeader[] sorted = headers.toArray(new SectionHeader[0]);
		Arrays.sort(sorted, Comparator.comparingInt(SectionHeader::getVirtualAddress));

		return sorted;
	}

	public SectionHeader findHeader(String name) {
		for (SectionHeader sh : headers) {
			if (sh.getName().equals(name))
				return sh;
		}

		return null;
	}

	public SectionData findSection(String name) {
		for (int i = 0; i < headers.size(); i++) {
			SectionHeader sh = headers.get(i);
			if (sh.getName().equals(name))
				return (SectionData) sections.get(i);
		}

		return null;
	}

}
