package br.zero.textgrid;

import java.lang.reflect.Method;

public class TextGridColumn {

	private String title;
	private String separator;
	private TextGridFormatter formatter;
	private String lineGetterMethod;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public TextGridFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(TextGridFormatter formatter) {
		this.formatter = formatter;
	}
	
	public String getLineGetterMethod() {
		return lineGetterMethod;
	}
	
	public void setLineGetterMethod(String lineGetterMethod) {
		this.lineGetterMethod = lineGetterMethod;
	}

	public Object getCellValue(Object line) throws TextGridException {
		Method getter;
		
		Object cellValue;
		
		try {
			getter = line.getClass().getMethod(lineGetterMethod);

			cellValue = getter.invoke(line);
		} catch (Exception e) {
			throw new TextGridException(e);
		}
		
		return cellValue;
	}

}
