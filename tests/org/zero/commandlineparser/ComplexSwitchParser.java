package org.zero.commandlineparser;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;
import br.zero.commandlineparser.ComplexParserParameter;
import br.zero.commandlineparser.ComplexParserReturn;

public class ComplexSwitchParser {
	
	private class ComplexSwitchParserReturn implements ComplexParserReturn {

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

	@CommandLineArgumentParserMethod(messageMethod = "getError")
	public ComplexParserReturn parseComplexEnum(ComplexParserParameter value) {
		ComplexSwitchParserReturn r = new ComplexSwitchParserReturn();
		
		r.setSubObjectValue(value.getArgs());
		r.setComplexSwitchValue(null);
		
		return r;
	}

	public String getError() {
		return null;
	}

}
