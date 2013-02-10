package br.zero.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Container para os demais elementos.
 * 
 * @author Rafael Monico
 * 
 */
public abstract class Document implements Element {

	private String title;
	private List<Element> elements;

	public Document() {
		elements = new ArrayList<Element>();
	}

	/**
	 * Deve criar os elementos do documento.
	 * @throws ObserverException 
	 */
	public abstract void create() throws ObserverException;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void addElement(Element element) {
		elements.add(element);
	}

	public List<Element> getElements() {
		return elements;
	}

	@Override
	public ElementType getType() {
		return ElementType.DOCUMENT;
	}

}
