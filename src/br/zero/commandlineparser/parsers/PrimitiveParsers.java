package br.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;

public class PrimitiveParsers {
	private String error = null;
	
	@CommandLineArgumentParserMethod(messageMethod="getError")
	public Integer parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			error = "Invalid number format (\"" + value + "\").";
			return 0;
		}
	}
	
	public String getError() {
		return error;
	}
}

