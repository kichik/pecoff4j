/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.io;

import com.kichik.pecoff4j.PE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PEAssembler {
	public static byte[] toBytes(PE pe) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		write(pe, bos);
		return bos.toByteArray();
	}

	public static void write(PE pe, String filename) throws IOException {
		write(pe, new FileOutputStream(filename));
	}

	public static void write(PE pe, File file) throws IOException {
		write(pe, new FileOutputStream(file));
	}

	public static void write(PE pe, OutputStream os) throws IOException {
		DataWriter dw = new DataWriter(os);
		write(pe, dw);
		dw.flush();
	}

	/**
	 * @deprecated use {@link PE#write(IDataWriter)} instead
	 */
	@Deprecated
	public static void write(PE pe, IDataWriter dw) throws IOException {
		pe.write(dw);
	}
}
