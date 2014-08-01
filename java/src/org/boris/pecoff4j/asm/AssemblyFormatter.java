/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.asm;

import java.io.IOException;
import java.io.PrintStream;

public class AssemblyFormatter {
	public static void format(AbstractInstruction[] instructions,
			PrintStream out) throws IOException {
		for (AbstractInstruction ai : instructions) {
			out.print(AbstractInstruction.toHexString(ai.getOffset(), false));
			out.print("   ");
			out.print(toHexString(ai.toCode(), 30));
			out.println(ai.toIntelAssembly());
		}
	}

	public static String toHexString(byte[] bytes, int pad) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(toHexString(b));
		}
		for (int i = pad - bytes.length * 2; i > 0; i--)
			sb.append(' ');
		return sb.toString();
	}

	private static String toHexString(byte b) {
		String s = Integer.toHexString((b) & 0xff);
		if (s.length() == 1) {
			return "0" + s;
		} else
			return s;
	}
}
