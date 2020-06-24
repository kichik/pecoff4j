/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.asm;

public class JumpIfInstruction extends AbstractInstruction {
	private int op;
	private int imm32;

	public JumpIfInstruction(int op, int imm32) {
		this.op = op;
		this.imm32 = imm32;
		this.code = toCode(0x0f, new ModRM(op), imm32);
	}

	public String getOp() {
		switch (op) {
		case 0x85:
			return "jnz";
		case 0x8d:
			return "jge";
		}
		return "???";
	}

	@Override
	public String toIntelAssembly() {
		return getOp() + "  " + toHexString(imm32, false) + " ("
				+ toHexString(offset + imm32 + size(), false) + ")";
	}
}
