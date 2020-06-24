/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.constant;

public interface SectionFlag {
	public static final int IMAGE_SCN_RESERVED_1 = 0x00000000;
	public static final int IMAGE_SCN_RESERVED_2 = 0x00000001;
	public static final int IMAGE_SCN_RESERVED_3 = 0x00000002;
	public static final int IMAGE_SCN_RESERVED_4 = 0x00000004;
	public static final int IMAGE_SCN_TYPE_NO_PAD = 0x00000008;
	public static final int IMAGE_SCN_RESERVED_5 = 0x00000010;
	public static final int IMAGE_SCN_CNT_CODE = 0x00000020;
	public static final int IMAGE_SCN_CNT_INITIALIZED_DATA = 0x00000040;
	public static final int IMAGE_SCN_CNT_UNINITIALIZED_DATA = 0x00000080;
	public static final int IMAGE_SCN_LNK_OTHER = 0x00000100;
	public static final int IMAGE_SCN_LNK_INFO = 0x00000200;
	public static final int IMAGE_SCN_RESERVED_6 = 0x00000400;
	public static final int IMAGE_SCN_LNK_REMOVE = 0x00000800;
	public static final int IMAGE_SCN_LNK_COMDAT = 0x00001000;
	public static final int IMAGE_SCN_GPREL = 0x00008000;
	public static final int IMAGE_SCN_MEM_PURGEABLE = 0x00020000;
	public static final int IMAGE_SCN_MEM_16BIT = 0x00020000;
	public static final int IMAGE_SCN_MEM_LOCKED = 0x00040000;
	public static final int IMAGE_SCN_MEM_PRELOAD = 0x00080000;
	public static final int IMAGE_SCN_ALIGN_1BYTES = 0x00100000;
	public static final int IMAGE_SCN_ALIGN_2BYTES = 0x00200000;
	public static final int IMAGE_SCN_ALIGN_4BYTES = 0x00300000;
	public static final int IMAGE_SCN_ALIGN_8BYTES = 0x00400000;
	public static final int IMAGE_SCN_ALIGN_16BYTES = 0x00500000;
	public static final int IMAGE_SCN_ALIGN_32BYTES = 0x00600000;
	public static final int IMAGE_SCN_ALIGN_64BYTES = 0x00700000;
	public static final int IMAGE_SCN_ALIGN_128BYTES = 0x00800000;
	public static final int IMAGE_SCN_ALIGN_256BYTES = 0x00900000;
	public static final int IMAGE_SCN_ALIGN_512BYTES = 0x00A00000;
	public static final int IMAGE_SCN_ALIGN_1024BYTES = 0x00B00000;
	public static final int IMAGE_SCN_ALIGN_2048BYTES = 0x00C00000;
	public static final int IMAGE_SCN_ALIGN_4096BYTES = 0x00D00000;
	public static final int IMAGE_SCN_ALIGN_8192BYTES = 0x00E00000;
	public static final int IMAGE_SCN_LNK_NRELOC_OVFL = 0x01000000;
	public static final int IMAGE_SCN_MEM_DISCARDABLE = 0x02000000;
	public static final int IMAGE_SCN_MEM_NOT_CACHED = 0x04000000;
	public static final int IMAGE_SCN_MEM_NOT_PAGED = 0x08000000;
	public static final int IMAGE_SCN_MEM_SHARED = 0x10000000;
	public static final int IMAGE_SCN_MEM_EXECUTE = 0x20000000;
	public static final int IMAGE_SCN_MEM_READ = 0x40000000;
	public static final int IMAGE_SCN_MEM_WRITE = 0x80000000;
}
