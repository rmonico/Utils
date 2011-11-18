package br.zero.commandlineparser.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;

public class UtilsParser {

	private String error;
	private DateFormat dateFormat;
	
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	@CommandLineArgumentParserMethod(messageMethod = "getError")
	public Calendar parseCalendar(String value) {
		Calendar calendar = GregorianCalendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(value));
		} catch (ParseException e) {
			error = "Não foi possível converter a string \"" + value + "\" para uma data...\n" + e.toString();
		}
		
		return calendar;
	}

	public String getError() {
		return error;
	}
}
