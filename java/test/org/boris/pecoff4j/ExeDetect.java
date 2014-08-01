package org.boris.pecoff4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExeDetect {
	public static void main(String[] args) throws Exception {
		File x64 = new File(
				"C:\\Program Files\\Java\\jre1.6.0_04\\bin\\java.exe");
		File x86 = new File(
				"C:\\Program Files (x86)\\Java\\jre1.6.0\\bin\\java.exe");
		System.out.println(is64Bit(x64));
		System.out.println(is64Bit(x86));
	}

	public static boolean is64Bit(File exe) throws IOException {
		InputStream is = new FileInputStream(exe);
		int magic = is.read() | is.read() << 8;
		if (magic != 0x5A4D)
			throw new IOException("Invalid Exe");
		for (int i = 0; i < 58; i++)
			is.read();
		int address = is.read() | is.read() << 8 | is.read() << 16
				| is.read() << 24;
		for (int i = 0; i < address - 60; i++)
			is.read();
		int machineType = is.read() | is.read() << 8;
		return machineType == 0x8664;
	}
}
