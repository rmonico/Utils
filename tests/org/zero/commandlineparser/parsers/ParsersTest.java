package org.zero.commandlineparser.parsers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.zero.commandlineparser.Command;
import org.zero.commandlineparser.CommandLineParserException;
import org.zero.commandlineparser.CustomCommandLineParserTests;
import org.zero.commandlineparser.EnumSwitch;
import org.zero.commandlineparser.ParsedSwitch;

public class ParsersTest extends CustomCommandLineParserTests {

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

}
