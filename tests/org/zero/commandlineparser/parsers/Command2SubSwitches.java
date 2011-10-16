package org.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineSwitch;

public class Command2SubSwitches {
	private String command2Switch;

	@CommandLineSwitch
	public void setCommand2Switch(String value) {
		command2Switch = value;
	}

	public String getCommand2Switch() {
		return command2Switch;
	}

}
