package org.boris.pecoff4j;

import java.io.File;
import java.io.FilenameFilter;

import org.boris.pecoff4j.util.IO;

public class FindExe {
	public static void main(String[] args) throws Exception {
		File[] files = IO.findFiles(new File("C:/windows/system32"),
				new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".dll")
								&& name.indexOf("dllcache") == -1;
					}
				});
		System.out.println("public static String[] DLL_FILES = {");
		for (File f : files) {
			String str = f.toString();
			str = str.replaceAll("\\\\", "/");
			System.out.println("\"" + str + "\",");
		}
		System.out.println("};");
	}
}
