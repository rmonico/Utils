package br.zero.textgrid;

import java.util.ArrayList;
import java.util.List;

public class TextGridData {
	private String title = "";
	private List<TextGridColumn> columns = new ArrayList<TextGridColumn>();
	private char headerSeparatorChar = '-';
	private String defaultColumnSeparator = " | ";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<TextGridColumn> getColumns() {
		return columns;
	}

	public char getHeaderSeparatorChar() {
		return headerSeparatorChar;
	}
	
	public void setHeaderSeparatorChar(char headerSeparatorChar) {
		this.headerSeparatorChar = headerSeparatorChar;
	}

	public String getDefaultColumnSeparator() {
		return defaultColumnSeparator;
	}

	public void setDefaultColumnSeparator(String defaultColumnSeparator) {
		this.defaultColumnSeparator = defaultColumnSeparator;
	}

	public void setColumns(List<TextGridColumn> columns) {
		this.columns = columns;
	}

	public TextGridColumn createColumn(String title, TextGridFormatter formatter, String getterMethod, String columnSeparator) {
		TextGridColumn newColumn = new TextGridColumn();
		
		newColumn.setTitle(title);
		newColumn.setSeparator(columnSeparator);
		newColumn.setFormatter(formatter);
		newColumn.setLineGetterMethod(getterMethod);
		
		columns.add(newColumn);
		
		return newColumn;
	}

	public TextGridColumn createColumn(String title, TextGridFormatter formatter, String getterMethod) {
		return createColumn(title, formatter, getterMethod, defaultColumnSeparator);
	}
}
