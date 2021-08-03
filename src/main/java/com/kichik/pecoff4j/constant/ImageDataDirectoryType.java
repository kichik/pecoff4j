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

public interface ImageDataDirectoryType {
	int RAW = -1;
	int EXPORT_TABLE = 0;
	int IMPORT_TABLE = 1;
	int RESOURCE_TABLE = 2;
	int EXCEPTION_TABLE = 3;
	int CERTIFICATE_TABLE = 4;
	int BASE_RELOCATION_TABLE = 5;
	int DEBUG = 6;
	int ARCHITECTURE = 7;
	int GLOBAL_PTR = 8;
	int TLS_TABLE = 9;
	int LOAD_CONFIG_TABLE = 10;
	int BOUND_IMPORT = 11;
	int IAT = 12;
	int DELAY_IMPORT_DESCRIPTOR = 13;
	int CLR_RUNTIME_HEADER = 14;
	int RESERVED = 15;
}
