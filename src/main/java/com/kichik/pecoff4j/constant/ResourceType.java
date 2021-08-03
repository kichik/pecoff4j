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

public interface ResourceType {
	int CURSOR = 0x0001;
	int BITMAP = 0x0002;
	int ICON = 0x0003;
	int MENU = 0x0004;
	int DIALOG = 0x0005;
	int STRING_TABLE = 0x0006;
	int FONT_DIRECTORY = 0x0007;
	int FONT = 0x0008;
	int ACCELERATORS_TABLE = 0x0009;
	int RC_DATA = 0x000A;
	int MESSAGE_TABLE = 0x000B;
	int GROUP_CURSOR = 0x000C;
	int GROUP_ICON = 0x000E;
	int VERSION_INFO = 0x0010;
	int DIALOG_INCLUDE = 0x0011;
	int PLUG_N_PLAY = 0x0013;
	int VXD = 0x0014;
	int ANIMATED_CURSOR = 0x0015;
	int ANIMATED_ICON = 0x0016;
	int HTML = 0x0017;
	int MANIFEST = 0x0018;
	int BITMAP_NEW = 0x2002;
	int MENU_NEW = 0x2004;
	int CURSOR_NEW = 0x2005;
}
