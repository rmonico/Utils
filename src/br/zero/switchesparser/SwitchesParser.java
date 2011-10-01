package br.zero.switchesparser;

import java.io.PrintStream;
import java.util.List;

import br.zero.commandlineparser.IInvalidCommandLineArgument;

public interface SwitchesParser {

	void setSwitchesObject(Object o);

	void parse() throws ParserException;
	
	void addParser(String parserId, Object parser);
	
	List<IInvalidCommandLineArgument> getErrors();
	
	boolean hasErrors();
	
	void printErrors(PrintStream printStream);
}
