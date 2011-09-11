package org.zero.commandlineparser;

public class ExcessiveArgument extends AbstractInvalidCommandLineArgument implements IInvalidCommandLineArgument {

	public ExcessiveArgument(String message) {
		super(message);
	}

}
