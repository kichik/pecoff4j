package com.kichik.pecoff4j;

import java.io.File;
import java.io.FilenameFilter;

import com.kichik.pecoff4j.util.IO;

public class FindExe {
	public static void main(String[] args) throws Exception {
		File[] files = IO.findFiles(new File("C:/windows/system32"),
				(dir, name) -> name.endsWith(".dll")
						&& !name.contains("dllcache"));
		System.out.println("public static String[] DLL_FILES = {");
		for (File f : files) {
			String str = f.toString();
			str = str.replaceAll("\\\\", "/");
			System.out.println("\"" + str + "\",");
		}
		System.out.println("};");
	}
}
