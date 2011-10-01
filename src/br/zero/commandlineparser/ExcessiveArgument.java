package br.zero.commandlineparser;

import br.zero.switchesparser.IInvalidCommandLineArgument;

public class ExcessiveArgument extends AbstractInvalidCommandLineArgument implements IInvalidCommandLineArgument {

	public ExcessiveArgument(String message) {
		super(message);
	}

}
