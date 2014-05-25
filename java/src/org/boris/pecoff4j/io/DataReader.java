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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataReader implements IDataReader
{
    private InputStream dis;
    private int position = 0;

    public DataReader(byte[] buffer) {
        this.dis = new BufferedInputStream(new ByteArrayInputStream(buffer));
    }

    public DataReader(byte[] buffer, int offset, int length) {
        this.dis = new BufferedInputStream(new ByteArrayInputStream(buffer,
                offset, length));
    }

    public DataReader(InputStream is) {
        if (is instanceof BufferedInputStream) {
            this.dis = is;
        } else {
            this.dis = new BufferedInputStream(is);
        }
    }

    public int readByte() throws IOException {
        position += 1;
        return dis.read();
    }

    public int readWord() throws IOException {
        position += 2;
        return dis.read() | dis.read() << 8;
    }

    public long readLong() throws IOException {
        return (readDoubleWord() & 0x00000000ffffffffl) |
                ((long) readDoubleWord() << 32l);
    }

    public int readDoubleWord() throws IOException {
        position += 4;
        return dis.read() | dis.read() << 8 | dis.read() << 16 |
                dis.read() << 24;
    }

    public int getPosition() {
        return position;
    }

    public void jumpTo(int location) throws IOException {
        if (location < position)
            throw new IOException(
                    "DataReader does not support scanning backwards (" +
                            location + ")");
        if (location > position)
            skipBytes(location - position);
    }

    public void skipBytes(int numBytes) throws IOException {
        position += numBytes;
        for (int i = 0; i < numBytes; i++) {
            dis.read();
        }
    }

    public void close() throws IOException {
        dis.close();
    }

    public void read(byte[] b) throws IOException {
        position += b.length;
        dis.read(b);
    }

    public String readUtf(int size) throws IOException {
        position += size;
        byte b[] = new byte[size];
        dis.read(b);
        int i = 0;
        for (; i < b.length; i++) {
            if (b[i] == 0)
                break;
        }
        return new String(b, 0, i);
    }

    public String readUtf() throws IOException {
        StringBuilder sb = new StringBuilder();
        int c = 0;
        while ((c = readByte()) != 0) {
            if (c == -1)
                throw new IOException("Unexpected end of stream");
            sb.append((char) c);
        }
        return sb.toString();
    }

    public String readUnicode() throws IOException {
        StringBuilder sb = new StringBuilder();
        char c = 0;
        while ((c = (char) readWord()) != 0) {
            sb.append(c);
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public String readUnicode(int size) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append((char) readWord());
        }
        return sb.toString();
    }
}