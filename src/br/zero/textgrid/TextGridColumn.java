package br.zero.textgrid;


public interface TextGridColumn {
	
	public String getTitle();
	public void setTitle(String title);

	public String getSeparator();
	public void setSeparator(String separator);
	
	public TextGridColumnAlignment getAlignment();
	public void setAlignment(TextGridColumnAlignment alignment);

	public Object getCellObject(Object line) throws TextGridException;
	public StringBuilder parse(Object cellValue) throws TextGridException;

}
