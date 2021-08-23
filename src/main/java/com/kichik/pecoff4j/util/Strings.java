package com.kichik.pecoff4j.util;

import java.nio.charset.StandardCharsets;

public class Strings {
	private static final char[] HEX_ALPHABET = "0123456789ABCDEF".toCharArray();

	public static String toHexString(byte[] bytes) {
		char[] chars = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			int value = bytes[i] & 0xFF;
			chars[i * 2] = HEX_ALPHABET[value >>> 4];
			chars[i * 2 + 1] = HEX_ALPHABET[value & 0x0F];
		}
		return new String(chars);
	}

	public static int getUtf16Length(String string) {
		return string.getBytes(StandardCharsets.UTF_16).length + 2; // null terminator
	}

}
