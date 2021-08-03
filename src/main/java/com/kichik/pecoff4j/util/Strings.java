package com.kichik.pecoff4j.util;

import java.nio.charset.StandardCharsets;

public class Strings {

	public static int getUtf16Length(String string) {
        return string.getBytes(StandardCharsets.UTF_16).length + 2; // null terminator
    }

}
