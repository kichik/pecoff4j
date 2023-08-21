package com.kichik.pecoff4j.util;

import java.nio.charset.StandardCharsets;

public enum PaddingType {
	ZEROS("\u0000"), PATTERN("PADDINGXXPADDING");

	final byte[] data;

	PaddingType(String str) {
		this.data = str.getBytes(StandardCharsets.US_ASCII);
	}

	public byte[] getData() {
		return data;
	}
}
