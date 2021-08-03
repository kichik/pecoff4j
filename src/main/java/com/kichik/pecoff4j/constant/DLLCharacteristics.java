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
 * Constants for DLL characteristics.
 */
public interface DLLCharacteristics {
	int IMAGE_DLL_RESERVED_1 = 0x0001;
	int IMAGE_DLL_RESERVED_2 = 0x0002;
	int IMAGE_DLL_RESERVED_3 = 0x0004;
	int IMAGE_DLL_RESERVED_4 = 0x0008;
	int IMAGE_DLL_CHARACTERISTICS_DYNAMIC_BASE = 0x0040;
	int IMAGE_DLL_CHARACTERISTICS_FORCE_INTEGRITY = 0x0080;
	int IMAGE_DLL_CHARACTERISTICS_NX_COMPAT = 0x0100;
	int IMAGE_DLLCHARACTERISTICS_NO_ISOLATION = 0x0200;
	int IMAGE_DLLCHARACTERISTICS_NO_SEH = 0x0400;
	int IMAGE_DLLCHARACTERISTICS_NO_BIND = 0x0800;
	int IMAGE_DLL_RESERVED_5 = 0x1000;
	int IMAGE_DLLCHARACTERISTICS_WDM_DRIVER = 0x2000;
	int IMAGE_DLLCHARACTERISTICS_TERMINAL_SERVER_AWARE = 0x8000;
}
