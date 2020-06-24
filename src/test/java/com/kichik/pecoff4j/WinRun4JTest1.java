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

import com.kichik.pecoff4j.io.PEParser;

public class WinRun4JTest1 {
	public static void main(String[] args) throws Exception {
		PE pe = PEParser.parse(WinRun4JTest1.class
				.getResourceAsStream("/WinRun4J.exe"));
		for (int i = 0; i < pe.getSectionTable().getNumberOfSections(); i++) {
			System.out.println(pe.getSectionTable().getHeader(i).getName());
		}
	}
}
