package br.zero.observer;

import java.io.PrintStream;

/**
 * Interface que deve ser implementada por qualquer renderizador. É
 * intencionalmente simples para permitir maior flexibilidade de implementação.
 * 
 * @author Rafael Monico
 * 
 */
public interface Renderer {

	void setDocument(Document document);

	Document getDocument();

	void setOutput(PrintStream output);

	PrintStream getOutput();

	void renderize() throws ObserverException;

}
