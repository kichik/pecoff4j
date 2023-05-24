package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.io.ValidatingWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ReadWriteTest {
    @Test
    public void testReadWriteExe() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));
        ValidatingWriter writer = new ValidatingWriter(new DataReader(getClass().getResourceAsStream("/WinRun4J.exe")));

        pe.write(writer);
        writer.assertEndOfStream();
    }

    @Test
    public void testReadWriteDll() throws IOException {
        PE pe = PEParser.parse(getClass().getResourceAsStream("/clr/ClassLibrary.dll"));
        ValidatingWriter writer = new ValidatingWriter(new DataReader(getClass().getResourceAsStream("/clr/ClassLibrary.dll")));

        pe.write(writer);
        writer.assertEndOfStream();
    }
}
