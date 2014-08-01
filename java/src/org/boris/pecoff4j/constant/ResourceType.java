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

public interface ResourceType {
	public static final int CURSOR = 0x0001;
	public static final int BITMAP = 0x0002;
	public static final int ICON = 0x0003;
	public static final int MENU = 0x0004;
	public static final int DIALOG = 0x0005;
	public static final int STRING_TABLE = 0x0006;
	public static final int FONT_DIRECTORY = 0x0007;
	public static final int FONT = 0x0008;
	public static final int ACCELERATORS_TABLE = 0x0009;
	public static final int RC_DATA = 0x000A;
	public static final int MESSAGE_TABLE = 0x000B;
	public static final int GROUP_CURSOR = 0x000C;
	public static final int GROUP_ICON = 0x000E;
	public static final int VERSION_INFO = 0x0010;
	public static final int DIALOG_INCLUDE = 0x0011;
	public static final int PLUG_N_PLAY = 0x0013;
	public static final int VXD = 0x0014;
	public static final int ANIMATED_CURSOR = 0x0015;
	public static final int ANIMATED_ICON = 0x0016;
	public static final int HTML = 0x0017;
	public static final int MANIFEST = 0x0018;
	public static final int BITMAP_NEW = 0x2002;
	public static final int MENU_NEW = 0x2004;
	public static final int CURSOR_NEW = 0x2005;
}
