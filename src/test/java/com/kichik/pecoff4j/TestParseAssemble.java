/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j;

import com.kichik.pecoff4j.io.PEAssembler;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.util.Diff;
import com.kichik.pecoff4j.util.IO;

import java.io.File;

public class TestParseAssemble {
	public static void main(String[] args) throws Exception {
		File[] files = TestParseDLLs.findPEs();
		int diffc = 500;
		for (File file : files) {
			// System.out.println(files[i]);
			byte[] b1 = IO.toBytes(file);
			try {
				PE pe = PEParser.parse(file);
				if (pe.getOptionalHeader() == null)
					continue;
				byte[] b2 = PEAssembler.toBytes(pe);
				if (!Diff.equals(b1, b2, false)) {
					System.out.println(file);
					if (diffc > 0) {
						Diff.findDiff(b1, b2, false);
						diffc--;
					}
				}
			} catch (Throwable e) {
				System.out.println(file);
				e.printStackTrace();
			}
		}
	}
}
