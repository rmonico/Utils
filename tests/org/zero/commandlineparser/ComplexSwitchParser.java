package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;

public class ComplexSwitchParser {

	@CommandLineArgumentParserMethod(messageMethod="getError")
	public String[] parseComplex(String[] value) {
		return value;
	}
	
	public String getError() {
		return null;
	}

}
