package com.kichik.pecoff4j;

import java.io.File;

import com.kichik.pecoff4j.io.PEParser;

public class ExeDetect {
	public static void main(String[] args) throws Exception {
		File xxx = new File(
				"C:\\Users\\Someone\\Desktop\\MaxxAudioRealtek64.dll");
		
		PEParser.parse(xxx);
	}
}
