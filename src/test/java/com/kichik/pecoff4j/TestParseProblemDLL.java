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

import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.io.PEParser;

public class TestParseProblemDLL {
	private static final String PF1 = "C:\\windows\\system32\\usrrtosa.dll";
	private static final String PF2 = "C:\\windows\\system32\\storage.dll";
	private static final String PF3 = "C:\\windows\\system32\\divx.dll";
	private static final String PF4 = "C:\\windows\\system32\\exe2bin.exe";
	private static final String PF5 = "C:\\windows\\system32\\dosx.exe";
	private static final String U32 = "C:\\windows\\system32\\user32.dll";

	public static void main(String[] args) throws Exception {
		PE pe = PEParser.parse(U32);
		System.out.println(pe);
	}
}
