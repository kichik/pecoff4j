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
	public static final int IMAGE_DEBUG_TYPE_UNKNOWN = 0;
	public static final int IMAGE_DEBUG_TYPE_COFF = 1;
	public static final int IMAGE_DEBUG_TYPE_CODEVIEW = 2;
	public static final int IMAGE_DEBUG_TYPE_FPO = 3;
	public static final int IMAGE_DEBUG_TYPE_MISC = 4;
	public static final int IMAGE_DEBUG_TYPE_EXCEPTION = 5;
	public static final int IMAGE_DEBUG_TYPE_FIXUP = 6;
	public static final int IMAGE_DEBUG_TYPE_OMAP_TO_SRC = 7;
	public static final int IMAGE_DEBUG_TYPE_OMAP_FROM_SRC = 8;
	public static final int IMAGE_DEBUG_TYPE_BORLAND = 9;
	public static final int IMAGE_DEBUG_TYPE_RESERVED10 = 10;
	public static final int IMAGE_DEBUG_TYPE_CLSID = 11;
}
