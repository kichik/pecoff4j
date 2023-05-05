package com.kichik.pecoff4j.util;

public class Strings {

	public static int getUtf16Length(String string) {
		return string.length() * 2 + 2; // null terminator
	}

}
