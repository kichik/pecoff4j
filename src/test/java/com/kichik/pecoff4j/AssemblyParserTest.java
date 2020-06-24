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

import java.io.ByteArrayInputStream;
import java.io.File;

import com.kichik.pecoff4j.asm.AbstractInstruction;
import com.kichik.pecoff4j.asm.AssemblyFormatter;
import com.kichik.pecoff4j.asm.AssemblyParser;
import com.kichik.pecoff4j.io.PEParser;

public class AssemblyParserTest {
	public static void main(String[] args) throws Exception {
		File f = new File("F:/eclipse/platform3.5/eclipse.exe");
		PE pe = PEParser.parse(f);
		SectionHeader sh = pe.getSectionTable().findHeader(".text");
		SectionData sd = pe.getSectionTable().findSection(".text");
		ByteArrayInputStream bis = new ByteArrayInputStream(sd.getData());
		AbstractInstruction[] inst = AssemblyParser.parseAll(
				0x400000 + sh.getVirtualAddress(), bis);
		AssemblyFormatter.format(inst, System.out);
	}
}
