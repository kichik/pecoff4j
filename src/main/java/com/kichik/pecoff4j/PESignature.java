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

import java.util.Arrays;

public class PESignature {
	private static final byte[] expected1 = new byte[] { 0x50, 0x45, 0x00, 0x00 };
	private static final byte[] expected2 = new byte[] { 0x50, 0x69, 0x00, 0x00 };
	private byte[] signature;

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public boolean isValid() {
		return Arrays.equals(expected1, signature)
				|| Arrays.equals(expected2, signature);
	}
}
