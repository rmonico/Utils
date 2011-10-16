package br.zero.switchesparser;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public interface SwitchesParser {

	void setSwitchesObject(Object o);

	void parse() throws ParserException;
	
	Map<String, Object> getParsers();
	
	boolean hasErrors();
	
	List<IInvalidCommandLineArgument> getErrors();
	
	void printErrors(PrintStream printStream);
}
