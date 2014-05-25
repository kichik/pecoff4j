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

public interface IDataWriter
{
    void writeByte(int b) throws IOException;

    void writeByte(int b, int count) throws IOException;

    void writeWord(int w) throws IOException;

    void writeDoubleWord(int dw) throws IOException;

    void writeLong(long l) throws IOException;

    void writeBytes(byte[] b) throws IOException;

    void writeUtf(String s) throws IOException;

    void writeUtf(String s, int len) throws IOException;

    public abstract int getPosition();
}
