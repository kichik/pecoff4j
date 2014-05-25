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

public class TEST extends AbstractInstruction
{
    private ModRM modrm;

    public TEST(ModRM modrm) {
        this.modrm = modrm;
        this.code = toCode(0x85, modrm);
    }

    public String toIntelAssembly() {
        return "test " + Register.to32(modrm.reg1) + ", " + Register.to32(modrm.reg2);
    }
}
