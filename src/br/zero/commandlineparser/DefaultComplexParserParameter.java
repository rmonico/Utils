package br.zero.commandlineparser;


class DefaultComplexParserParameter implements ComplexParserParameter {

	private String[] args;
//	private Map<String, Object> parsers;

	@Override
	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] value) {
		args = value;
	}

//	@Override
//	public Map<String, Object> getParsers() {
//		return parsers;
//	}
//
//	public void setParsers() {
//	}
}
