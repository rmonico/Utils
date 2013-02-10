package br.zero.textgrid;

public abstract class TextGridFormatter {
	
	public abstract StringBuilder parse(Object cellValue) throws TextGridException;

}
