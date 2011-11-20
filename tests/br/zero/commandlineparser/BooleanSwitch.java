package br.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineSwitch;

public class BooleanSwitch {
	private boolean showHelp;

	@CommandLineSwitch(param = "help")
	public void setShowHelp(boolean value) {
		showHelp = value;
	}

	public boolean getShowHelp() {
		return showHelp;
	}

}

