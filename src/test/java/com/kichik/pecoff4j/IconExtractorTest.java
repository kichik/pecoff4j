package com.kichik.pecoff4j;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import com.kichik.pecoff4j.util.Diff;
import com.kichik.pecoff4j.util.FindFilesCallback;
import com.kichik.pecoff4j.util.IO;
import com.kichik.pecoff4j.util.IconExtractor;
import com.kichik.pecoff4j.util.PEFilenameFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IconExtractorTest {

	@TempDir
	File tempDir;

	@Test
	void testIconExtraction() throws Exception {
		File exe = new File(tempDir, "WinRun4J.exe");
		IO.copy(getClass().getResourceAsStream("/WinRun4J.exe"), Files.newOutputStream(exe.toPath()), true);

		File output = new File(tempDir, "output");
		output.mkdir();

		IconExtractor.extract(exe, output);

		byte[] expected = IO.toBytes(getClass().getResourceAsStream("/WinRun4J.ico"));
		byte[] actual = IO.toBytes(new File(output, "WinRun4J.exe-icon0.ico"));
		assertTrue(Diff.equals(expected, actual));
	}
}
