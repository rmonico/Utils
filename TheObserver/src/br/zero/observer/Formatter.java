package br.zero.observer;

public interface Formatter {
	public String parse(Object value) throws ObserverException;
}
