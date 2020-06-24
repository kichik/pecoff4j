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

/**
 * Used to store the stub program.
 */
public class DOSStub {
	private byte[] stub;

	public byte[] getStub() {
		return stub;
	}

	public void setStub(byte[] stub) {
		this.stub = stub;
	}
}
