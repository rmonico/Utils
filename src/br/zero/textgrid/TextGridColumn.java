package br.zero.textgrid;


public interface TextGridColumn {
	
	public String getTitle();
	public void setTitle(String title);

	public String getSeparator();
	public void setSeparator(String separator);

	public Object getCellObject(Object line) throws TextGridException;
	public String parse(Object cellValue) throws TextGridException;

}
