package org.zero.commandlineparser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLineParser {

	private String[] commandLine;
	private Object switchesObject;
	private Map<String, Object> parsers = new HashMap<String, Object>();
	private Map<String, Method> properties;
	private List<ICommandLineParsingError> errors;

	public void setCommandLine(String[] commandLine) {
		this.commandLine = commandLine;
	}

	public void setSwitchesObject(Object o) {
		switchesObject = o;
	}

	public void parse() throws CommandLineParserException {
		properties = new HashMap<String, Method>();

		errors = new ArrayList<ICommandLineParsingError>();

		findProperties();

		// Nada a fazer
		if (properties.isEmpty()) {
			return;
		}

		doParsing();
	}

	private void doParsing() throws CommandLineParserException {
		for (int i = 0; i < commandLine.length; i++) {
			String switchCandidate = commandLine[i];
			String valueCandidate;

			Method setter;

			if ((setter = getIndexedArgument(properties, i)) != null) {
				valueCandidate = commandLine[i];

			} else if ((setter = properties.get(switchCandidate)) != null) {

				// Se for boolean, não preciso do valor
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
				errors.add(new ExcessiveCommandLineArgument(switchCandidate));
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

	private void callSetterFor(Method propertySetterMethod, String value) throws CommandLineParserException {
		Object o = doParser(propertySetterMethod, value);

		try {
			propertySetterMethod.invoke(switchesObject, o);
		} catch (IllegalArgumentException e) {
			// Condição verificada anteriormente, não deveria acontecer
			assert false : e;
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// Condição verificada anteriormente, não deveria acontecer
			assert false : e;
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CommandLineParserException("Erro chamando método setter...\n\n" + e);
		}
	}

	private Object doParser(Method setter, String value) throws CommandLineParserException {
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

		if (!isValidParserMethodFor(parserMethod, setterParameter)) {
			throw new CommandLineParserException("O método \"" + parserMethod + "\" é um método de parsing inválido.");
		}

		Object parsedObject;

		try {
			parsedObject = parserMethod.invoke(parser, value);
		} catch (IllegalArgumentException e) {
			// Condição verificada anteriormente, não deveria acontecer
			assert false : e;
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// Condição verificada anteriormente, não deveria acontecer
			assert false : e;
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new CommandLineParserException("Erro chamando parser...\n\n" + e);
		}

		if (parsedObject == null) {
			return null;
		} else {
			return parsedObject;
		}
	}

	private boolean isValidParserMethodFor(Method parserMethod, Class<?> setterParameter) {

		boolean hasRightAnnotation = parserMethod.getAnnotation(CommandLineArgumentParserMethod.class) != null;
		boolean isAccessible = parserMethod.isAccessible();
		boolean isOneParameterMethod = parserMethod.getParameterTypes().length == 1;
		boolean theParameterIsRightType = isOneParameterMethod ? parserMethod.getParameterTypes()[0].getDeclaringClass().equals(setterParameter) : false;

		return ((hasRightAnnotation) && (isAccessible) && (isOneParameterMethod) && (theParameterIsRightType));
	}

	private Method getParserMethod(String parserName, Object parser) {
		int dotIndex = parserName.indexOf('.');

		String parserMethod = parserName.substring(dotIndex + 1, parserName.length());

		Method method = null;
		try {
			method = parser.getClass().getMethod(parserMethod, String.class);
			// Nenhuma das duas exceções abaixo deveria acontecer, já foi
			// verificado antes
		} catch (SecurityException e) {
			assert false : e;
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			assert false : e;
			throw new RuntimeException(e);
		}

		return method;
	}

	private void findProperties() throws CommandLineParserException {

		for (Method method : switchesObject.getClass().getMethods()) {
			if (isPropertySetterMethod(method)) {
				String[] propertiesNames = getPropertyNameFor(method);

				for (String propertyName : propertiesNames) {
					properties.put(propertyName, method);
				}

				setDefaultValue(method);
			}
		}
	}

	private void setDefaultValue(Method setter) throws CommandLineParserException {
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

	private String[] getPropertyNameFor(Method method) {
		// Procura o nome da propriedade pela anotação
		CommandLineSwitch switchSetup = method.getAnnotation(CommandLineSwitch.class);
		if (switchSetup.param().length > 0) {
			return switchSetup.param();
		} else {
			return new String[] { method.getName().substring(3) };
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

	public List<ICommandLineParsingError> getErrors() {
		return errors;
	}

}
