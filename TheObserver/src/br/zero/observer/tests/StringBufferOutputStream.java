package br.zero.observer.tests;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StringBufferOutputStream extends OutputStream {

	List<String> buffer;
	String s;

	public StringBufferOutputStream() {
		buffer = new ArrayList<String>();
		createNewLine();
	}

	private void createNewLine() {
		s = "";

		buffer.add(s);
	}

	@Override
	public void write(int b) throws IOException {
		char c = (char) b;

		if (c == '\n') {
			createNewLine();
		} else {
			s += c;
			
			buffer.set(buffer.size()-1, s);
		}
	}

	public List<String> getBuffer() {
		return buffer;
	}

}
