package br.zero.observer.htmlrenderer;

import java.io.PrintStream;

import br.zero.observer.Document;
import br.zero.observer.Element;
import br.zero.observer.Link;
import br.zero.observer.ObserverException;
import br.zero.observer.Renderer;
import br.zero.observer.Table;

public class HTMLRenderer implements Renderer {

	private Document document;
	private PrintStream output;
	private HTMLStyle style;

	public HTMLRenderer(HTMLStyle style) {
		this.style = style;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public PrintStream getOutput() {
		return output;
	}

	public void setOutput(PrintStream output) {
		this.output = output;
	}

	@Override
	public void renderize() throws ObserverException {
		document.create();

		style.setDocument(document);
		style.setOutput(output);
		style.setRenderer(this);

		output.println("<html>");
		output.println("<head>");

		String title = document.getTitle();
		if (title != null) {
			output.println("  <title>" + style.getWindowTitle() + "</title>");
		}
		output.println("</head>");
		output.println("<body>");

		style.makeDocumentTitle();

		for (Element e : document.getElements()) {
			renderElement(1, e);
		}

		output.println("</body>");
		output.println("</html>");
	}

	void renderElement(int identLevel, Element e) throws ObserverException {
		switch (e.getType()) {
		case TABLE: {
			style.makeTable(identLevel, (Table<?>) e, output);
			break;
		}
		case LINK: {
			style.makeLink(identLevel, (Link) e, output);
			break;
		}

		default: {
			assert false : "Tipo de elemento não encontrado...";
		}
		}
	}
}
