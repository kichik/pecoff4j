package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.PEParser;
import org.junit.jupiter.api.Test;

public class ImportTests {
    @Test
    void testWinRun4J() throws Exception {
        PE pe = PEParser.parse(WinRun4JTest1.class
                .getResourceAsStream("/WinRun4J.exe"));
        run(pe);
    }

	@Test
	void testPaintXP() throws Exception {
		PE pe = PEParser.parse(WinRun4JTest1.class
				.getResourceAsStream("/paint-XP-x32.exe"));
		run(pe);
	}

	@Test
	void testPaint10() throws Exception {
		PE pe = PEParser.parse(WinRun4JTest1.class
				.getResourceAsStream("/paint-10-x32+.exe"));
		run(pe);
	}

	private void run(PE pe) {
		ImportDirectory importDirectory = pe.getImageData().getImportTable();
		for (int i = 0; i < importDirectory.size(); i++) {
			System.out.printf("=========== %s ===========\n", importDirectory.getName(i));
			ImportDirectoryTable importDirectoryTable = importDirectory.getNameTable(i);
			for (int t = 0; t < importDirectoryTable.size(); t++) {
				ImportEntry importEntry = importDirectoryTable.getEntry(t);
				System.out.printf(" - %s @%s\n",
						importEntry.getName(),
						Integer.toHexString(importEntry.getVal()).toUpperCase());
			}
		}
	}
}
