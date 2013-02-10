package br.zero.observer.tests;

import static org.junit.Assert.assertEquals;

import java.io.PrintStream;
import java.util.List;

import org.junit.Test;

public class StringBufferOutputStreamTest {

	@Test
	public void should_return_a_empty_buffer() {
		StringBufferOutputStream stream = new StringBufferOutputStream();
		
		List<String> buffer = stream.getBuffer();
		assertEquals(1, buffer.size());
		
		String line1 = buffer.get(0);
		assertEquals("", line1.toString());
	}
	
	@Test
	public void should_return_hello_world_at_first_line() {
		StringBufferOutputStream stream = new StringBufferOutputStream();
		PrintStream printStream = new PrintStream(stream);
		
		printStream.println("Hello World!");
		
		List<String> buffer = stream.getBuffer();
		
		assertEquals(2, buffer.size());
		
		String line1 = buffer.get(0);
		assertEquals("Hello World!", line1.toString());

		String line2 = buffer.get(1);
		assertEquals("", line2.toString());
	}

	@Test
	public void should_return_a_three_line_buffer() {
		StringBufferOutputStream stream = new StringBufferOutputStream();
		PrintStream printStream = new PrintStream(stream);
		
		printStream.println("Line 1");
		printStream.println("Line 2");
		printStream.println("Line 3");
		
		List<String> buffer = stream.getBuffer();
		
		assertEquals(4, buffer.size());
		
		String line1 = buffer.get(0);
		assertEquals("Line 1", line1.toString());

		String line2 = buffer.get(1);
		assertEquals("Line 2", line2.toString());

		String line3 = buffer.get(2);
		assertEquals("Line 3", line3.toString());
		
		String line4 = buffer.get(3);
		assertEquals("", line4.toString());
	}
}
