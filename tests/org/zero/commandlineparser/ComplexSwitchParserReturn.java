package org.zero.commandlineparser;

import br.zero.commandlineparser.ComplexParserReturn;

class ComplexSwitchParserReturn implements ComplexParserReturn {

	private Object complexSwitchValue;
	private Object subObjectValue;

	@Override
	public Object getComplexSwitchValue() {
		// TODO Auto-generated method stub
		return complexSwitchValue;
	}
	
	public void setComplexSwitchValue(Object value) {
		complexSwitchValue = value;
	}

	@Override
	public Object getSubObjectValue() {
		return subObjectValue;
	}
	
	public void setSubObjectValue(Object value) {
		this.subObjectValue = value;
	}
	
}