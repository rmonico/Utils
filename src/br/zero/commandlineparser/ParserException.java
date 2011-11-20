package br.zero.commandlineparser;

public class ParserException extends Exception {

	public ParserException(Exception e) {
		super(e);
	}

	public ParserException(String s) {
		super(s);
	}

	public ParserException(String s, Exception e) {
		super(s, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1990370538727539663L;

}
