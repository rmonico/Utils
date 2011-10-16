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
	public ComplexParserReturn parseComplex(ComplexParserParameter value) {
		ComplexSwitchParserReturn r = new ComplexSwitchParserReturn();
		
		// TODO Chamar o CommandLineParser para descobrir esse valor 
		r.setSubObjectValue("param1value");
		r.setComplexSwitchValue(value.getArgs()[0]);
		
		return r;
	}

	public String getError() {
		return null;
	}

}
