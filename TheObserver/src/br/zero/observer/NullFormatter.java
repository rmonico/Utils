package br.zero.observer;

public class NullFormatter implements Formatter {
	
	public final static NullFormatter instance = new NullFormatter();

	@Override
	public String parse(Object value) throws ObserverException {
		return "[null]";
	}

}
