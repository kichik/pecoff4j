package org.boris.pecoff4j.util;

import java.io.UnsupportedEncodingException;

public class Strings {

	public static int getUtf16Length(String string) {
		try {
			return string.getBytes("UTF-16").length + 2; // null terminator
		} catch (UnsupportedEncodingException e) {
			return string.length() * 2 + 2;
		}
	}

}
