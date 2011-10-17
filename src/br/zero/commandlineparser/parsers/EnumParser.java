package br.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;
import br.zero.commandlineparser.CommandLineSwitchParam;
import br.zero.commandlineparser.ComplexParserParameter;
import br.zero.commandlineparser.ComplexParserReturn;
import br.zero.switchesparser.ParserException;
import br.zero.switchesparser.SwitchesParser;

public class EnumParser {

	@SuppressWarnings("rawtypes")
	private Class<? extends Enum> enumClass;
	private String error;

	public EnumParser(@SuppressWarnings("rawtypes") Class<? extends Enum> enumClass) {
		this.enumClass = enumClass;
	}

	@SuppressWarnings("rawtypes")
	@CommandLineArgumentParserMethod(messageMethod = "getError")
	public Enum parseEnum(String value) {
		for (Enum e : enumClass.getEnumConstants()) {
			String switchParam;
			CommandLineSwitchParam switchParamAnnotation;
			try {
				switchParamAnnotation = enumClass.getDeclaredField(e.name()).getAnnotation(CommandLineSwitchParam.class);
			} catch (SecurityException e1) {
				// Nunca deveria acontecer...
				throw new RuntimeException(e1);
			} catch (NoSuchFieldException e2) {
				// Nunca deveria acontecer...
				throw new RuntimeException(e2);
			}

			if (switchParamAnnotation != null) {
				switchParam = switchParamAnnotation.name();

			} else {
				// Usa o nome da constante como a opção do parâmetro
				switchParam = e.name();
			}

			if (switchParam.equals(value)) {
				return e;
			}
		}

		error = "Não foi possível resolver a constante \"" + value + "\" para o enum \"" + enumClass.getName() + "\".";

		return null;
	}

	private class EnumParserComplexReturn implements ComplexParserReturn {

		private Object subObjectValue;
		private Enum<?> complexSwitchValue;

		@Override
		public Enum<?> getComplexSwitchValue() {
			return complexSwitchValue;
		}
		
		public void setComplexSwitchValue(Enum<?> value) {
			complexSwitchValue = value;
		}
		
		@Override
		public Object getSubObjectValue() {
			return subObjectValue;
		}
		
		public void setSubObjectValue(Object value) {
			subObjectValue = value;
		}
		
	}
	
	@CommandLineArgumentParserMethod(messageMethod = "getError")
	public ComplexParserReturn parseComplexEnum(ComplexParserParameter parameter) {
		
		if (!(parameter.getValuesObject() instanceof String[])) {
			throw new RuntimeException("Tipo de propriedade não suportada por este parser!");
		}
		
		String[] valuesObject = (String[]) parameter.getValuesObject();
		
		Enum<?> e = parseEnum(valuesObject[0]);

		EnumParserComplexReturn r = new EnumParserComplexReturn();

		r.setComplexSwitchValue(e);
		
		SwitchesParser subParser = parameter.getParser().createSubSwitchesParser();
		
		Class<?> subObjectClass = parameter.getSubObjectClasses().get(e.toString());
		
		if (subObjectClass == null) {
			throw new RuntimeException("Classe de subObjeto não encontrada (" + e + ").");
		}
		
		Object subObject = null;
		try {
			subObject = subObjectClass.newInstance();
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		
		subParser.setSwitchesObject(subObject);
		
		subParser.setValuesObject(parameter.getValuesObject());
		
		try {
			subParser.parse();
		} catch (ParserException e1) {
			throw new RuntimeException(e1);
		}
		
		r.setSubObjectValue(subObject); 

		return r;
	}

	public String getError() {
		return error;
	}
}
