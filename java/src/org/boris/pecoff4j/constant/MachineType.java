/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package org.boris.pecoff4j.constant;

/**
 * The constants for machine types.
 */
public interface MachineType
{
    public static final int IMAGE_FILE_MACHINE_UNKNOWN = 0x0;
    public static final int IMAGE_FILE_MACHINE_AM33 = 0x1d3;
    public static final int IMAGE_FILE_MACHINE_AMD64 = 0x8664;
    public static final int IMAGE_FILE_MACHINE_ARM = 0x1c0;
    public static final int IMAGE_FILE_MACHINE_EBC = 0xebc;
    public static final int IMAGE_FILE_MACHINE_I386 = 0x14c;
    public static final int IMAGE_FILE_MACHINE_IA64 = 0x200;
    public static final int IMAGE_FILE_MACHINE_M32R = 0x9041;
    public static final int IMAGE_FILE_MACHINE_MIPS16 = 0x266;
    public static final int IMAGE_FILE_MACHINE_MIPSFPU = 0x366;
    public static final int IMAGE_FILE_MACHINE_MIPSFPU16 = 0x466;
    public static final int IMAGE_FILE_MACHINE_POWERPC = 0x1f0;
    public static final int IMAGE_FILE_MACHINE_POWERPCFP = 0x1f1;
    public static final int IMAGE_FILE_MACHINE_R4000 = 0x166;
    public static final int IMAGE_FILE_MACHINE_SH3 = 0x1a2;
    public static final int IMAGE_FILE_MACHINE_SH3DSP = 0x1a3;
    public static final int IMAGE_FILE_MACHINE_SH4 = 0x1a6;
    public static final int IMAGE_FILE_MACHINE_SH5 = 0x1a8;
    public static final int IMAGE_FILE_MACHINE_THUMB = 0x1c2;
    public static final int IMAGE_FILE_MACHINE_WCEMIPSV2 = 0x169;
}
