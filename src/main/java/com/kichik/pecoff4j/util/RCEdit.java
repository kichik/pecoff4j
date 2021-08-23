/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     Peter Smith
 *******************************************************************************/
package com.kichik.pecoff4j.util;

import java.io.File;
import java.io.IOException;

import com.kichik.pecoff4j.PE;
import com.kichik.pecoff4j.ResourceDirectory;
import com.kichik.pecoff4j.io.PEParser;

public class RCEdit {
	public static void main(String[] args) throws Exception {
		launch(new String[0]);
		launch(new String[] { "/I", "test/WinRun4J.exe", "test/eclipse.ico" });
	}

	public static void launch(String[] args) throws Exception {
		assertArgCount(args, 2, 3);

		String option = args[0].toUpperCase();
        switch (option) {
            case "/I":
                assertArgCount(args, 3, 3);
                addIcon(args[1], args[2]);
                break;
            case "/N":
                assertArgCount(args, 3, 3);
                setIni(args[1], args[2]);
                break;
            case "/S":
                assertArgCount(args, 3, 3);
                setSplash(args[1], args[2]);
                break;
        }
	}

	private static void addIcon(String exe, String icon) throws IOException {
		PE pe = PEParser.parse(exe);
		IconFile ic = IconFile.parse(icon);
	}

	private static void setIni(String exe, String ini) throws IOException {
		PE pe = PEParser.parse(exe);
		byte[] inib = IO.toBytes(new File(ini));
		ResourceDirectory rd = pe.getImageData().getResourceTable();
		if (rd != null) {

		}
	}

	private static void setSplash(String exe, String splash) throws IOException {
		PE pe = PEParser.parse(exe);
		byte[] spb = IO.toBytes(new File(splash));
		ResourceDirectory rd = pe.getImageData().getResourceTable();

	}

	private static void assertArgCount(String[] args, int min, int max) {
		if (args.length < min || args.length > max) {
			printUsage();
			System.exit(1);
		}
	}

	private static void printUsage() {
		printf("WinRun4J Resource Editor v2.0 (winrun4j.sf.net)\n\n");
		printf("Edits resources in executables (EXE) and dynamic link-libraries (DLL).\n\n");
		printf("RCEDIT <option> <exe/dll> [resource]\n\n");
		printf("  filename\tSpecifies the filename of the EXE/DLL.\n");
		printf("  resource\tSpecifies the name of the resource to add to the EXE/DLL.\n");
		printf("  /I\t\tSet the icon as the default icon for the executable.\n");
		printf("  /A\t\tAdds an icon to the EXE/DLL.\n");
		printf("  /N\t\tSets the INI file.\n");
		printf("  /J\t\tAdds a JAR file.\n");
		printf("  /E\t\tExtracts a JAR file from the EXE/DLL.\n");
		printf("  /S\t\tSets the splash image.\n");
		printf("  /C\t\tClears all resources from the EXE/DLL.\n");
		printf("  /L\t\tLists the resources in the EXE/DLL.\n");
		printf("  /P\t\tOutputs the contents of the INI file in the EXE.\n");
	}

	private static void printf(String s) {
		System.out.print(s);
	}
}
