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

public class SUB extends AbstractInstruction
{
    private ModRM modrm;
    private int imm32;

    public SUB(ModRM modrm, int imm32) {
        this.modrm = modrm;
        this.imm32 = imm32;
        this.code = toCode(0x81, modrm, imm32);
    }

    public String toIntelAssembly() {
        return "sub  " + Register.to32(modrm.reg1) + ", " + toHexString(imm32, false);
    }
}
