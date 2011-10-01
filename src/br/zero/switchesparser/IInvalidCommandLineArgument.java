package br.zero.switchesparser;

/**
 * Representação de um erro em uma linha de comando.
 * 
 * @author Rafael Monico
 * 
 */

// TODO Mudar para um nome mais genérico
public interface IInvalidCommandLineArgument {
	String getMessage();
	
	// Obs: override method toString too
}
