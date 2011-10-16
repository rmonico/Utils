package org.zero.commandlineparser.parsers;


import br.zero.commandlineparser.CommandLineSwitch;

public class CommandLineOptionSwitch {
	private AnotherCommand command;

	@CommandLineSwitch(parser = "EnumParser.parseEnum")
	public void setCommand(AnotherCommand value) {
		command = value;
	}

	public AnotherCommand getCommand() {
		return command;
	}
}

