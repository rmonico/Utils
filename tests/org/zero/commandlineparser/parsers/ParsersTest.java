package org.zero.commandlineparser.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.zero.commandlineparser.Command;
import org.zero.commandlineparser.CustomCommandLineParserTests;
import org.zero.commandlineparser.EnumSwitch;
import org.zero.commandlineparser.ParsedSwitch;

import br.zero.commandlineparser.parsers.EnumParser;
import br.zero.commandlineparser.parsers.IntegerParser;
import br.zero.switchesparser.ParserException;

public class ParsersTest extends CustomCommandLineParserTests {

	@Test
	public void testParsedSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "Arg1", "45" });

		parser.getPropertyParsers().put("arg1parser", new IntegerParser());

		ParsedSwitch switches = new ParsedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("Arg1", (int) 45, (int) switches.getArg1());
	}

	@Test
	public void testEnumSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "Command", "ADD" });

		EnumSwitch so = new EnumSwitch();

		parser.setSwitchesObject(so);

		parser.getPropertyParsers().put("EnumParser", new EnumParser(Command.class));

		parser.parse();

		assertEquals("Command", Command.ADD, so.getCommand());
	}

	/**
	 * Testa um opção de enum nomeada. No exemplo, o item REMOVE do enum é
	 * setado pelo parâmetro "rm" da linha de comando.
	 * 
	 * @throws ParserException
	 */
	@Test
	public void testNamedEnumSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "Command", "rm" });

		CommandLineOptionSwitch switches = new CommandLineOptionSwitch();

		parser.setSwitchesObject(switches);

		parser.getPropertyParsers().put("EnumParser", new EnumParser(AnotherCommand.class));

		parser.parse();

		assertEquals("default param", AnotherCommand.REMOVE, switches.getCommand());
	}

	@Test
	public void test1ComplexEnumSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "ComplexCommand", "COMMAND1", "setCommand1Switch", "Command1Value" });

		parser.getPropertyParsers().put("EnumParser", new EnumParser(ComplexCommand.class));

		MainBean switches = new MainBean();

		parser.setSwitchesObject(switches);
		
		parser.parse();

		assertEquals("Main Bean - Command", ComplexCommand.COMMAND1, switches.getComplexCommand());
		
		assertNotNull("Complex command - sub switches 1 not null", switches.getCommand1SubSwitches());
		assertNull("Complex command - sub switches 2 null", switches.getCommand2SubSwitches());

		assertEquals("Complex switch - sub switches 1 contents", "Command1Value", switches.getCommand1SubSwitches().getCommand1Switch());

		// O switch complexo consumiu toda a linha de comando
		assertFalse("Complex switch - no errors", parser.hasErrors());
	}

	@Test
	public void test2ComplexEnumSwitch() throws ParserException {
		parser.setValuesObject(new String[] { "ComplexCommand", "COMMAND2", "setCommand2Switch", "Command2Value" });

		parser.getPropertyParsers().put("EnumParser", new EnumParser(ComplexCommand.class));

		MainBean switches = new MainBean();

		parser.setSwitchesObject(switches);
		
		parser.parse();

		assertEquals("Main Bean - Command", ComplexCommand.COMMAND2, switches.getComplexCommand());
		
		assertNull("Complex command - sub switches 1 null", switches.getCommand1SubSwitches());
		assertNotNull("Complex command - sub switches 2 not null", switches.getCommand2SubSwitches());

		assertEquals("Complex switch - sub switches 2 contents", "Command2Value", switches.getCommand2SubSwitches().getCommand2Switch());

		// O switch complexo consumiu toda a linha de comando
		assertFalse("Complex switch - no errors", parser.hasErrors());
	}
}
