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

public class JMP extends AbstractInstruction {
	private byte imm8;
	private int imm32;

	public JMP(byte imm8) {
		this.imm8 = imm8;
		this.code = toCode(0xeb, imm8);
	}

	public JMP(int imm32) {
		this.imm32 = imm32;
		this.code = toCode(0xe9, imm32);
	}

	@Override
	public String toIntelAssembly() {
		switch (getOpCode()) {
		case 0xe9:
			return "jmp  " + toHexString(imm32, false);
		}
		return "jmp  " + toHexString(imm8, false);
	}
}
