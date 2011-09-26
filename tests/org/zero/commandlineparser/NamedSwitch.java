package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;

public class NamedSwitch {

	private String arg1;

	@CommandLineSwitch(param = "argument1")
	public void setArg1(String value) {
		arg1 = value;
	}

	public String getArg1() {
		return arg1;
	}

}
