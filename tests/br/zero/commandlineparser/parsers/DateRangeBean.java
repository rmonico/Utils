package br.zero.commandlineparser.parsers;

import br.zero.commandlineparser.CommandLineSwitch;
import br.zero.types.DateRange;

public class DateRangeBean {
	private DateRange dateRange;

	public DateRange getDateRange() {
		return dateRange;
	}

	@CommandLineSwitch(index=1, parser="UtilsParser.parseDateRange")
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

}
