package br.zero.switchesparser;

import java.io.PrintStream;
import java.util.List;


public interface SwitchesParser {

	void setSwitchesObject(Object o);

	void parse() throws ParserException;
	
	void addParser(String parserId, Object parser);
	
	boolean hasErrors();
	
	List<IInvalidCommandLineArgument> getErrors();
	
	void printErrors(PrintStream printStream);
}
