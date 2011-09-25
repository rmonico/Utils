package br.zero.commandlineparser;

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
