package br.zero.commandlineparser;

public class ExcessiveArgument extends AbstractInvalidCommandLineArgument implements IInvalidCommandLineArgument {

	public ExcessiveArgument(String message) {
		super(message);
	}

}
