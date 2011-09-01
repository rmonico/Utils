package org.zero.commandlineparser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CommandLineParserTest {

	CommandLineParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new CommandLineParser();
	}

	@Test
	public void testMinimalParsing() {
		parser.setCommandLine(new String[] { "Arg1", "arg1_value" });

		BasicSwitch switches = new BasicSwitch();

		parser.setSwitchesObject(switches);

		parser.doParsing();

		assertEquals("Arg1", "arg1_value", switches.getArg1());
	}

	@Test
	public void testNamedSwitches() {
		parser.setCommandLine(new String[] { "argument1", "argument1_value" });

		NamedSwitch switches = new NamedSwitch();

		parser.setSwitchesObject(switches);

		parser.doParsing();

		assertEquals("argument1", "argument1_value", switches.getArg1());
	}

	@Test
	public void testDefaultValueSwitch() {
		parser.setCommandLine(new String[] {});

		DefaultValueSwitch switches = new DefaultValueSwitch();

		parser.setSwitchesObject(switches);

		parser.doParsing();

		assertEquals("arg1", "argument1_value", switches.getArg1());
	}

	@Test
	public void testParsedSwitch() {
		parser.setCommandLine(new String[] { "Arg1", "45" });

		parser.addParser("arg1parser", new IntegerParser());

		ParsedSwitch switches = new ParsedSwitch();

		parser.setSwitchesObject(switches);

		parser.doParsing();

		assertEquals("Arg1", (int) 45, (int) switches.getArg1());
	}

	@Test
	public void testEnumSwitch() {
		CommandLineParser cmdlParser = new CommandLineParser();

		cmdlParser.setCommandLine(new String[] { "Command", "add" });

		EnumSwitch so = new EnumSwitch();

		cmdlParser.setSwitchesObject(so);

		cmdlParser.addParser("EnumParser", new EnumParser(Command.class));

		cmdlParser.doParsing();

		assertEquals("Command", Command.ADD, so.getCommand());
	}

	@Test
	public void testBooleanSwitch() {
		parser.setCommandLine(new String[] { "help" });

		BooleanSwitch switches = new BooleanSwitch();

		switches.setShowHelp(false);

		parser.setSwitchesObject(switches);

		parser.doParsing();

		assertEquals("show help", true, switches.getShowHelp());
	}

	@Test
	public void testDefaultSwitch() {
		parser.setCommandLine(new String[] { "remove" });

		DefaultSwitch switches = new DefaultSwitch();

		parser.setSwitchesObject(switches);

		parser.doParsing();

		assertEquals("default param", Command.REMOVE, switches.getCommand());
	}

	@Test
	public void testCommandLineSwitchOption() {
		parser.setCommandLine(new String[] { "Command", "rm" });

		CommandLineOptionSwitch switches = new CommandLineOptionSwitch();

		parser.setSwitchesObject(switches);

		parser.doParsing();

		assertEquals("default param", AnotherCommand.REMOVE, switches.getCommand());
	}
}

class BasicSwitch {

	private String arg1;

	@CommandLineSwitch
	public void setArg1(String value) {
		arg1 = value;
	}

	public String getArg1() {
		return arg1;
	}

}

class NamedSwitch {

	private String arg1;

	@CommandLineSwitch(param = "argument1")
	public void setArg1(String value) {
		arg1 = value;
	}

	public String getArg1() {
		return arg1;
	}

}

class DefaultValueSwitch {
	private String arg1;

	@CommandLineSwitch(defaultValue = "argument1_value")
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public String getArg1() {
		return arg1;
	}
}

class IntegerParser {
	public Integer parse(String value) {
		return Integer.parseInt(value);
	}
}

class ParsedSwitch {
	private Integer arg1;

	@CommandLineSwitch(parser = "arg1parser.parse")
	public void setArg1(Integer value) {
		arg1 = value;
	}

	public Integer getArg1() {
		return arg1;
	}
}

enum Command {
	ADD, REMOVE;
}

class EnumSwitch {
	private Command command;

	@CommandLineSwitch(parser = "EnumParser.parseEnum")
	public void setCommand(Command value) {
		command = value;
	}

	public Command getCommand() {
		return command;
	}
}

class BooleanSwitch {
	private boolean showHelp;

	@CommandLineSwitch(param = "help")
	public void setShowHelp(boolean value) {
		showHelp = value;
	}

	public boolean getShowHelp() {
		return showHelp;
	}
}

//@CommandLineBean(defaultSwitch = "Command")
class DefaultSwitch {
	private Command command;

	@CommandLineSwitch(parser = "EnumParser.parseEnum")
	public void setCommand(Command value) {
		command = value;
	}

	public Command getCommand() {
		return command;
	}
}

enum AnotherCommand {
	ADD,
//	@CommandLineSwitchParam(name = "rm")
	REMOVE;
}

class CommandLineOptionSwitch {
	private AnotherCommand command;

	@CommandLineSwitch(parser = "EnumParser.parseEnum")
	public void setCommand(AnotherCommand value) {
		command = value;
	}

	public AnotherCommand getCommand() {
		return command;
	}
}