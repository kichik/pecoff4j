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

public class RET extends AbstractInstruction
{
    public RET() {
        this.code = toCode(0xc3);
    }

    public String toIntelAssembly() {
        return "ret";
    }
}
