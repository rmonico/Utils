package br.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;
import br.zero.commandlineparser.ComplexParserParameter;
import br.zero.commandlineparser.ComplexParserReturn;

public class ComplexSwitchParser {

	@CommandLineArgumentParserMethod(messageMethod = "getError")
	public ComplexParserReturn parseComplex(ComplexParserParameter parameter) {
		
		ComplexSwitchParserReturn r = new ComplexSwitchParserReturn();

		String arg0 = ((String[]) parameter.getValuesObject())[0];
		
		String subObject;
		
		if ("ComplexSwitchValue_Param1".equals(arg0)) {
			subObject = "param1value";
		} else if ("ComplexSwitchValue_Param2".equals(arg0)) {
			subObject = "param2value";
		} else {
			subObject = null;
		}
		
		r.setSubObjectValue(subObject);
		
		r.setComplexSwitchValue(arg0);
		
		return r;
	}

	public String getError() {
		return null;
	}

}
