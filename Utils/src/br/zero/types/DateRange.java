package br.zero.types;

import java.util.Calendar;

public class DateRange {
	private Calendar start;
	private Calendar end;

	public DateRange() {
		super();
	}
	
	public DateRange(Calendar start, Calendar end) {
		this();
		
		this.start = start;
		this.end = end;
	}
	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

}
