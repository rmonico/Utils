package br.zero.commandlineparser;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.zero.switchesparser.IInvalidCommandLineArgument;
import br.zero.switchesparser.ParserException;
import br.zero.switchesparser.SwitchesParser;

// TODO Criar suporte a i18n. Do jeito que está teria que fazer uma classe para cada localidade que tiver que ser suportada.
// TODO Ver o que fazer quando houver um defaultValue e um index simultaneamente no mesmo switch
// TODO Melhorar a implementação. Fazer refatorações para extrair mais métodos.
public class CommandLineParser implements SwitchesParser {

	private String[] commandLine;
	private Object switchesObject;
	private Map<String, Object> parsers = new HashMap<String, Object>();
	private Map<String, Method> properties;
	private List<IInvalidCommandLineArgument> errors;
	private CommandLineSwitch switchSetup;

	@Override
	public void setValuesObject(Object o) {
		if (!(o instanceof String[])) {
			throw new RuntimeException("CommandLineParser.setValuesObject: Parâmetro deve ser uma linha de comando (String[]).");
		}
		
		this.commandLine = (String[]) o;
	}

	@Override
	public String[] getValuesObject() {
		return commandLine;
	}
	
	public void setSwitchesObject(Object o) {
		switchesObject = o;
	}

	public void parse() throws ParserException {
		properties = new HashMap<String, Method>();

		errors = new ArrayList<IInvalidCommandLineArgument>();

		findProperties();

		// Nada a fazer
		if (commandLine == null) {
			return;
		}

		// Nada a fazer
		if (properties.isEmpty()) {
			return;
		}

		doParsing();
	}

	private void doParsing() throws ParserException {
		for (int i = 0; i < commandLine.length; i++) {
			String switchCandidate = commandLine[i];
			String[] valueCandidate;

			Method setter;

			switchSetup = null;

			if ((setter = getIndexedArgument(properties, i)) != null) {
				switchSetup = setter.getAnnotation(CommandLineSwitch.class);

				if (switchSetup.complexParser()) {
					valueCandidate = Arrays.copyOfRange(commandLine, i, commandLine.length);
				} else {
					valueCandidate = new String[] { commandLine[i] };
				}
			} else if ((setter = properties.get(switchCandidate)) != null) {
				switchSetup = setter.getAnnotation(CommandLineSwitch.class);

				// Se for boolean, não preciso do valor
				if (isBooleanSwitch(setter)) {
					if (switchSetup.complexParser()) {
						valueCandidate = Arrays.copyOfRange(commandLine, i, commandLine.length);
					} else {
						valueCandidate = new String[] { "true" };
					}
				} else {
					// Se não for boolean...

					if (switchSetup.complexParser()) {
						valueCandidate = Arrays.copyOfRange(commandLine, i + 1, commandLine.length);
					} else {
						// valueCandidate deve ser o próximo item na linha de
						// comando
						valueCandidate = new String[] { commandLine[i + 1] };

						// O argumento já foi consumido, pular ele
						i++;
					}

				}

			} else {
				errors.add(new ExcessiveArgument(switchCandidate));
				continue;
			}

			setValueFor(setter, valueCandidate);

			// switch's complexos consomem toda a linha de comando depois deles
			if (switchSetup.complexParser()) {
				return;
			}
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

	private void setValueFor(Method setter, String[] value) throws ParserException {
		Object parsedObject = doParser(setter, value);

		try {
			if (switchSetup.complexParser()) {
				callSetterForComplex(switchesObject, setter, (ComplexParserReturn) parsedObject);
			} else {
				setter.invoke(switchesObject, parsedObject);
			}
		} catch (IllegalArgumentException e) {
			// Condição verificada anteriormente, não deveria acontecer
			assert false : e;
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// Condição verificada anteriormente, não deveria acontecer
			assert false : e;
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new ParserException("Erro chamando método setter...\n\n" + e);
		}
	}

	private void callSetterForComplex(Object targetBean, Method setter, ComplexParserReturn parsedObject) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException {
		setter.invoke(switchesObject, parsedObject.getComplexSwitchValue());
		
		SubCommandLine subCommandLineSetup = getSubCommandLineFor(parsedObject);
		
		if (subCommandLineSetup == null) {
			throw new RuntimeException("Nenhuma sub linha de comando encontrada..."); 
		}
		
		Method subObjectMethod;
		
		try {
			subObjectMethod = targetBean.getClass().getMethod(subCommandLineSetup.propertyName(), parsedObject.getSubObjectValue().getClass());
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		
		subObjectMethod.invoke(targetBean, parsedObject.getSubObjectValue());
	}

	private SubCommandLine getSubCommandLineFor(ComplexParserReturn parsedObject) {
		
		String complexSwitchValueAsString = parsedObject.getComplexSwitchValue().toString();
		
		for (SubCommandLine s : switchSetup.subCommandLineProperties()) {
			if (complexSwitchValueAsString.equals(s.value())) {
				return s;
			}
		}
		
		// O toString não bateu com nenhum objeto, não sub command line
		return null;
	}

	private Object doParser(Method setter, String[] valueCandidate) throws ParserException {
		Class<?> setterParameter = setter.getParameterTypes()[0];

		Object parsedObject = makeEmbeededParser(setter, valueCandidate, setterParameter);

		if (parsedObject != null) {
			return parsedObject;
		}

		String parserName = switchSetup.parser();

		String parserId = parserName.substring(0, parserName.indexOf('.'));

		Object parser = parsers.get(parserId);

		Method parserMethod = getParserMethod(parserName, parser);

		if (!isValidParserMethodFor(parserMethod, setterParameter)) {
			throw new ParserException("O método \"" + parserMethod + "\" não é um método de parsing válido para propriedades do tipo \"" + setterParameter + "\".");
		}

		try {
			if (switchSetup.complexParser()) {
				parsedObject = callComplexParser(parser, parserMethod, valueCandidate);
			} else {
				parsedObject = parserMethod.invoke(parser, valueCandidate[0]);
			}
		} catch (IllegalArgumentException e) {
			// Condição verificada anteriormente, não deveria acontecer
			assert false : e;
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// Condição verificada anteriormente, não deveria acontecer
			assert false : e;
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new ParserException("Erro chamando parser...\n" + e);
		}

		CommandLineArgumentParserMethod parserMethodSetup = parserMethod.getAnnotation(CommandLineArgumentParserMethod.class);

		// Verifica se houve algum problema durante o parsing
		String parserMessageMethodName;
		if (!(parserMessageMethodName = parserMethodSetup.messageMethod()).isEmpty()) {
			Method parserMessageMethod;
			try {
				parserMessageMethod = parser.getClass().getMethod(parserMessageMethodName);

			} catch (Exception e) {
				throwInvalidParserMessageMethodException(parserMessageMethodName, parser);

				// Código inatingível, ver chamada acima
				return null;
			}

			if (!(parserMessageMethod.getReturnType().equals(String.class) && (Modifier.isPublic(parserMessageMethod.getModifiers())))) {
				throwInvalidParserMessageMethodException(parserMessageMethodName, parser);

				// Código inatingível, ver chamada acima
				return null;
			}

			String message;

			try {
				message = (String) parserMessageMethod.invoke(parser);
			} catch (IllegalArgumentException e) {
				// Condição verificada anteriormente, não deveria acontecer
				assert false : e;
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				// Condição verificada anteriormente, não deveria acontecer
				assert false : e;
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new ParserException("Erro devolvendo a mensagem de erro...");
			}

			if (message != null) {
				errors.add(new CommandLineOptionParsingError(message));
			}

		}

		return parsedObject;
	}

	private Object callComplexParser(Object parser, Method parserMethod, String[] valueCandidate) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		DefaultComplexParserParameter complexParserParameter = new DefaultComplexParserParameter();

		complexParserParameter.setParser(this);
		
		complexParserParameter.setValuesObject(valueCandidate);
		
		for (SubCommandLine subCommandLine : switchSetup.subCommandLineProperties()) {
			complexParserParameter.getSubObjectClasses().put(subCommandLine.value(), subCommandLine.subCommandLineClass());
		}

		Object parsedObject = parserMethod.invoke(parser, complexParserParameter);

		return parsedObject;
	}

	private Object makeEmbeededParser(Method setter, String[] valueCandidate, Class<?> setterParameter) {
		if (switchSetup.parser().isEmpty()) {
			// SwitchesParser não informado na anotação
			if (setterParameter.equals(String.class)) {
				// Se for string, copia o valor da linha de comando para o
				// setter
				return valueCandidate[0];
			} else if (isBooleanSwitch(setter)) {
				// Switch tipo boolean, apenas mudar para true se o parâmetro
				// for encontrado
				return new Boolean(valueCandidate[0]);

			} else {
				throw new RuntimeException("Switches do tipo \"" + setterParameter + "\" devem possuir um parser obrigatoriamente!");
			}
		}
		return null;
	}

	private void throwInvalidParserMessageMethodException(String methodName, Object parser) throws ParserException {
		throw new ParserException("O nome de método \"" + methodName + "\" é inválido para a classe \"" + parser.getClass() + "\".");
	}

	private boolean isValidParserMethodFor(Method parserMethod, Class<?> setterParameter) {

		boolean hasRightAnnotation = parserMethod.getAnnotation(CommandLineArgumentParserMethod.class) != null;
		boolean isMethodPublic = Modifier.isPublic(parserMethod.getModifiers());
		boolean isOneParameterMethod = parserMethod.getParameterTypes().length == 1;

		Class<?> parameterClass = switchSetup.complexParser() ? ComplexParserParameter.class : String.class;

		boolean isRightParameter = isOneParameterMethod ? parserMethod.getParameterTypes()[0].equals(parameterClass) : false;

		boolean isReturnTypeOk = switchSetup.complexParser() ? true : setterParameter.isAssignableFrom(parserMethod.getReturnType());
		boolean isEnumProperty = ((setterParameter.isEnum()) && (parserMethod.getReturnType().equals(Enum.class)));

		return hasRightAnnotation && isMethodPublic && isOneParameterMethod && isRightParameter && (isReturnTypeOk || isEnumProperty);
	}

	private Method getParserMethod(String parserName, Object parser) {
		int dotIndex = parserName.indexOf('.');

		String parserMethod = parserName.substring(dotIndex + 1, parserName.length());

		Method method = null;
		try {
			if (switchSetup.complexParser()) {
				method = parser.getClass().getMethod(parserMethod, ComplexParserParameter.class);
			} else {
				method = parser.getClass().getMethod(parserMethod, String.class);
			}
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

	private void findProperties() throws ParserException {

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

	private void setDefaultValue(Method setter) throws ParserException {
		String[] defaultValue;

		switchSetup = setter.getAnnotation(CommandLineSwitch.class);

		// Switch boolean
		if (isBooleanSwitch(setter)) {
			// Para propriedades boolean o valor da propriedade default é false,
			// independente da anotação estar configurada ou não para um valor
			// default
			defaultValue = new String[] { "false" };
		} else {
			defaultValue = switchSetup.defaultValue();
		}

		if (defaultValue.length > 0) {
			setValueFor(setter, defaultValue);
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

	public List<IInvalidCommandLineArgument> getErrors() {
		return errors;
	}

	public boolean hasErrors() {
		return !getErrors().isEmpty();
	}

	public void printErrors(PrintStream printStream) {
		for (IInvalidCommandLineArgument error : getErrors()) {
			printStream.println(error);
		}
	}

	@Override
	public Map<String, Object> getPropertyParsers() {
		return parsers;
	}

	@Override
	public SwitchesParser createSubSwitchesParser() {
		CommandLineParser subParser = new CommandLineParser();
		
		for (String id : getPropertyParsers().keySet()) {
			subParser.getPropertyParsers().put(id, getPropertyParsers().get(id));
		}
		
		return subParser;
	}

}
