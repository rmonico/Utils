package br.zero.observer;

public class ObserverException extends Exception {

	public ObserverException(Exception e) {
		super(e);
	}

	public ObserverException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5964600854751592840L;

}
