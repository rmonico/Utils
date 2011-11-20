package br.zero.textgrid;

public abstract class TextGridFormatter {
	
	public abstract String parse(Object cellValue) throws TextGridException;

}
