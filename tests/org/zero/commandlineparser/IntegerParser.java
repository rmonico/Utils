package org.zero.commandlineparser;

class IntegerParser {
	private String error = null;
	
	@CommandLineArgumentParserMethod(errorMessage="getError")
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

