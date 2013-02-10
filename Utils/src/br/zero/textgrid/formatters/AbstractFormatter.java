package br.zero.textgrid.formatters;

import br.zero.textgrid.TextGridFormatter;

public abstract class AbstractFormatter extends TextGridFormatter {

	private StringBuilder nullString = new StringBuilder("[null]");

	public StringBuilder getNullString() {
		return nullString;
	}

	public void setNullString(StringBuilder nullString) {
		this.nullString = nullString;
	}
}
