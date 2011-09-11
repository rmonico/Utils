package org.zero.commandlineparser;

class IntegerParser {
	@CommandLineArgumentParserMethod
	public Integer parse(String value) {
		return Integer.parseInt(value);
	}
	
}

