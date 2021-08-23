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

public class ImageDataDirectory {
	private int virtualAddress;
	private int size;

	public int getVirtualAddress() {
		return virtualAddress;
	}

	public int getSize() {
		return size;
	}

	public void setVirtualAddress(int virtualAddress) {
		this.virtualAddress = virtualAddress;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ImageDataDirectory{" +
				"virtualAddress=" + Integer.toHexString(virtualAddress) +
				", size=" + size +
				'}';
	}
}
