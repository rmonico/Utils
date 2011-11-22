package br.zero.textgrid;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TextGridColumnAlignmentTests {
	
	@Test
	public void doLeftAlignmentTest() {
		StringBuilder alignedValue = TextGridColumnAlignment.LEFT.getAlignedString(10, new StringBuilder("abcdef"));
		
		assertEquals("Left alignment", "abcdef    ", alignedValue.toString());
	}
	
	@Test
	public void doLeftAlignmentTest2() {
		StringBuilder alignedValue = TextGridColumnAlignment.LEFT.getAlignedString(6, new StringBuilder("abcdef"));
		
		assertEquals("Left alignment", "abcdef", alignedValue.toString());
	}
	
	@Test
	public void doLeftAlignmentTest3() {
		StringBuilder alignedValue = TextGridColumnAlignment.LEFT.getAlignedString(5, new StringBuilder("abcdef"));
		
		assertEquals("Left alignment", "ab...", alignedValue.toString());
	}
	
	@Test
	public void doLeftAlignmentTest4() {
		StringBuilder alignedValue = TextGridColumnAlignment.LEFT.getAlignedString(3, new StringBuilder("abcdef"));
		
		assertEquals("Left alignment", "...", alignedValue.toString());
	}
	
	@Test
	public void doRightAlignmentTest() {
		StringBuilder alignedValue = TextGridColumnAlignment.RIGHT.getAlignedString(10, new StringBuilder("abcdef"));
		
		assertEquals("Right alignment", "    abcdef", alignedValue.toString());
	}
	
	@Test
	public void doRightAlignmentTest2() {
		StringBuilder alignedValue = TextGridColumnAlignment.RIGHT.getAlignedString(6, new StringBuilder("abcdef"));
		
		assertEquals("Right alignment", "abcdef", alignedValue.toString());
	}
	
	@Test
	public void doRightAlignmentTest3() {
		StringBuilder alignedValue = TextGridColumnAlignment.RIGHT.getAlignedString(5, new StringBuilder("abcdef"));
		
		assertEquals("Right alignment", "...ef", alignedValue.toString());
	}
	
	@Test
	public void doRightAlignmentTest4() {
		StringBuilder alignedValue = TextGridColumnAlignment.RIGHT.getAlignedString(3, new StringBuilder("abcdef"));
		
		assertEquals("Right alignment", "...", alignedValue.toString());
	}

	@Test
	public void doCenterAlignmentTest() {
		StringBuilder alignedValue = TextGridColumnAlignment.CENTER.getAlignedString(11, new StringBuilder("abcdef"));
		
		// O espaço extra deve ficar a direita
		assertEquals("Center alignment", "  abcdef   ", alignedValue.toString());
	}
	
	@Test
	public void doCenterAlignmentTest2() {
		StringBuilder alignedValue = TextGridColumnAlignment.CENTER.getAlignedString(6, new StringBuilder("abcdef"));
		
		assertEquals("Center alignment", "abcdef", alignedValue.toString());
	}
	
	@Test
	public void doCenterAlignmentTest3() {
		StringBuilder alignedValue = TextGridColumnAlignment.CENTER.getAlignedString(5, new StringBuilder("abcdef"));
		
		assertEquals("Center alignment", "ab...", alignedValue.toString());
	}
	
	@Test
	public void doCenterAlignmentTest4() {
		StringBuilder alignedValue = TextGridColumnAlignment.CENTER.getAlignedString(3, new StringBuilder("abcdef"));
		
		assertEquals("Center alignment", "...", alignedValue.toString());
	}
}
