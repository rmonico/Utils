package br.zero.freelook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

// TODO Mover esta classe para um pacote mais adequado
public class StringBuilderListOutputStream extends OutputStream {

	private List<StringBuilder> list;
	private StringBuilder currentLine;
	
	public StringBuilderListOutputStream(List<StringBuilder> list) {
		this.list = list;
		
		currentLine = new StringBuilder();
		list.add(currentLine);
	}

	@Override
	public void write(int b) throws IOException {
		
		if (b == '\n') {
			currentLine = new StringBuilder();

			list.add(currentLine);
		} else {
			currentLine.append((char) b);
		}
	}

}
