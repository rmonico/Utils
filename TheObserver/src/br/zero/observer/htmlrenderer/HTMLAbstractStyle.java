package br.zero.observer.htmlrenderer;

import java.io.PrintStream;

import br.zero.observer.Document;

public abstract class HTMLAbstractStyle implements HTMLStyle {

	private Document document;
	private PrintStream output;
	private HTMLRenderer renderer;

	@Override
	public Document getDocument() {
		return document;
	}

	@Override
	public void setDocument(Document document) {
		this.document = document;
	}

	@Override
	public PrintStream getOutput() {
		return output;
	}

	@Override
	public void setOutput(PrintStream output) {
		this.output = output;
	}

	@Override
	public void setRenderer(HTMLRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public HTMLRenderer getRenderer() {
		return renderer;
	}

}
