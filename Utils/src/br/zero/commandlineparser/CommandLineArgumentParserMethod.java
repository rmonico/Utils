package br.zero.commandlineparser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
// TODO Métodos marcados com esta anotação devem poder lançar ParserException opcionalmente
public @interface CommandLineArgumentParserMethod {

	String messageMethod() default "";

}
