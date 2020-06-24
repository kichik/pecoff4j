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

public class CMP extends AbstractInstruction {
	private ModRM modrm;
	private byte imm8;

	public CMP(ModRM modrm, byte imm8) {
		this.modrm = modrm;
		this.imm8 = imm8;
		this.code = toCode(0x3b, modrm, imm8);
	}

	@Override
	public String toIntelAssembly() {
		return "cmp  " + Register.to32(modrm.reg2) + ", ["
				+ Register.to32(modrm.reg1) + toHexString(imm8, true) + "]";
	}
}
