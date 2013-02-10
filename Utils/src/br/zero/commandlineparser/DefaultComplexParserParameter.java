package br.zero.commandlineparser;

import java.util.HashMap;
import java.util.Map;

class DefaultComplexParserParameter implements ComplexParserParameter {

	private CommandLineParser parser;
	private String[] valuesObject;
	private Map<String, Class<?>> subObjectClasses = new HashMap<String, Class<?>>();
	
	@Override
	public CommandLineParser getParser() {
		return parser;
	}

	public void setParser(CommandLineParser parser) {
		this.parser = parser;
	}

	@Override
	public String[] getValuesObject() {
		return valuesObject;
	}

	public void setValuesObject(String[] valuesObject) {
		this.valuesObject = valuesObject;
	}

	@Override
	public Map<String, Class<?>> getSubObjectClasses() {
		return subObjectClasses;
	}

}
