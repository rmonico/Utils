package br.zero.commandlineparser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommandLine {

	String value();

	Class<?> subCommandLineClass() default Object.class;
	// TODO Fazer os testes disto depois do almoço

	String propertyName() default "";

}
