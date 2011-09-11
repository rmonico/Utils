package org.zero.commandlineparser;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

// TODO Levar em conta o uso de prefixos para os argumentos.
// TODO Devolver os itens excessivos na linha de comando
// TODO Suportar múltiplos nomes para a mesma propriedade na anotação CommandLineSwitch
public class CommandLineParser {

	private String[] commandLine;
	private Object switchesObject;
	private Map<String, Object> parsers = new HashMap<String, Object>();

	public void setCommandLine(String[] commandLine) {
		this.commandLine = commandLine;
	}

	public void setSwitchesObject(Object o) {
		switchesObject = o;
	}

	public void doParsing() {
		// Chave: nome da propriedade + Método que deve ser chamado para ela
		Map<String, Method> properties = new HashMap<String, Method>();

		findProperties(properties);

		// Nada a fazer
		if (properties.isEmpty()) {
			return;
		}

		parseCommandLine(properties);

	}

	private void parseCommandLine(Map<String, Method> properties) {
		for (int i = 0; i < commandLine.length; i++) {
			String switchCandidate = commandLine[i];
			String valueCandidate;

			Method setter;

			if ((setter = getIndexedArgument(properties, i)) != null) {
				valueCandidate = commandLine[i];

			} else if ((setter = properties.get(switchCandidate)) != null) {

				// Se for boolean, só precisa do nome do argumento
				if (isBooleanSwitch(setter)) {
					valueCandidate = "true";
				} else {
					// Se não for boolean...

					// valueCandidate deve ser o próximo item na linha de
					// comando
					valueCandidate = commandLine[i + 1];

					// O argumento já foi consumido, pular ele
					i++;
				}

			} else {
				continue;
			}

			callSetterFor(setter, valueCandidate);
		}
	}

	private Method getIndexedArgument(Map<String, Method> properties, int argIndex) {
		for (Method method : properties.values()) {
			CommandLineSwitch annotation = method.getAnnotation(CommandLineSwitch.class);

			if (annotation.index() > 0) {
				return method;
			}
		}

		return null;
	}

	private boolean isBooleanSwitch(Method setter) {
		return setter.getParameterTypes()[0].getName().equals("boolean");
	}

	private void callSetterFor(Method propertySetterMethod, String value) {
		Object o = doParser(propertySetterMethod, value);

		try {
			propertySetterMethod.invoke(switchesObject, o);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object doParser(Method setter, String value) {
		CommandLineSwitch switchSetup = setter.getAnnotation(CommandLineSwitch.class);

		Class<?> setterParameter = setter.getParameterTypes()[0];

		if (switchSetup.parser().isEmpty()) {
			// Parser não informado na anotação
			if (setterParameter.equals(String.class)) {
				// Se for string, copia o valor da linha de comando para o
				// setter
				return value;
			} else if (isBooleanSwitch(setter)) {
				// Switch tipo boolean, apenas mudar para true se o parâmetro
				// for encontrado
				return new Boolean(value);

			} else {
				throw new RuntimeException("Switches do tipo \"" + setterParameter + "\" devem possuir um parser obrigatoriamente!");
			}
		}

		String parserName = switchSetup.parser();

		String parserId = parserName.substring(0, parserName.indexOf('.'));

		Object parser = parsers.get(parserId);

		Method parserMethod = getParserMethod(parserName, parser);

		try {
			return parserMethod.invoke(parser, value);
		} catch (Exception e) {
			assert false : e;
			throw new RuntimeException(e);
		}

	}

	private Method getParserMethod(String parserName, Object parser) {
		int dotIndex = parserName.indexOf('.');

		String parserMethod = parserName.substring(dotIndex + 1, parserName.length());

		Method method = null;
		try {
			method = parser.getClass().getMethod(parserMethod, String.class);
		} catch (Exception e) {
			// Nunca deveria acontecer, isso já foi verificado anteriormente
			assert false : e;
			throw new RuntimeException(e);
		}

		return method;
	}

	private void findProperties(Map<String, Method> properties) {

		for (Method method : switchesObject.getClass().getMethods()) {
			if (isPropertySetterMethod(method)) {
				String propertyName = getPropertyNameFor(method);

				properties.put(propertyName, method);

				setDefaultValue(method);
			}
		}
	}

	private void setDefaultValue(Method setter) {
		CommandLineSwitch switchSetup = setter.getAnnotation(CommandLineSwitch.class);

		String defaultValue;

		// Switch boolean
		if (isBooleanSwitch(setter)) {
			// Para propriedades boolean o valor da propriedade default é false,
			// independente da anotação estar configurada ou não para um valor
			// default
			defaultValue = "false";
		} else {
			defaultValue = switchSetup.defaultValue();
		}

		if (!defaultValue.isEmpty()) {
			callSetterFor(setter, defaultValue);
		}
	}

	private String getPropertyNameFor(Method method) {
		// Procura o nome da propriedade pela anotação
		CommandLineSwitch switchSetup = method.getAnnotation(CommandLineSwitch.class);
		if (!switchSetup.param().isEmpty()) {
			return switchSetup.param();
		} else {
			return method.getName().substring(3);
		}
	}

	private boolean isPropertySetterMethod(Method method) {
		boolean isAnnotated = method.getAnnotation(CommandLineSwitch.class) != null;
		boolean isOneParameterMethod = method.getParameterTypes().length == 1;
		boolean methodNameBeginsWithSet = method.getName().startsWith("set");
		boolean isVoidMethod = method.getReturnType().getName().equals("void");
		boolean isPublicMethod = Modifier.isPublic(method.getModifiers());
		boolean isStatic = Modifier.isStatic(method.getModifiers());

		return isAnnotated && isOneParameterMethod && methodNameBeginsWithSet && isVoidMethod && isPublicMethod && (!isStatic);
	}

	public void addParser(String parserId, Object parser) {
		parsers.put(parserId, parser);
	}

}
