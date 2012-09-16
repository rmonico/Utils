package br.zero.tinycontroller;

public class TinyControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5939335310834051927L;
	
	public TinyControllerException(Exception e) {
		super(e);
	}

	public TinyControllerException(String message, Exception exception) {
		super(message, exception);
	}

	public TinyControllerException(String message) {
		super(message);
	}

}
