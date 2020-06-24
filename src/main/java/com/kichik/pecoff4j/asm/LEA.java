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

public class LEA extends AbstractInstruction {
	private ModRM modrm;
	private SIB sib;
	private int imm32;

	public LEA(ModRM modrm, int imm32) {
		this.modrm = modrm;
		this.imm32 = imm32;
		this.code = toCode(0x8d, modrm, imm32);
	}

	public LEA(ModRM modrm, SIB sib, int imm32) {
		this.modrm = modrm;
		this.sib = sib;
		this.imm32 = imm32;
		this.code = toCode(0x8d, modrm, sib, imm32);
	}

	@Override
	public String toIntelAssembly() {
		if (sib != null) {
			return "lea  " + Register.to32(modrm.reg2) + ", ["
					+ sib.toString(imm32) + "]";
		}
		return "lea  " + Register.to32(modrm.reg2) + ", ["
				+ Register.to32(modrm.reg1) + toHexString(imm32, true) + "]";
	}
}
