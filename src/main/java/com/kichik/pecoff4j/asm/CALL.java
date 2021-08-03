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

public class CALL extends AbstractInstruction {
	private final int imm32;

	public CALL(ModRM modrm, int imm32) {
		this.imm32 = imm32;
		this.code = toCode(0xff, modrm, imm32);
	}

	public CALL(int opcode, int imm32) {
		this.imm32 = imm32;
		this.code = toCode(opcode, imm32);
	}

	@Override
	public String toIntelAssembly() {
		switch (getOpCode()) {
		case 0xe8:
			return "call " + toHexString(imm32, false) + " ("
					+ toHexString(offset + imm32 + size(), false) + ")";
		}
		return "call " + toHexString(imm32, false);
	}
}
