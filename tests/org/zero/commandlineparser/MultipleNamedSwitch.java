package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;

public class MultipleNamedSwitch {
	private String argument;

	@CommandLineSwitch(param = { "-arg", "--argument" })
	public void setArgument(String value) {
		argument = value;
	}

	public String getArgument() {
		return argument;
	}
}