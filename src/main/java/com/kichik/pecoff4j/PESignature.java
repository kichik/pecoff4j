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
import java.util.Arrays;

public class PESignature {
	private static byte[] expected1 = new byte[] { 0x50, 0x45, 0x00, 0x00 };
	private static byte[] expected2 = new byte[] { 0x50, 0x69, 0x00, 0x00 };
	private byte[] signature;

	public static PESignature read(IDataReader dr) throws IOException {
		PESignature ps = new PESignature();
		byte[] signature = new byte[4];
		dr.read(signature);
		ps.setSignature(signature);
		return ps;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeBytes(getSignature());
	}

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
