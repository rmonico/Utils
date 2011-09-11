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
	public void testMinimalParsing() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "Arg1", "arg1_value" });

		BasicSwitch switches = new BasicSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("Arg1", "arg1_value", switches.getArg1());
	}

	@Test
	public void testNamedSwitches() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "argument1", "argument1_value" });

		NamedSwitch switches = new NamedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("argument1", "argument1_value", switches.getArg1());
	}

	@Test
	public void testDefaultValueSwitch() throws CommandLineParserException {
		parser.setCommandLine(new String[] {});

		DefaultValueSwitch switches = new DefaultValueSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("arg1", "argument1_value", switches.getArg1());
	}

	@Test
	public void testParsedSwitch() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "Arg1", "45" });

		parser.addParser("arg1parser", new IntegerParser());

		ParsedSwitch switches = new ParsedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("Arg1", (int) 45, (int) switches.getArg1());
	}

	@Test
	public void testEnumSwitch() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "Command", "ADD" });

		EnumSwitch so = new EnumSwitch();

		parser.setSwitchesObject(so);

		parser.addParser("EnumParser", new EnumParser(Command.class));

		parser.parse();

		assertEquals("Command", Command.ADD, so.getCommand());
	}

	@Test
	public void testBooleanSwitch() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "help" });

		BooleanSwitch switches = new BooleanSwitch();

		switches.setShowHelp(false);

		parser.setSwitchesObject(switches);

		parser.addParser("EnumParser", new EnumParser(Command.class));
		
		parser.parse();

		assertEquals("show help", true, switches.getShowHelp());
	}

	@Test
	public void testDefaultSwitch() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "REMOVE" });

		DefaultSwitch switches = new DefaultSwitch();

		parser.setSwitchesObject(switches);

		parser.addParser("EnumParser", new EnumParser(Command.class));

		parser.parse();

		assertEquals("default param", Command.REMOVE, switches.getCommand());
	}

	@Test
	public void testCommandLineSwitchOption() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "Command", "rm" });

		CommandLineOptionSwitch switches = new CommandLineOptionSwitch();

		parser.setSwitchesObject(switches);

		parser.addParser("EnumParser", new EnumParser(AnotherCommand.class));

		parser.parse();
		
		assertEquals("default param", AnotherCommand.REMOVE, switches.getCommand());
	}
	
	@Test
	public void testMultipleNamedSwitch() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "-arg", "option1" });

		MultipleNamedSwitch switches = new MultipleNamedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();
		
		assertEquals("-arg", "option1", switches.getArgument());

	
		parser.setCommandLine(new String[] { "--argument", "option2" });

		switches = new MultipleNamedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();
		
		assertEquals("-arg", "option2", switches.getArgument());
	}
	
	@Test
	public void testExcessiveCommandLineArgs() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "Arg1", "arg1_value", "excessive argument"});

		BasicSwitch switches = new BasicSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("excessive argument - size", 1, parser.getExcessiveArguments().size());
		
		assertEquals("excessive argument - itens", "excessive argument", parser.getExcessiveArguments().get(0));
		
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

class DefaultSwitch {
	private Command command;

	@CommandLineSwitch(parser = "EnumParser.parseEnum", index=1)
	public void setCommand(Command value) {
		command = value;
	}

	public Command getCommand() {
		return command;
	}
}

enum AnotherCommand {
	ADD,
	@CommandLineSwitchParam(name = "rm")
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

class MultipleNamedSwitch {
	private String argument;

	@CommandLineSwitch(param={"-arg", "--argument"})
	public void setArgument(String value) {
		argument = value;
	}

	public String getArgument() {
		return argument;
	}
	
}