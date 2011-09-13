package org.zero.commandlineparser;

public class EnumSwitch {
	private Command command;

	@CommandLineSwitch(parser = "EnumParser.parseEnum")
	public void setCommand(Command value) {
		command = value;
	}

	public Command getCommand() {
		return command;
	}
}

