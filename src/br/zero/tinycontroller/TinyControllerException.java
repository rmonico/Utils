package br.zero.tinycontroller;

public class TinyControllerException extends Exception {

	public TinyControllerException(Exception e) {
		super(e);
	}

	public TinyControllerException(String message, Exception exception) {
		super(message, exception);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5939335310834051927L;

}
