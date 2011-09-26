package br.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;
import br.zero.commandlineparser.CommandLineParser;
import br.zero.commandlineparser.CommandLineSwitchParam;

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

	public Enum<?> parseComplexEnum(String[] value) {
		Enum<?> e = parseEnum(value[0]);

		CommandLineParser parser = new CommandLineParser();

		// De onde eu vou tirar a propriedade addParser do parser???
		// Posso criar um setter para passar essas informações. Usar uma
		// interface que representa o parser

		return e;
	}

	public String getError() {
		return error;
	}
}
