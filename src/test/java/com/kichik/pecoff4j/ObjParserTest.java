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
import com.kichik.pecoff4j.util.Reflection;

public class ObjParserTest {
	public static void main(String[] args) throws Exception {
		PE p = PEParser
				.parse("F:\\eclipse\\workspace\\WinRun4J\\build\\WinRun4J-Debug\\win32.obj");
		Reflection.println(p);
	}
}
