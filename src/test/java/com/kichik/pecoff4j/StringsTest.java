package com.kichik.pecoff4j;

import com.kichik.pecoff4j.util.Strings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringsTest {
    @Test
    public void testUtf16Length() {
        assertEquals(2, Strings.getUtf16Length(""));
        assertEquals(4, Strings.getUtf16Length("π"));
        assertEquals(10, Strings.getUtf16Length("test"));
    }
}
