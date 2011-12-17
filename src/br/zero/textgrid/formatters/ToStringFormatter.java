package br.zero.textgrid.formatters;

import br.zero.textgrid.TextGridException;

public class ToStringFormatter extends AbstractFormatter {

	public ToStringFormatter() {
		
	}
	
	public ToStringFormatter(String nullString) {
		this();
		
		setNullString(new StringBuilder(nullString));
	}
	
	@Override
	public StringBuilder parse(Object cellValue) throws TextGridException {
		if (cellValue == null) {
			return getNullString();
		}
		
		return new StringBuilder(cellValue.toString());
	}

}
