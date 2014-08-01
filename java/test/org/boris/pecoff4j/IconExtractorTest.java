package org.boris.pecoff4j;

import java.io.File;

import org.boris.pecoff4j.util.FindFilesCallback;
import org.boris.pecoff4j.util.IO;
import org.boris.pecoff4j.util.IconExtractor;
import org.boris.pecoff4j.util.PEFilenameFilter;

public class IconExtractorTest implements FindFilesCallback {
	private static File outdir = new File("F:/Development/icons/extracted");

	public static void main(String[] args) throws Exception {
		IO.findFiles(new File("F:/Program Files/"), new PEFilenameFilter(),
				new IconExtractorTest());
	}

	@Override
	public void fileFound(File fs) {
		try {
			System.out.println(fs);
			IconExtractor.extract(fs, outdir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
