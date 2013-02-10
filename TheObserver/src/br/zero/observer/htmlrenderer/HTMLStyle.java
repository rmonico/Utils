package br.zero.observer.htmlrenderer;

import java.io.PrintStream;

import br.zero.observer.Document;
import br.zero.observer.Link;
import br.zero.observer.ObserverException;
import br.zero.observer.Table;

public interface HTMLStyle {

	void setDocument(Document document);

	Document getDocument();

	void setOutput(PrintStream output);

	PrintStream getOutput();
	
	void setRenderer(HTMLRenderer renderer);
	
	HTMLRenderer getRenderer();

	String getWindowTitle();

	void makeDocumentTitle();

	<T> void makeTable(int identLevel, Table<T> table, PrintStream output) throws ObserverException;

	void makeLink(int identLevel, Link link, PrintStream output) throws ObserverException;

}
