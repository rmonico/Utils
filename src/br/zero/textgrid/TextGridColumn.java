package br.zero.textgrid;


public interface TextGridColumn {
	
	public String getTitle();
	public void setTitle(String title);

	public String getSeparator();
	public void setSeparator(String separator);

	public Object getCellValue(Object line) throws TextGridException;

}
