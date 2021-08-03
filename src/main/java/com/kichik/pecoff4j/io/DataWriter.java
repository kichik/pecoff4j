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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DataWriter implements IDataWriter {
	private final BufferedOutputStream out;
	private int position;

	public DataWriter(File output) throws FileNotFoundException {
		this(new FileOutputStream(output));
	}

	public DataWriter(OutputStream out) {
		this.out = new BufferedOutputStream(out);
	}

	@Override
	public void writeByte(int b) throws IOException {
		out.write(b);
		position++;
	}

	@Override
	public void writeByte(int b, int count) throws IOException {
		for (int i = 0; i < count; i++) {
			out.write(b);
		}
		position += count;
	}

	@Override
	public void writeBytes(byte[] b) throws IOException {
		out.write(b);
		position += b.length;
	}

	@Override
	public void writeDoubleWord(int dw) throws IOException {
		out.write(dw & 0xff);
		out.write(dw >> 8 & 0xff);
		out.write(dw >> 16 & 0xff);
		out.write(dw >> 24 & 0xff);
		position += 4;
	}

	@Override
	public void writeWord(int w) throws IOException {
		out.write(w & 0xff);
		out.write(w >> 8 & 0xff);
		position += 2;
	}

	@Override
	public void writeLong(long l) throws IOException {
		writeDoubleWord((int) l);
		writeDoubleWord((int) (l >> 32));
	}

	public void flush() throws IOException {
		out.flush();
	}

	public void close() throws IOException {
		out.close();
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public void writeUtf(String s, int len) throws IOException {
		byte[] b = s.getBytes(); // FIXME sort out charset
		int i = 0;
		for (; i < b.length && i < len; i++) {
			out.write(b[i]);
		}
		for (; i < len; i++) {
			out.write(0);
		}
		position += len;
	}

	@Override
	public void writeUtf(String s) throws IOException {
		byte[] b = s.getBytes(); // FIXME sort out charset
		out.write(b);
		out.write(0);
		position += b.length + 1;
	}
}
