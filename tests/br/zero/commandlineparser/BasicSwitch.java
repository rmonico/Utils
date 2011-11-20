package br.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;

public class BasicSwitch {
	private String arg1;

	@CommandLineSwitch
	public void setArg1(String value) {
		arg1 = value;
	}

	public String getArg1() {
		return arg1;
	}

}
