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
import com.kichik.pecoff4j.io.DataReader;
import com.kichik.pecoff4j.io.DataWriter;
import com.kichik.pecoff4j.io.PEParser;
import com.kichik.pecoff4j.resources.StringFileInfo;
import com.kichik.pecoff4j.resources.StringPair;
import com.kichik.pecoff4j.resources.Var;
import com.kichik.pecoff4j.resources.VarFileInfo;
import com.kichik.pecoff4j.resources.VersionInfo;
import com.kichik.pecoff4j.util.ResourceHelper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for all {@link VersionInfo} related structures.
 */
public class VersionInfoTest {

	private static final int PAD = 2;
	private static final int NO_PAD = 0;

	@Test
	public void testVersionInfoLength() throws IOException {
		VersionInfo version = readVersionInfo();
		int origLength = version.getLength();

		version.rebuild();
		VersionInfo reloaded = reload(version);

		assertEquals(52, reloaded.getValueLength());
		assertEquals(origLength, reloaded.getLength());
	}

	@Test
	public void testVersionStrings() throws IOException {
		VersionInfo version = readVersionInfo();

		// check StringFileInfo structure
		StringFileInfo stringFileInfo = version.getStringFileInfo();
		assertEquals("StringFileInfo", stringFileInfo.getKey());
		assertEquals(1, stringFileInfo.getCount());
		List<StringPair> strings = stringFileInfo.getTables().get(0).getStrings();

		assertEquals(4, strings.size());
		assertKeyValue("FileDescription", "WinRun4J Application Launcher", strings.get(0));
		assertKeyValue("FileVersion", "0, 0, 2, 0", strings.get(1));
		assertKeyValue("OriginalFilename", "WinRun4J.exe", strings.get(2));
		assertKeyValue("ProductVersion", "0, 0, 2, 0", strings.get(3));

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

	@Test
	public void testStringFileInfoModification() throws IOException {
		VersionInfo version = readVersionInfo();

		List<StringPair> strings = version.getStringFileInfo().getTables().get(0).getStrings();
		strings.get(0).setValue("My Application");
		strings.add(createStringPair(0, "MyKey", "MyBinValue"));
		strings.add(createStringPair(1, "Empty", ""));
		strings.remove(2);

		version.rebuild();
		VersionInfo reloaded = reload(version);
		List<StringPair> newStrings = reloaded.getStringFileInfo().getTables().get(0).getStrings();

		assertEquals(5, newStrings.size());
		assertKeyValue("FileDescription", "My Application", newStrings.get(0));
		assertLength(6 + 16 * 2 + PAD + 15 * 2, 15, newStrings.get(0));
		assertKeyValue("FileVersion", "0, 0, 2, 0", newStrings.get(1));
		assertLength(6 + 12 * 2 + PAD + 11 * 2, 11, newStrings.get(1));
		assertKeyValue("ProductVersion", "0, 0, 2, 0", newStrings.get(2));
		assertLength(6 + 15 * 2 + NO_PAD + 11 * 2, 11, newStrings.get(2));
		assertKeyValue("MyKey", "MyBinValue", newStrings.get(3));
		assertLength(6 + 6 * 2 + PAD + 11 * 2, 11 * 2, newStrings.get(3));
		assertKeyValue("Empty", "", newStrings.get(4));
		assertLength(6 + 6 * 2 + PAD, 0, newStrings.get(4));

		final int sumPairs = 70 + 54 + PAD + 60 + PAD + 42 + PAD + 20;
		assertEquals(24 + sumPairs, reloaded.getStringFileInfo().getTables().get(0).getLength());
		assertEquals(36 + 24 + sumPairs, reloaded.getStringFileInfo().getLength());
		assertEquals(38 + PAD + 52 + reloaded.getVarFileInfo().getLength() + 36 + 24 + sumPairs, reloaded.getLength());
	}

	@Test
	public void testVarFileInfoModification() throws IOException {
		VersionInfo version = readVersionInfo();
		List<Var> vars = version.getVarFileInfo().getVars();

		vars.get(0).getValues().add(1234567);
		vars.add(createVar("MyKey", 2345678));

		version.rebuild();
		VersionInfo reloaded = reload(version);

		List<Var> newVars = reloaded.getVarFileInfo().getVars();
		assertEquals(2, newVars.size());

		assertEquals(8, newVars.get(0).getValueLength());
		assertEquals(6 + 12 * 2 + PAD + 8, newVars.get(0).getLength());
		assertEquals(78644233, newVars.get(0).getValues().get(0));
		assertEquals(1234567, newVars.get(0).getValues().get(1));

		assertEquals(4, newVars.get(1).getValueLength());
		assertEquals(6 + 6 * 2 + PAD + 4, newVars.get(1).getLength());
		assertEquals(2345678, newVars.get(1).getValues().get(0));
	}

	private VersionInfo readVersionInfo() throws IOException {
		PE pe = PEParser.parse(getClass().getResourceAsStream("/WinRun4J.exe"));
		ResourceDirectory rd = pe.getImageData().getResourceTable();

		ResourceEntry[] entries = ResourceHelper.findResources(rd, ResourceType.VERSION_INFO);
		assertEquals(1, entries.length);

		return VersionInfo.read(new DataReader(entries[0].getData()));
	}

	private VersionInfo reload(VersionInfo versionInfo) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataWriter dataWriter = new DataWriter(out);
		versionInfo.write(dataWriter);
		dataWriter.flush();
		return VersionInfo.read(new DataReader(new ByteArrayInputStream(out.toByteArray())));
	}

	private StringPair createStringPair(int type, String key, String value) {
		StringPair stringPair = new StringPair();
		stringPair.setType(type);
		stringPair.setKey(key);
		stringPair.setValue(value);
		return stringPair;
	}

	private Var createVar(String key, int... values) {
		Var v = new Var();
		v.setKey(key);
		for (int value : values) {
			v.getValues().add(value);
		}
		return v;
	}

	private void assertKeyValue(String key, String value, StringPair actual) {
		assertEquals(key, actual.getKey());
		assertEquals(value, actual.getValue());
	}

	private void assertLength(int length, int valueLength, StringPair actual) {
		assertEquals(length, actual.getLength());
		assertEquals(valueLength, actual.getValueLength());
	}

}