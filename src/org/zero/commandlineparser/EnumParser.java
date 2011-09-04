package org.zero.commandlineparser;

public class EnumParser {

	@SuppressWarnings("rawtypes")
	private Class<? extends Enum> enumClass;

	public EnumParser(@SuppressWarnings("rawtypes") Class<? extends Enum> enumClass) {
		this.enumClass = enumClass;
	}

	@SuppressWarnings("rawtypes")
	public Enum parseEnum(String value) {
		// TODO Usar o valueOf do próprio Enum
		for (Enum e : enumClass.getEnumConstants()) {
			// TODO: Trocar o upperCase da linha abaixo por uma anotação na
			// constante do enum
			if (e.name().equals(value.toUpperCase())) {
				return e;
			}
		}

		return null;
	}
}
