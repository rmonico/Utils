package br.zero.observer;

public class DefaultFormatter implements Formatter {

	@Override
	public String parse(Object value) throws ObserverException {
		if (value == null) {
			return NullFormatter.instance.parse(value);
		} else {
			return value.toString();
		}
	}

}
