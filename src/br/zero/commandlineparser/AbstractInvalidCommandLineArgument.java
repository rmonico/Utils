package br.zero.commandlineparser;

import br.zero.switchesparser.IInvalidCommandLineArgument;

public abstract class AbstractInvalidCommandLineArgument implements IInvalidCommandLineArgument {

	private String message;

	public AbstractInvalidCommandLineArgument() {
		super();
	}

	public AbstractInvalidCommandLineArgument(String message) {
		this.message = message;
	}

	void setMessage(String value) {
		message = value;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
