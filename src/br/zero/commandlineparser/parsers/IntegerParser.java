package br.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;

public class IntegerParser {
	private String error = null;
	
	@CommandLineArgumentParserMethod(messageMethod="getError")
	public Integer parse(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			error = "Formato incorreto do número (\"" + value + "\").";
			return 0;
		}
	}
	
	public String getError() {
		return error;
	}
}

