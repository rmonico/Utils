package br.zero.textgrid;

import java.util.ArrayList;
import java.util.List;

public class TextGridData {
	private String title = "";
	private List<TextGridFormattedColumn> columns = new ArrayList<TextGridFormattedColumn>();
	private char headerSeparatorChar = '-';
	private String defaultColumnSeparator = " | ";
	private boolean aligned = true;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<TextGridFormattedColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<TextGridFormattedColumn> columns) {
		this.columns = columns;
	}

	public boolean getAligned() {
		return aligned;
	}

	public void setAligned(boolean aligned) {
		this.aligned = aligned;
	}

	public TextGridFormattedColumn createFormattedColumn(String title, TextGridFormatter formatter, String getterMethod, String columnSeparator) {
		TextGridFormattedColumn newColumn = new TextGridFormattedColumn();
		
		newColumn.setTitle(title);
		newColumn.setSeparator(columnSeparator);
		newColumn.setFormatter(formatter);
		newColumn.setLineGetterMethod(getterMethod);
		
		columns.add(newColumn);
		
		return newColumn;
	}

	public TextGridFormattedColumn createFormattedColumn(String title, TextGridFormatter formatter, String getterMethod) {
		return createFormattedColumn(title, formatter, getterMethod, defaultColumnSeparator);
	}
	
}
