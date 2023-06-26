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

import java.io.EOFException;
import java.io.IOException;

public interface IDataReader extends AutoCloseable {
	public abstract int readByte() throws IOException;

	public abstract int readWord() throws IOException;

	public abstract int readDoubleWord() throws IOException;

	public abstract long readLong() throws IOException;

	public abstract int getPosition();
	
	public abstract boolean hasMore() throws IOException;

	public abstract void jumpTo(int location) throws IOException;

	public abstract void skipBytes(int numBytes) throws IOException;

	public abstract void close() throws IOException;

	public abstract void read(byte[] b) throws IOException;

	public abstract String readUtf(int size) throws IOException;

	public abstract String readUtf() throws IOException;

	public abstract String readUnicode() throws IOException;

	public abstract String readUnicode(int size) throws IOException;

	public abstract byte[] readAll() throws IOException;

	/**
	 * Align the reader's position
	 * @param alignment the alignment in bytes
	 * @return the number of bytes that have been skipped
	 */
	default int align(int alignment) throws IOException {
		int off = (alignment - (getPosition() % alignment)) % alignment;
		try {
			skipBytes(off);
		} catch (EOFException ignored) {
			// no need to align when it's at the end of its data
		}
		return off;
	}

	/**
	 * Read all bytes until the given position is reached. If all these bytes are zero, null is returned instead.
	 */
	default byte[] readNonZeroOrNull(int pointer) throws IOException {
		if (pointer > getPosition()) {
			byte[] pa = new byte[pointer - getPosition()];
			read(pa);
			boolean zeroes = true;
			for (int i = 0; i < pa.length; i++) {
				if (pa[i] != 0) {
					zeroes = false;
					break;
				}
			}
			if (!zeroes)
				return pa;
		}

		return null;
	}
}
