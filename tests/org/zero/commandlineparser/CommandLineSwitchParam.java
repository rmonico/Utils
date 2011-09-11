package org.zero.commandlineparser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação destinada ao uso com constantes de enum.
 * 
 * @author Rafael Monico
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CommandLineSwitchParam {

	/**
	 * Indica o nome do parâmetro que o argumento deve ter quando for feito o
	 * parser da linha de comando.
	 * 
	 * @return
	 */
	String name();

}
