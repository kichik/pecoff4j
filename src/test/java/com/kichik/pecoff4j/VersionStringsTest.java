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

import com.kichik.pecoff4j.constant.ResourceType;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.io.ResourceParser;
import com.kichik.pecoff4j.resources.StringFileInfo;
import com.kichik.pecoff4j.resources.StringTable;
import com.kichik.pecoff4j.resources.VersionInfo;
import com.kichik.pecoff4j.util.ResourceHelper;

import java.io.IOException;

public class VersionStringsTest {

	public static void main(String[] args) throws IOException {
		testVersionStrings("C:/windows/system32/notepad.exe");
		testVersionStrings("C:/windows/system32/ieframe.dll");
		testVersionStrings("C:/windows/system32/igfxtray.exe");
	}

	public static void testVersionStrings(String path) throws IOException {
		PE pe = PEParser.parse(path);
		ResourceDirectory rd = pe.getImageData().getResourceTable();

		ResourceEntry[] entries = ResourceHelper.findResources(rd,
				ResourceType.VERSION_INFO);
		for (ResourceEntry entry : entries) {
			byte[] data = entry.getData();
			VersionInfo version = ResourceParser.readVersionInfo(data);

			StringFileInfo strings = version.getStringFileInfo();
			StringTable table = strings.getTable(0);
			for (int j = 0; j < table.getCount(); j++) {
				String key = table.getString(j).getKey();
				String value = table.getString(j).getValue();
				System.out.println(key + " = " + value);
			}
		}
	}

}