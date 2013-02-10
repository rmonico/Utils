package br.zero.utils;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
	
	public static void assertCalendar(String expected, Calendar actual) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.US);
		
		assertEquals(expected, sdf.format(actual.getTime()));
	}
	
}
