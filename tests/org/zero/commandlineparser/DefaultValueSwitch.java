package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;

public class DefaultValueSwitch {
	private String arg1;

	@CommandLineSwitch(defaultValue = "argument1_value")
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public String getArg1() {
		return arg1;
	}
}

