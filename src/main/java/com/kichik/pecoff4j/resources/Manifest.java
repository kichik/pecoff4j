/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.resources;

import com.kichik.pecoff4j.io.IDataReader;
import com.kichik.pecoff4j.io.IDataWriter;

import java.io.IOException;

public class Manifest {
	private String str;

	public static Manifest read(IDataReader dr, int length)
			throws IOException {
		Manifest mf = new Manifest();
		mf.set(dr.readUtf(length));
		return mf;
	}

	public void write(IDataWriter dw) throws IOException {
		dw.writeUtf(get(), get().length());
	}

	public String get() {
		return str;
	}

	public void set(String str) {
		this.str = str;
	}
}
