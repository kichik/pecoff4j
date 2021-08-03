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
 * The constants for the windows subsystem.
 */
public interface WindowsSubsystem {
	int IMAGE_SUBSYSTEM_UNKNOWN = 0;
	int IMAGE_SUBSYSTEM_NATIVE = 1;
	int IMAGE_SUBSYSTEM_WINDOWS_GUI = 2;
	int IMAGE_SUBSYSTEM_WINDOWS_CUI = 3;
	int IMAGE_SUBSYSTEM_POSIX_CUI = 7;
	int IMAGE_SUBSYSTEM_WINDOWS_CE_GUI = 9;
	int IMAGE_SUBSYSTEM_EFI_APPLICATION = 10;
	int IMAGE_SUBSYSTEM_EFI_BOOT_SERVICE_DRIVER = 11;
	int IMAGE_SUBSYSTEM_EFI_RUNTIME_DRIVER = 12;
	int IMAGE_SUBSYSTEM_EFI_ROM = 13;
	int IMAGE_SUBSYSTEM_XBOX = 14;
}
