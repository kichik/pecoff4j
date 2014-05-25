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

public class ADD extends AbstractInstruction
{
    private ModRM modrm;
    private byte imm8;
    private int imm32;

    public ADD(ModRM modrm, byte imm8) {
        this.modrm = modrm;
        this.imm8 = imm8;
        this.code = toCode(0x83, modrm, imm8);
    }

    public ADD(int opcode, ModRM modrm, int imm32) {
        this.modrm = modrm;
        this.imm32 = imm32;
        this.code = toCode(opcode, modrm, imm32);
    }

    public String toIntelAssembly() {
        switch (getOpCode()) {
        case 0x03:
            return "add  " + modrm.toIntelAssembly(imm32);
        }
        return "add  " + Register.to32(modrm.reg1) + ", " + toHexString(imm8, false);
    }
}
