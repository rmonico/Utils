package br.zero.commandlineparser;

import java.util.Map;

/**
 * Deve devolver um parser e o objeto de valores com as informações sobre onde
 * deverá ser efetuado o processamento do parsing.
 * 
 * @author Rafael Monico
 * 
 */
public interface ComplexParserParameter {

	CommandLineParser getParser();

	Object getValuesObject();
	
	/**
	 * TODO Escrever esse Javadoc, é importante. 
	 * 
	 * @return
	 */
	Map<String, Class<?>> getSubObjectClasses();

}
