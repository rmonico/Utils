package br.zero.observer;

import java.util.HashMap;
import java.util.Map;

public class Link implements Element {

	private String handler;
	private String label;
	private Map<String, Object> params;
	
	public Link() {
		super();
	}
	
	public Link(String handler, String label) {
		this();
		
		setHandler(handler);
		setLabel(label);
	}

	@Override
	public ElementType getType() {
		return ElementType.LINK;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		if (getLabel() != null) {
			return getLabel();
		} else {
			return super.toString();
		}
	}

	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		return params;
	}
}
