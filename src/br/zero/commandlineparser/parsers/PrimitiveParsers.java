package br.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;

public class PrimitiveParsers {
	private String error;
	
	@CommandLineArgumentParserMethod(messageMethod="getError")
	public Integer parseInteger(String value) {
		error = null;
		
		if ("null".equals(value)) {
			return null;
		}
		
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			error = "Invalid number format (\"" + value + "\").";
			return 0;
		}
	}
	
	@CommandLineArgumentParserMethod(messageMethod="getError")
	public Double parseDouble(String value) {
		error = null;
		
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException nfe) {
			error = "Invalid number format (\"" + value + "\").";
			return 0.0;
		}
	}

	public String getError() {
		return error;
	}
}

