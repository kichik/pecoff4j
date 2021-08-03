package com.kichik.pecoff4j;

import java.io.File;
import java.io.FilenameFilter;

import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.util.IO;
import com.kichik.pecoff4j.util.Reflection;

public class TestParseDLLs {

	public static void main(String[] args) throws Exception {
		File[] files = findPEs();
        for (File file : files)
        {
            System.out.println(file);
            PE pe = PEParser.parse(file);
            System.out.println(Reflection.toString(pe));
        }
	}

	public static File[] findPEs() {
		FilenameFilter ff = (dir, name) -> (name.endsWith(".dll") || name.endsWith(".exe"))
				&& !name.contains("dllcache");
		File[] files = IO.findFiles(new File("F:/Program Files/"), ff);
		// File[] files = IO.findFiles(new File("C:/Program Files/"), ff);
		// File[] files = IO.findFiles(new File("C:/windows/system32"), ff);

		return files;
	}

}
