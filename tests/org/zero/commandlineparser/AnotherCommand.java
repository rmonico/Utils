package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitchParam;

public enum AnotherCommand {
	ADD, @CommandLineSwitchParam(name = "rm")
	REMOVE;
}
