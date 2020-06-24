package com.kichik.pecoff4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.kichik.pecoff4j.io.PEParser;

public class ExeDetect {
	public static void main(String[] args) throws Exception {
		File xxx = new File(
				"C:\\Users\\Someone\\Desktop\\MaxxAudioRealtek64.dll");
		
		PEParser.parse(xxx);
	}
}
