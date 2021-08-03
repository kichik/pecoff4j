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

/**
 * The constants for characteristics.
 */
public interface Characteristics {
	int IMAGE_FILE_RELOCS_STRIPPED = 0x0001;
	int IMAGE_FILE_EXECUTABLE_IMAGE = 0x0002;
	int IMAGE_FILE_LINE_NUMS_STRIPPED = 0x0004;
	int IMAGE_FILE_LOCAL_SYMS_STRIPPED = 0x0008;
	int IMAGE_FILE_AGGRESSIVE_WS_TRIM = 0x0010;
	int IMAGE_FILE_LARGE_ADDRESS_AWARE = 0x0020;
	int IMAGE_FILE_RESERVED = 0x0040;
	int IMAGE_FILE_BYTES_REVERSED_LO = 0x0080;
	int IMAGE_FILE_32BIT_MACHINE = 0x0100;
	int IMAGE_FILE_DEBUG_STRIPPED = 0x0200;
	int IMAGE_FILE_REMOVABLE_RUN_FROM_SWAP = 0x0400;
	int IMAGE_FILE_NET_RUN_FROM_SWAP = 0x0800;
	int IMAGE_FILE_SYSTEM = 0x1000;
	int IMAGE_FILE_DLL = 0x2000;
	int IMAGE_FILE_UP_SYSTEM_ONLY = 0x4000;
	int IMAGE_FILE_BYTES_REVERSED_HI = 0x8000;
}
