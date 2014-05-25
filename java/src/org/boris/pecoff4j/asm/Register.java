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

public class Register
{
    public static String to32(int register) {
        switch (register) {
        case 0:
            return "eax";
        case 1:
            return "ecx";
        case 2:
            return "edx";
        case 3:
            return "ebx";
        case 4:
            return "esp";
        case 5:
            return "ebp";
        case 6:
            return "esi";
        case 7:
            return "edi";
        }
        return null;
    }
}
