package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.PEParser;
import org.junit.jupiter.api.Test;

public class ImportTests {
    @Test
    void foo() throws Exception {
        PE pe = PEParser.parse(WinRun4JTest1.class
                .getResourceAsStream("/WinRun4J.exe"));
        ImportDirectory importDirectory = pe.getImageData().getImportTable();
        for (int i = 0; i < importDirectory.size(); i++) {
            System.out.printf("=========== %s ===========\n", importDirectory.getName(i));
            ImportDirectoryTable importDirectoryTable = importDirectory.getNameTable(i);
            for (int t = 0; t < importDirectoryTable.size(); t++)  {
                ImportEntry importEntry = importDirectoryTable.getEntry(t);
                System.out.printf(" - %s @%s\n",
                        importEntry.getName() ,
                        Integer.toHexString(importEntry.getVal()).toUpperCase());
            }
        }
    }
}
