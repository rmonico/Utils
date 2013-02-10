package br.zero.textgrid;

import java.util.ArrayList;
import java.util.List;

public class TextGridData {
	private String title = "";
	private List<TextGridColumn> columns = new ArrayList<TextGridColumn>();
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

	public List<? extends TextGridColumn> getColumns() {
		return columns;
	}

	public boolean isAligned() {
		return aligned;
	}

	public void setAligned(boolean aligned) {
		this.aligned = aligned;
	}
	
	public void registerColumn(TextGridColumn column) {
		columns.add(column);
	}
	
}
