package br.zero.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class JUnitUtils {
	public static void assertBlockEquals(String message,
			String expectedBlock, String actualBlock) {
		
		
		List<String> expected = Arrays.asList(expectedBlock.split("\n"));
		
		List<String> actual = Arrays.asList(actualBlock.split("\n"));
		
		assertListsEquals(message, expected, actual);
	}

	public static <T> void assertListsEquals(String message, List<T> expectedList, List<T> actualList) {
		List<T> smallerList = (expectedList.size() < actualList.size()) ? expectedList : actualList;
		
		for (int i=0; i < smallerList.size() ; i++) {
			assertEquals(message + " (linha " + (i+1) + ")", expectedList.get(i), actualList.get(i));
		}

		if (expectedList.size() != actualList.size()) {
			assertEquals(message + " (list sizes diff)", expectedList.toString(), actualList.toString());
		}
	}
	
}
