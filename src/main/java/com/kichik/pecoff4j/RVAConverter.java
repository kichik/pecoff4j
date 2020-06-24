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

public class RVAConverter {
	private int[] virtualAddress;
	private int[] pointerToRawData;

	public RVAConverter(int[] virtualAddress, int[] pointerToRawData) {
		this.virtualAddress = virtualAddress;
		this.pointerToRawData = pointerToRawData;
	}

	public int convertVirtualAddressToRawDataPointer(int virtualAddress) {
		for (int i = 0; i < this.virtualAddress.length; i++) {
			if (virtualAddress < this.virtualAddress[i]) {
				if (i > 0) {
					int prd = pointerToRawData[i - 1];
					int vad = this.virtualAddress[i - 1];
					return prd + virtualAddress - vad;
				} else {
					return virtualAddress;
				}
			}
		}

		// Hit the last item
		int prd = this.pointerToRawData[this.virtualAddress.length - 1];
		int vad = this.virtualAddress[this.virtualAddress.length - 1];
		return prd + virtualAddress - vad;
	}
}
