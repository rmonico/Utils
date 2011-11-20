package br.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineSwitchParam;

public enum AnotherCommand {
	ADD, @CommandLineSwitchParam(name = "rm")
	REMOVE;
}
