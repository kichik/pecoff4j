package com.kichik.pecoff4j.util;

/**
 * Utility methods for alignment.
 */
public class Alignment {
    public static int alignDword(int value) {
        return value + ((4 - (value % 4)) % 4);
    }

    public static int align(int value, int alignment) {
        return value + ((alignment - (value % alignment)) % alignment);
    }
}
