package br.zero.commandlineparser.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;
import br.zero.types.DateRange;

public class UtilsParser {

	private String error;
	private DateFormat dateFormat;
	
	public UtilsParser() {
		super();
	}
	
	public UtilsParser(DateFormat dateFormat) {
		this();
		
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

	@CommandLineArgumentParserMethod(messageMethod = "getError")
	public DateRange parseDateRange(String value) {
		String dataInicio = getDataInicio(value);
		String dataFim = getDataFim(value);
		
		
		Calendar startDate = GregorianCalendar.getInstance();
		try {
			startDate.setTime(dateFormat.parse("13/nov/2011"));
		} catch (ParseException e) {
			error = "Error parsing start date: " + e.getMessage();
		}
		
		Calendar endDate = GregorianCalendar.getInstance();
		try {
			endDate.setTime(dateFormat.parse("15/nov/2011"));
		} catch (ParseException e) {
			error = "Error parsing end date: " + e.getMessage();
		}
		
		return new DateRange(startDate, endDate);
	}
	
	private String getDataInicio(String value) {
//		int separatorIndex = 
		return null;
	}
	
	private String getDataFim(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getError() {
		return error;
	}
}
