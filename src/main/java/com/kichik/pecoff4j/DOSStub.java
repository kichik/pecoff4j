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

/**
 * Used to store the stub program.
 */
public class DOSStub {
	private byte[] stub;

	public static DOSStub read(DOSHeader header, IDataReader dr)
			throws IOException {
		DOSStub ds = new DOSStub();
		int pos = dr.getPosition();
		int add = header.getAddressOfNewExeHeader();
		byte[] stub = new byte[add - pos];
		dr.read(stub);
		ds.setStub(stub);
		return ds;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeBytes(getStub());
	}

	public byte[] getStub() {
		return stub;
	}

	public void setStub(byte[] stub) {
		this.stub = stub;
	}
}
