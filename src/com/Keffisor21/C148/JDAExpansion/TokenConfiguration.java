package com.Keffisor21.C148.JDAExpansion;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TokenConfiguration {
	//part of my proyect JDAExpansion (https://github.com/Keffisor/JDAExpansion/blob/57c82d8dd73d3278e80ffd588ec3f016226449f5/JDAExpansion/src/com/Keffisor21/JDAExpansion/ConfigManager/TokenConfiguration.java#L12)
	public static String getTokenFileContent() {
		File file = new File("token.txt");
		if(!file.exists()) {
			try {
			file.createNewFile();
			file.setWritable(true, false);
			file.setReadable(true, false);
			} catch (IOException e) {
				System.out.println("Failed on creating the token.txt");
			}
		}
		try {
			return Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8).get(0);
		} catch (IOException | IndexOutOfBoundsException e) {
            return "";
		}
	}
}