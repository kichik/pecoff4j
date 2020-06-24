/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.util;

public class DataObject {
	private byte[] buffer;

	public void set(byte[] buffer) {
		this.buffer = buffer;
	}

	public byte[] get() {
		return buffer;
	}
}
