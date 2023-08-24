package com.kichik.pecoff4j.io;

import com.kichik.pecoff4j.util.PaddingType;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * A {@link IDataWriter} that validates the data to write by comparing them
 * to the data provided by a {@link IDataReader}. Compared to a diff of
 * expected and actual byte array, this approach provides immediate feedback
 * when a value is written that is not expected.
 */
public class ValidatingWriter implements IDataWriter {

	private static final byte[] PADDING = PaddingType.PATTERN.getData();

	private final IDataReader expected;

	public ValidatingWriter(IDataReader expected) {
		this.expected = expected;
	}

	@Override
	public void writeByte(int b) throws IOException {
		Assertions.assertEquals(expected.readByte(), b);
	}

	@Override
	public void writeByte(int b, int count) throws IOException {
		for (int i = 0; i < count; i++) {
			Assertions.assertEquals(expected.readByte(), b);
		}
	}

	@Override
	public void writeBytes(byte[] b) throws IOException {
		byte[] expectedBytes = new byte[b.length];
		expected.read(expectedBytes);
		Assertions.assertArrayEquals(expectedBytes, b);
	}

	@Override
	public void writeDoubleWord(int dw) throws IOException {
		Assertions.assertEquals(expected.readDoubleWord(), dw);
	}

	@Override
	public void writeWord(int w) throws IOException {
		Assertions.assertEquals(expected.readWord(), w);
	}

	@Override
	public void writeLong(long l) throws IOException {
		Assertions.assertEquals(expected.readLong(), l);
	}

	@Override
	public int getPosition() {
		return expected.getPosition();
	}

	@Override
	public void writeUtf(String s, int len) throws IOException {
		int position = expected.getPosition();
		Assertions.assertEquals(expected.readUtf(len), s);
		Assertions.assertEquals(position + len, expected.getPosition());
	}

	@Override
	public void writeUtf(String s) throws IOException {
		Assertions.assertEquals(expected.readUtf(s.length()), s);
	}

	@Override
	public void writeUnicode(String s) throws IOException {
		Assertions.assertEquals(expected.readUnicode(), s);
	}

	@Override
	public void writeUnicode(String s, int len) throws IOException {
		Assertions.assertEquals(expected.readUnicode(len - 1), s);
		Assertions.assertEquals(expected.readWord(), 0);
	}

	@Override
	public int align(int alignment) throws IOException {
		int off = (alignment - (getPosition() % alignment)) % alignment;
		if (off != 0) {
			for (int i = 0; i < off; i++) {
				if (expected.hasMore()) {
					int value = expected.readByte();
					if (value != 0 && value != PADDING[i % 16]) {
						Assertions.fail("Padding unexpected");
					}
				}
			}
		}
		return off;
	}

	public void assertEndOfStream() throws IOException {
		Assertions.assertFalse(expected.hasMore());
	}

	@Override
	public String toString() {
		return "ValidatingWriter @ " + getPosition();
	}
}
