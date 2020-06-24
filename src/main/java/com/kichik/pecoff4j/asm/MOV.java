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

public class MOV extends AbstractInstruction {
	private ModRM modrm;
	private SIB sib;
	private int disp32;
	private int imm32;

	public MOV(ModRM modrm) {
		this.modrm = modrm;
		this.code = toCode(0x8b, modrm);
	}

	public MOV(ModRM modrm, byte imm8) {
		this.modrm = modrm;
		this.imm32 = imm8;
		this.code = toCode(0x8b, modrm, imm8);
	}

	public MOV(ModRM modrm, int disp32, int imm32) {
		this.modrm = modrm;
		this.disp32 = disp32;
		this.imm32 = imm32;
		this.code = toCode(0xc7, modrm, disp32, imm32);
	}

	public MOV(ModRM modrm, byte disp8, int imm32) {
		this.modrm = modrm;
		this.disp32 = disp8;
		this.imm32 = imm32;
		this.code = toCode(0xc7, modrm, disp8, imm32);
	}

	public MOV(ModRM modrm, int imm32) {
		this.modrm = modrm;
		this.imm32 = imm32;
		this.code = toCode(0x89, modrm, imm32);
	}

	public MOV(int opcode, ModRM modrm, byte imm8) {
		this.modrm = modrm;
		this.imm32 = imm8;
		this.code = toCode(opcode, modrm, imm8);
	}

	public MOV(int opcode, ModRM modrm, int imm32) {
		this.modrm = modrm;
		this.imm32 = imm32;
		this.code = toCode(opcode, modrm, imm32);
	}

	public MOV(int opcode, int imm32) {
		this.imm32 = imm32;
		this.code = toCode(opcode, imm32);
	}

	public MOV(ModRM modrm, SIB sib, byte imm8) {
		this.modrm = modrm;
		this.sib = sib;
		this.imm32 = imm8;
		this.code = toCode(0x89, modrm, sib, imm8);
	}

	public MOV(int opcode, ModRM modrm, SIB sib, int imm32) {
		this.modrm = modrm;
		this.sib = sib;
		this.imm32 = imm32;
		this.code = toCode(opcode, modrm, sib, imm32);
	}

	@Override
	public String toIntelAssembly() {
		switch ((code[0]) & 0xff) {
		case 0x8b:
			switch (modrm.mod) {
			case 0:
				return "mov  [" + Register.to32(modrm.reg2) + "], "
						+ Register.to32(modrm.reg1);
			case 1:
				return "mov  " + Register.to32(modrm.reg2) + ", ["
						+ Register.to32(modrm.reg1)
						+ toHexString((byte) imm32, true) + "]";
			case 2:
				return "mov  " + Register.to32(modrm.reg2) + ", ["
						+ Register.to32(modrm.reg1) + toHexString(imm32, true)
						+ "]";
			case 3:
				return "mov  " + Register.to32(modrm.reg2) + ", "
						+ Register.to32(modrm.reg1);
			}
		case 0x89:
			switch (modrm.mod) {
			case 0:

			}
			return "mov  [" + Register.to32(modrm.reg1)
					+ toHexString(imm32, true) + "], "
					+ Register.to32(modrm.reg2);
		case 0xc6:
			return "mov  byte ptr [" + Register.to32(modrm.reg1) + "], "
					+ toHexString((byte) imm32, false);
		case 0xc7:
			switch (modrm.mod) {
			case 1:
				return "mov  dword ptr [" + Register.to32(modrm.reg1)
						+ toHexString((byte) disp32, true) + "], "
						+ toHexString(imm32, false);
			case 2:
				return "mov  dword ptr [" + Register.to32(modrm.reg1)
						+ toHexString(disp32, true) + "], "
						+ toHexString(imm32, false);
			}
		case 0xa1:
			return "mov  eax, [" + toHexString(imm32, false) + "]";
		case 0xa3:
			return "mov  [" + toHexString(imm32, false) + "], eax";
		}

		return "MOV: UNKNOWN";
	}
}
