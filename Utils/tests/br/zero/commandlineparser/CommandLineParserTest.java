package br.zero.commandlineparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.zero.commandlineparser.CommandLineOptionParsingInvalid;
import br.zero.commandlineparser.ExcessiveArgument;
import br.zero.commandlineparser.ParserException;
import br.zero.commandlineparser.parsers.EnumParser;
import br.zero.commandlineparser.parsers.PrimitiveParsers;

public class CommandLineParserTest extends CustomCommandLineParserTests {

	@Test
	public void testMinimalParsing() throws ParserException {
		parser.setValuesObject(new String[] { "Arg1", "arg1_value" });

		BasicSwitch switches = new BasicSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("Arg1", "arg1_value", switches.getArg1());
	}

	@Test
	public void testNamedSwitches() throws ParserException {
		parser.setValuesObject(new String[] { "argument1", "argument1_value" });

		NamedSwitch switches = new NamedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("argument1", "argument1_value", switches.getArg1());
	}

	@Test
	public void testDefaultValueSwitch() throws ParserException {
		parser.setValuesObject(new String[] {});

		DefaultValueSwitch switches = new DefaultValueSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("arg1", "argument1_value", switches.getArg1());
	}

	@Test
	public void testBooleanSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "help" });

		BooleanSwitch switches = new BooleanSwitch();

		switches.setShowHelp(false);

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("show help", true, switches.getShowHelp());
	}

	@Test
	public void testDefaultSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "REMOVE" });

		DefaultSwitch switches = new DefaultSwitch();

		parser.setSwitchesObject(switches);

		parser.getPropertyParsers().put("EnumParser", new EnumParser(Command.class));

		parser.parse();

		assertEquals("default param", Command.REMOVE, switches.getCommand());
	}

	@Test
	public void testMultipleNamedSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "-arg", "option1" });

		MultipleNamedSwitch switches = new MultipleNamedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("-arg", "option1", switches.getArgument());

		parser.setValuesObject(new String[] { "--argument", "option2" });

		switches = new MultipleNamedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("-arg", "option2", switches.getArgument());
	}

	@Test
	public void testExcessiveCommandLineArgument() throws ParserException {
		parser.setValuesObject(new String[] { "Arg1", "arg1_value", "excessive argument" });

		BasicSwitch switches = new BasicSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("excessive argument - size", 1, parser.getErrors().size());

		assertTrue("excessive argument - itens", parser.getErrors().get(0) instanceof ExcessiveArgument);

		assertEquals("excessive argument - item 0, valor", "excessive argument", parser.getErrors().get(0).getMessage());
	}

	@Test
	public void testParserError() throws ParserException {
		parser.setValuesObject(new String[] { "Arg1", "xxx" });

		parser.getPropertyParsers().put("arg1parser", new PrimitiveParsers());

		ParsedSwitch switches = new ParsedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("parser error - size", 1, parser.getErrors().size());

		assertTrue("parser error - item 0, class", parser.getErrors().get(0) instanceof CommandLineOptionParsingInvalid);

		assertEquals("parser error - item 0, valor", "Invalid number format (\"xxx\").", parser.getErrors().get(0).getMessage());
	}

	/**
	 * Complex switch: permite que um parser receba o restante da linha de
	 * comando depois dele.
	 * 
	 * @throws ParserException
	 */
	@Test
	public void test1ComplexSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "ComplexSwitch", "ComplexSwitchValue_Param1", "param1", "param1value" });

		parser.getPropertyParsers().put("ComplexSwitchParser", new ComplexSwitchParser());

		ComplexParserBean switches = new ComplexParserBean();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("Complex switch - value", "ComplexSwitchValue_Param1", switches.getComplexSwitch());

		assertEquals("SubCommandLine - Param 1", "param1value", switches.getParam1());

		// O switch complexo consumiu toda a linha de comando
		assertFalse("Complex switch - no errors", parser.hasErrors());
	}
		
	@Test
	public void test2ComplexSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "ComplexSwitch", "ComplexSwitchValue_Param2", "param2", "param2value" });

		parser.getPropertyParsers().put("ComplexSwitchParser", new ComplexSwitchParser());

		ComplexParserBean switches = new ComplexParserBean();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("Complex switch - value", "ComplexSwitchValue_Param2", switches.getComplexSwitch());

		assertEquals("SubCommandLine - Param 2", "param2value", switches.getParam2());

		// O switch complexo consumiu toda a linha de comando
		assertFalse("Complex switch - no errors", parser.hasErrors());
	}
}
