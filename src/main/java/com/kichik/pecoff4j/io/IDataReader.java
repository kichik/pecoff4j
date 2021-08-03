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

import java.io.IOException;

public interface IDataReader extends AutoCloseable {
	int readByte() throws IOException;

	int readWord() throws IOException;

	int readDoubleWord() throws IOException;

	long readLong() throws IOException;

	int getPosition();
	
	boolean hasMore() throws IOException;

	void jumpTo(int location) throws IOException;

	void skipBytes(int numBytes) throws IOException;

	void close() throws IOException;

	void read(byte[] b) throws IOException;

	String readUtf(int size) throws IOException;

	String readUtf() throws IOException;

	String readUnicode() throws IOException;

	String readUnicode(int size) throws IOException;

	byte[] readAll() throws IOException;

	/**
	 * @param reader Parent reader to assign.
	 * @return Current reader <i>(Convenience for chain calls)</i>
	 */
	IDataReader withParent(IDataReader reader);

	/**
	 * @return Parent reader if any has {@link #withParent(IDataReader) been assigned}.
	 */
	IDataReader getParent();

	/**
	 * @return Copy of the current reader with a fresh state.
	 */
    IDataReader copy();
}
