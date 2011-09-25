package org.zero.commandlineparser;

public class ParsedSwitch {
	private Integer arg1;

	@CommandLineSwitch(parser = "arg1parser.parse")
	public void setArg1(Integer value) {
		arg1 = value;
	}

	public Integer getArg1() {
		return arg1;
	}
}
