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
 * The debug types.
 */
public interface DebugType {
	int IMAGE_DEBUG_TYPE_UNKNOWN = 0;
	int IMAGE_DEBUG_TYPE_COFF = 1;
	int IMAGE_DEBUG_TYPE_CODEVIEW = 2;
	int IMAGE_DEBUG_TYPE_FPO = 3;
	int IMAGE_DEBUG_TYPE_MISC = 4;
	int IMAGE_DEBUG_TYPE_EXCEPTION = 5;
	int IMAGE_DEBUG_TYPE_FIXUP = 6;
	int IMAGE_DEBUG_TYPE_OMAP_TO_SRC = 7;
	int IMAGE_DEBUG_TYPE_OMAP_FROM_SRC = 8;
	int IMAGE_DEBUG_TYPE_BORLAND = 9;
	int IMAGE_DEBUG_TYPE_RESERVED10 = 10;
	int IMAGE_DEBUG_TYPE_CLSID = 11;
}
