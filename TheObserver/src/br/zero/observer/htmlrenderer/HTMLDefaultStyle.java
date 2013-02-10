package br.zero.observer.htmlrenderer;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.zero.observer.Document;
import br.zero.observer.Element;
import br.zero.observer.Formatter;
import br.zero.observer.Link;
import br.zero.observer.NullFormatter;
import br.zero.observer.ObserverException;
import br.zero.observer.Table;
import br.zero.observer.TableColumn;

public class HTMLDefaultStyle extends HTMLAbstractStyle {

	private Document document;
	private PrintStream output;
	private HTMLRenderer renderer;

	@Override
	public void setDocument(Document document) {
		super.setDocument(document);
		this.document = document;
	}

	@Override
	public void setOutput(PrintStream output) {
		super.setOutput(output);
		this.output = output;
	}
	
	@Override
	public void setRenderer(HTMLRenderer renderer) {
		super.setRenderer(renderer);
		this.renderer = renderer;
	}

	@Override
	public String getWindowTitle() {
		return document.getTitle();
	}

	@Override
	public void makeDocumentTitle() {
		String title = document.getTitle();

		if (title != null) {
			output.println("<h1>" + title + "</h1><br/>");
			output.println("<br/>");
		}
	}

	@Override
	public <T> void makeTable(int identLevel, Table<T> e, PrintStream output) throws ObserverException {
		String identation = getIdentationString(identLevel);

		output.println(identation + "<table>");

		makeHeaderRow(identLevel + 1, e);

		makeBody(identLevel + 1, e);

		output.println(identation + "</table>");
	}

	private <T> void makeHeaderRow(int identLevel, Table<T> e) {
		Class<T> lineClass = e.getLineClass();

		String identation = getIdentationString(identLevel);

		output.println(identation + "<!-- header -->");
		output.println(identation + "<tr>");

		makeHeaderColumns(identLevel + 1, lineClass);

		output.println(identation + "</tr>");
	}

	private <T> void makeHeaderColumns(int identLevel, Class<T> lineClass) {
		String identation = getIdentationString(identLevel);

		List<TableColumn> columns = new ArrayList<TableColumn>();

		for (Method m : lineClass.getMethods()) {
			TableColumn column = m.getAnnotation(TableColumn.class);

			if (column == null) {
				continue;
			}

			columns.add(column);
		}

		Comparator<TableColumn> comparator = new Comparator<TableColumn>() {

			@Override
			public int compare(TableColumn o1, TableColumn o2) {
				return o1.index() - o2.index();
			}
		};

		Collections.sort(columns, comparator);

		for (TableColumn column : columns) {
			output.println(identation + "<td>" + column.title() + "</td>");
		}
	}

	private String getIdentationString(int identLevel) {
		String identString = "";

		for (int i = 0; i < identLevel; i++) {
			identString += "  ";
		}

		return identString;
	}

	private <T> void makeBody(int identLevel, Table<T> e) throws ObserverException {
		List<T> rows = e.getRows();

		if (rows.isEmpty()) {
			return;
		}

		List<Method> methodList = getMethodList(rows);

		String identation = getIdentationString(identLevel);

		output.println("");
		output.println(identation + "<!-- body -->");

		for (T row : rows) {
			output.println(identation + "<tr>");

			makeRow(identLevel + 1, row, methodList);

			output.println(identation + "</tr>");
			output.println("");
		}
	}

	private <T> List<Method> getMethodList(List<T> rows) {
		List<Method> methodList = new ArrayList<Method>();

		for (Method m : rows.get(0).getClass().getMethods()) {
			TableColumn tableColumn = m.getAnnotation(TableColumn.class);
			if (tableColumn != null) {
				methodList.add(m);
			}
		}

		Comparator<Method> comparator = new Comparator<Method>() {

			@Override
			public int compare(Method o1, Method o2) {
				TableColumn ann1 = o1.getAnnotation(TableColumn.class);
				TableColumn ann2 = o2.getAnnotation(TableColumn.class);

				return ann1.index() - ann2.index();
			}

		};

		Collections.sort(methodList, comparator);

		return methodList;
	}

	private <T> void makeRow(int identLevel, T row, List<Method> methodList) throws ObserverException {
		for (Method m : methodList) {
			makeCell(identLevel, row, m);
		}
	}

	private <T> void makeCell(int identLevel, T row, Method m) throws ObserverException {
		Object methodValue = getMethodValue(row, m);

		String identation = getIdentationString(identLevel);

		output.println(identation + "<td>");
		
		if (methodValue instanceof Element) {
			renderer.renderElement(identLevel + 1, (Element) methodValue);
		} else {
			String formatterMethodValue;
			
			formatterMethodValue = getFormatterMethodValue(m, methodValue);
			
			output.println(identation + "  " + formatterMethodValue);
		}
		
		output.println(identation + "</td>");
	}

	private String getFormatterMethodValue(Method m, Object methodValue) throws ObserverException {
		String formatterMethodValue;
		TableColumn annotation = m.getAnnotation(TableColumn.class);
		
		assert annotation != null;
		
		if (methodValue != null) {
			Class<? extends Formatter> formatterClass = annotation.formatterClass();
			
			Formatter formatter;
			try {
				formatter = formatterClass.newInstance();
			} catch (Exception e) {
				throw new ObserverException(e);
			}
			
			formatterMethodValue = formatter.parse(methodValue);
			
		} else {
			formatterMethodValue = NullFormatter.instance.parse(null);
		}
		return formatterMethodValue;
	}

	private <T> Object getMethodValue(T row, Method m) throws ObserverException {
		Object methodValue;

		try {
			methodValue = m.invoke(row);
		} catch (Exception e) {
			throw new ObserverException(e);
		}
		return methodValue;
	}

	@Override
	public void makeLink(int identLevel, Link link, PrintStream output) throws ObserverException {
		String identation = getIdentationString(identLevel);

		String parameters = getParametersString(link.getParams());
		
		output.println(identation + "<a href=\"" + link.getHandler() + parameters + "\">" + link.getLabel() + "</a>");
	}

	private String getParametersString(Map<String, Object> params) {
		StringBuilder paramsString = new StringBuilder("");

		if (params == null || params.isEmpty()) {
			return "";
		}
		
		paramsString = new StringBuilder("?");
		
		for (Entry<String, Object> entry : params.entrySet()) {
			paramsString.append(entry.getKey());
			paramsString.append("=");
			paramsString.append(entry.getValue().toString());
			paramsString.append("&");
		}
		
		return paramsString.toString();
	}
}
