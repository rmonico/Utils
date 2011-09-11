package org.zero.commandlineparser;

import java.io.File;


public class CommomFilters {

	@CommandLineOptionFilter
	public String filterExistingFile(String fileName) {
		File file = new File(fileName);

		if (!file.exists()) {
			return "Arquivo não encontrado: \"" + fileName + "\".";
		}
		
		return null;
	}
}
