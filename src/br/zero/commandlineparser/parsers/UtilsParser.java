package br.zero.commandlineparser.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.zero.commandlineparser.CommandLineArgumentParserMethod;
import br.zero.types.DateRange;

public class UtilsParser {

	private static final Character DATERANGE_SEPARATOR = '-';
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
		String startDateStr = getStartDateStr(value);
		String endDateStr = getEndDateStr(value);

		Calendar startDate = GregorianCalendar.getInstance();
		try {
			startDate.setTime(dateFormat.parse(startDateStr));
		} catch (ParseException e) {
			error = "Error parsing start date: " + e.getMessage();
		}

		Calendar endDate = GregorianCalendar.getInstance();
		try {
			endDate.setTime(dateFormat.parse(endDateStr));
		} catch (ParseException e) {
			error = "Error parsing end date: " + e.getMessage();
		}

		return new DateRange(startDate, endDate);
	}

	private String getStartDateStr(String value) {
		int separatorIndex = value.indexOf(DATERANGE_SEPARATOR);

		if (separatorIndex == -1) {
			return null;
		}

		return value.substring(0, separatorIndex);
	}

	private String getEndDateStr(String value) {
		int separatorIndex = value.indexOf(DATERANGE_SEPARATOR);

		if (separatorIndex == -1) {
			return null;
		}

		return value.substring(separatorIndex + 1, value.length());
	}

	public String getError() {
		return error;
	}
}
