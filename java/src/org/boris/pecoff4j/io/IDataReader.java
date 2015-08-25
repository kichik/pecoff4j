/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.io;

import java.io.IOException;

public interface IDataReader {
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
}
