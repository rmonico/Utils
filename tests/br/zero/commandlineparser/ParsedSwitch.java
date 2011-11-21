package br.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;

public class ParsedSwitch {
	private Integer arg1;

	@CommandLineSwitch(parser = "arg1parser.parseInteger")
	public void setArg1(Integer value) {
		arg1 = value;
	}

	public Integer getArg1() {
		return arg1;
	}
}

