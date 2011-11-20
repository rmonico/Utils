package br.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineSwitch;
import br.zero.commandlineparser.SubCommandLine;

public class MainBean {
	private ComplexCommand complexCommand;
	private Command1SubSwitches command1SubSwitches;
	private Command2SubSwitches command2SubSwitches;

	@CommandLineSwitch(parser = "EnumParser.parseComplexEnum", complexParser = true, subCommandLineProperties = { 
			@SubCommandLine(value="COMMAND1", propertyName="setCommand1SubSwitches", subCommandLineClass=Command1SubSwitches.class),
			@SubCommandLine(value="COMMAND2", propertyName="setCommand2SubSwitches", subCommandLineClass=Command2SubSwitches.class) })
	public void setComplexCommand(ComplexCommand complexCommand) {
		this.complexCommand = complexCommand;
	}

	public ComplexCommand getComplexCommand() {
		return complexCommand;
	}

	public void setCommand1SubSwitches(Command1SubSwitches value) {
		command1SubSwitches = value;
	}

	public Command1SubSwitches getCommand1SubSwitches() {
		return command1SubSwitches;
	}

	public void setCommand2SubSwitches(Command2SubSwitches value) {
		command2SubSwitches = value;
	}

	public Command2SubSwitches getCommand2SubSwitches() {
		return command2SubSwitches;
	}

}
