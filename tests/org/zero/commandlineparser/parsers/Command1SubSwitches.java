package org.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineSwitch;

public class Command1SubSwitches {
	private String command1Switch;

	@CommandLineSwitch
	public void setCommand1Switch(String value) {
		command1Switch = value;
	}

	public String getCommand1Switch() {
		return command1Switch;
	}
	
}
