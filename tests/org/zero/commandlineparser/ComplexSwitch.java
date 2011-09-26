package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;

public class ComplexSwitch {

	private String[] command;

	@CommandLineSwitch(parser = "ComplexSwitchParser.parseComplex", complexParser=true)
	public void setComplexSwitch(String[] value) {
		command = value;
	}

	public String[] getComplexSwitch() {
		return command;
	}

}
