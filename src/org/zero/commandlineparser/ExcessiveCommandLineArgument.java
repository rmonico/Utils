package org.zero.commandlineparser;

public class ExcessiveCommandLineArgument extends AbstractCommandLineParsingError implements ICommandLineParsingError {

	public ExcessiveCommandLineArgument(String message) {
		super(message);
	}

}
