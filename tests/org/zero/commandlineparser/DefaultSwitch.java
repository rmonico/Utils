package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;

public class DefaultSwitch {
	private Command command;

	@CommandLineSwitch(parser = "EnumParser.parseEnum", index = 1)
	public void setCommand(Command value) {
		command = value;
	}

	public Command getCommand() {
		return command;
	}
}
