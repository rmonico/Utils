package org.zero.commandlineparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.zero.commandlineparser.CommandLineOptionParsingError;
import br.zero.commandlineparser.CommandLineParserException;
import br.zero.commandlineparser.ExcessiveArgument;
import br.zero.commandlineparser.parsers.EnumParser;
import br.zero.commandlineparser.parsers.IntegerParser;

public class CommandLineParserTest extends CustomCommandLineParserTests {

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
	public void testBooleanSwitch() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "help" });

		BooleanSwitch switches = new BooleanSwitch();

		switches.setShowHelp(false);

		parser.setSwitchesObject(switches);

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
	public void testExcessiveCommandLineArgument() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "Arg1", "arg1_value", "excessive argument" });

		BasicSwitch switches = new BasicSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("excessive argument - size", 1, parser.getErrors().size());

		assertTrue("excessive argument - itens", parser.getErrors().get(0) instanceof ExcessiveArgument);

		assertEquals("excessive argument - item 0, valor", "excessive argument", parser.getErrors().get(0).getMessage());
	}

	@Test
	public void testParserError() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "Arg1", "xxx" });

		parser.addParser("arg1parser", new IntegerParser());

		ParsedSwitch switches = new ParsedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("parser error - size", 1, parser.getErrors().size());

		assertTrue("parser error - item 0, class", parser.getErrors().get(0) instanceof CommandLineOptionParsingError);

		assertEquals("parser error - item 0, valor", "Formato incorreto do número (\"xxx\").", parser.getErrors().get(0).getMessage());
	}

	/**
	 * Complex switch: permite que um parser receba o restante da linha de
	 * comando depois dele.
	 * 
	 * @throws CommandLineParserException
	 */
	@Test
	public void testComplexSwitch() throws CommandLineParserException {
		parser.setCommandLine(new String[] { "ComplexSwitch", "param1", "param2" });

		parser.addParser("ComplexSwitchParser", new ComplexSwitchParser());

		ComplexSwitch switches = new ComplexSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("Complex switch - size", 2, switches.getComplexSwitch().length);

		assertEquals("Complex switch - item 0", "param1", switches.getComplexSwitch()[0]);

		assertEquals("Complex switch - item 1", "param2", switches.getComplexSwitch()[1]);

		assertFalse("Complex switch - no errors", parser.hasErrors());
	}
}
