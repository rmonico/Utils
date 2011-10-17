package br.zero.switchesparser;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public interface SwitchesParser {

	/**
	 * valuesObject: Contém as informações para processo de parsing.
	 * 
	 * @return
	 */
	Object getValuesObject();

	/**
	 * 
	 * @param o
	 * @throws ParserException
	 *             Lançado caso o parser rejeite o parâmetro.
	 */
	void setValuesObject(Object o);

	/**
	 * switchesObject: Deve ser o objeto anotado com as informações para o
	 * processo de parsing.
	 * 
	 * @param o
	 */
	void setSwitchesObject(Object o);

	/**
	 * Devolve o mapa com os parsers de propriedades. Cada parser deve ser
	 * unicamente identificado por meio de um ID textual.
	 * 
	 * @return
	 */
	Map<String, Object> getPropertyParsers();

	/**
	 * Realiza o processo de parsing.
	 * 
	 * @throws ParserException
	 */
	void parse() throws ParserException;

	/**
	 * Indica a presença de eventuais erros.
	 * 
	 * @return
	 */
	boolean hasErrors();

	/**
	 * Devolve uma lista com os erros ocorridos durante o parsing. Mesmo que não
	 * existam erros, deve devolver uma lista não nula, porém, vazia.
	 * 
	 * @return
	 */
	List<IInvalidCommandLineArgument> getErrors();

	/**
	 * Dá uma saída dos erros para <code>printStream</code>. Implementação
	 * opcional.
	 * 
	 * @param printStream
	 */
	void printErrors(PrintStream printStream);

	/**
	 * Deve devolver um <code>SwitchesParser</code> para realizar o parser de
	 * uma propriedade complexa. Tal parser não possui nenhum requisito
	 * especial. Normalmente será um parser da mesma classe que o parser
	 * original, com os parâmetros originalmente recebidos.
	 * 
	 * @return
	 */
	SwitchesParser createSubSwitchesParser();
}
