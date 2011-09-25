package br.zero.commandlineparser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandLineSwitch {

	/**
	 * Substitui o nome do parâmetro na linha de comando. Caso não seja
	 * informado, é utilizado o nome do setter sem o prefixo "set", com a
	 * primeira letra após este prefixo em minúscula.
	 * 
	 * @return
	 */
	String[] param() default {};

	/**
	 * Valor que substitui o da linha de comando, caso o usuário final não
	 * informe este. Este valor é repassado ao parser, caso exista.
	 * 
	 * @return
	 */
	String defaultValue() default "";

	/**
	 * Id do objeto de parser (definido em CommandLineParser.addParser) seguido
	 * do nome do método na classe de parsing, separado por ponto (.).
	 * 
	 * Exemplo:
	 * 
	 * IntegerParser.parse
	 * 
	 * Neste caso, o método addParser deve ser chamado da seguinte forma:
	 * 
	 * addParser("IntegerParser", new Parser())
	 * 
	 * onde a classe Parser() deverá possuir um método
	 * 
	 * public Integer parse(String value)
	 * 
	 * Para este exemplo, o valor de retorno, o nome do método e o tipo do
	 * parâmetro devem ser, necessáriamente, Integer, parse e String,
	 * respectivamente.
	 * 
	 * Para switches do tipo String não é obrigatório informar um parser. Neste
	 * caso, o valor da linha de comando é passado diretamente ao método setter.
	 * Para switches de outras classes, uma exceção é lançada caso o parser não
	 * seja informado.
	 * 
	 * @return
	 */
	String parser() default "";

	/**
	 * Indica o local onde o parâmetro deverá aparecer na linha de comando. Caso
	 * a posição do parâmetro seja indicado através desse atributo, a opção não
	 * deverá aparecer na linha de comando. Por exemplo, a linha de comando:
	 * 
	 * "Arg1 opt1"
	 * 
	 * Pelo uso desse atributo, poderia ser:
	 * 
	 * "opt1"
	 * 
	 * Nesse caso "opt1" seria passado diretamente ao parser responsável por
	 * esse parâmetro.
	 * 
	 * @return
	 */
	int index() default -1;

}
