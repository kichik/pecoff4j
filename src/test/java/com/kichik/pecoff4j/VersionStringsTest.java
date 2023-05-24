/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Amir Szekely
 *******************************************************************************/
package com.kichik.pecoff4j;

import java.io.IOException;

import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.resources.StringFileInfo;
import com.kichik.pecoff4j.resources.StringPair;
import com.kichik.pecoff4j.resources.StringTable;
import com.kichik.pecoff4j.resources.Var;
import com.kichik.pecoff4j.resources.VarFileInfo;
import com.kichik.pecoff4j.resources.VersionInfo;
import com.kichik.pecoff4j.util.ResourceHelper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VersionStringsTest {

	@Test
	public void testVersionStrings() throws IOException {
		PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));
		ResourceDirectory rd = pe.getImageData().getResourceTable();

		ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.VERSION_INFO);
		assertEquals(1, entries.length);

		VersionInfo version = VersionInfo.read(new DataReader(entries[0].getData()));

		// check StringFileInfo structure
		StringFileInfo strings = version.getStringFileInfo();
		assertEquals("StringFileInfo", strings.getKey());
		assertEquals(1, strings.getCount());
		StringTable table = strings.getTable(0);

		assertEquals(4, table.getCount());
		assertKeyValue("FileDescription", "WinRun4J Application Launcher", table.getString(0));
		assertKeyValue("FileVersion", "0, 0, 2, 0", table.getString(1));
		assertKeyValue("OriginalFilename", "WinRun4J.exe", table.getString(2));
		assertKeyValue("ProductVersion", "0, 0, 2, 0", table.getString(3));

		// check VarFileInfo structure
		VarFileInfo varFileInfo = version.getVarFileInfo();
		assertEquals("VarFileInfo", varFileInfo.getKey());

		assertEquals(1, varFileInfo.getVars().size());
		Var var1 = varFileInfo.getVars().get(0);
		assertEquals("Translation", var1.getKey());
		assertEquals(1, var1.getValues().size());
		// code page 1200 is "utf-16"
		assertEquals(1200, var1.getValues().get(0) >> 16);
		// Microsoft language identifier 1033 is "English - United States"
		assertEquals(1033, var1.getValues().get(0) & 0xFFFF);
	}

	private void assertKeyValue(String key, String value, StringPair actual) {
		assertEquals(key, actual.getKey());
		assertEquals(value, actual.getValue());
	}

}