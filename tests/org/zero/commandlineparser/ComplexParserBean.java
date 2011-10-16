package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;
import br.zero.commandlineparser.SubCommandLine;

public class ComplexParserBean {

	private String complexSwitch;
	private String param1;
	private String param2;

	@CommandLineSwitch(parser = "ComplexSwitchParser.parseComplex", complexParser = true, subCommandLineProperties = { 
				@SubCommandLine(value = "ComplexSwitchValue_Param1", propertyName = "setParam1"),
				@SubCommandLine(value = "ComplexSwitchValue_Param2", propertyName = "setParam2") })
	public void setComplexSwitch(String value) {
		complexSwitch = value;
	}

	public String getComplexSwitch() {
		return complexSwitch;
	}
	
	public void setParam1(String value) {
		param1 = value;
	}
	
	public String getParam1() {
		return param1;
	}

	public void setParam2(String value) {
		param2 = value;
	}
	
	public Object getParam2() {
		return param2;
	}

}
