package org.zero.commandlineparser;

public abstract class AbstractCommandLineParsingError implements ICommandLineParsingError {

	private String message;

	public AbstractCommandLineParsingError() {
		super();
	}

	public AbstractCommandLineParsingError(String message) {
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
