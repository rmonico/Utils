package br.zero.observer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatter implements Formatter {

	@Override
	public String parse(Object cellValue) throws ObserverException {
		if (cellValue == null) {
			return NullFormatter.instance.parse(cellValue);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

		if (cellValue instanceof Date) {
			Date value = (Date) cellValue;

			return sdf.format(value);
		} else if (cellValue instanceof Calendar) {
			Calendar value = (Calendar) cellValue;

			return sdf.format(value.getTime());
		} else {
			throw new ObserverException("DATE_FORMATTER: Must be used only with java.util.Date or java.util.Calendar fields.");
		}
	}

}
