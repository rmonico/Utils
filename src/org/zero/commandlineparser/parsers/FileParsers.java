package org.zero.commandlineparser.parsers;

import java.io.File;

import org.zero.commandlineparser.CommandLineArgumentParserMethod;

public class FileParsers {
	private String error = null;
	
	@CommandLineArgumentParserMethod(messageMethod="getError")
	public File parseExistingFile(String value) {
		File file = new File(value);
		
		if (!file.exists()) {
			error = "Arquivo não encontrado.";
			
			return null;
		}
		
		error = null;
		
		return file;
	}
	
	public String getError() {
		return error;
	}

}
