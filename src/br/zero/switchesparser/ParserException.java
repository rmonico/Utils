package br.zero.switchesparser;


public class ParserException extends Exception {

	public ParserException(Exception e) {
		super(e);
	}

	public ParserException(String s) {
		super(s);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1990370538727539663L;

}
