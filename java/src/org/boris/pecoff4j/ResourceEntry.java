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

public class ResourceEntry {
	private int id;
	private String name;
    private int offset;
	private byte[] data;
	private ResourceDirectory directory;
    private int dataRVA;
	private int codePage;
	private int reserved;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public ResourceDirectory getDirectory() {
		return directory;
	}

	public void setDirectory(ResourceDirectory directory) {
		this.directory = directory;
	}

    public int getDataRVA() {
        return dataRVA;
    }

    public void setDataRVA(int dataRVA) {
        this.dataRVA = dataRVA;
    }

	public int getCodePage() {
		return codePage;
	}

	public void setCodePage(int codePage) {
		this.codePage = codePage;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}
}
