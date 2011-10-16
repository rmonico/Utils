package br.zero.commandlineparser;

import java.util.Map;

/**
 * Deve devolver todas as informações necessárias para a montagem de um parser
 * novo.
 * 
 * @author Rafael Monico
 * 
 */
public interface ComplexParserParameter {

	String[] getArgs();
	
	Map<String, Object> getParsers();

}
