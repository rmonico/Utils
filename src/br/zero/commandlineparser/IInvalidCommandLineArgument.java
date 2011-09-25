package br.zero.commandlineparser;

/**
 * Representação de um erro em uma linha de comando.
 * 
 * @author Rafael Monico
 * 
 */

public interface IInvalidCommandLineArgument {
	String getMessage();
	
	// Obs: override method toString too
}
