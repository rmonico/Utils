package org.zero.commandlineparser.parsers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.zero.commandlineparser.AnotherCommand;
import org.zero.commandlineparser.Command;
import org.zero.commandlineparser.CommandLineOptionSwitch;
import org.zero.commandlineparser.CustomCommandLineParserTests;
import org.zero.commandlineparser.EnumSwitch;
import org.zero.commandlineparser.ParsedSwitch;

import br.zero.commandlineparser.parsers.EnumParser;
import br.zero.commandlineparser.parsers.IntegerParser;
import br.zero.switchesparser.ParserException;

public class ParsersTest extends CustomCommandLineParserTests {

	@Test
	public void testParsedSwitch() throws ParserException {
		parser.setCommandLine(new String[] { "Arg1", "45" });

		parser.addParser("arg1parser", new IntegerParser());

		ParsedSwitch switches = new ParsedSwitch();

		parser.setSwitchesObject(switches);

		parser.parse();

		assertEquals("Arg1", (int) 45, (int) switches.getArg1());
	}

	@Test
	public void testEnumSwitch() throws ParserException {
		parser.setCommandLine(new String[] { "Command", "ADD" });

		EnumSwitch so = new EnumSwitch();

		parser.setSwitchesObject(so);

		parser.addParser("EnumParser", new EnumParser(Command.class));

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
		parser.setCommandLine(new String[] { "Command", "rm" });

		CommandLineOptionSwitch switches = new CommandLineOptionSwitch();

		parser.setSwitchesObject(switches);

		parser.addParser("EnumParser", new EnumParser(AnotherCommand.class));

		parser.parse();

		assertEquals("default param", AnotherCommand.REMOVE, switches.getCommand());
	}

}
