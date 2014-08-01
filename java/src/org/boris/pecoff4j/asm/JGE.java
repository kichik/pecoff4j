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

public class JGE extends AbstractInstruction {
	private byte imm8;

	public JGE(byte imm8) {
		this.imm8 = imm8;
		this.code = toCode(0x7d, imm8);
	}

	@Override
	public String toIntelAssembly() {
		return "jge  " + toHexString(imm8, true);
	}
}
