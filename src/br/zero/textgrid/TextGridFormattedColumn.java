package br.zero.textgrid;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TextGridFormattedColumn implements TextGridColumn {

	private String title;
	private String separator;
	private TextGridFormatter formatter;
	private String lineGetterMethod;

	public static final TextGridFormatter ID_FORMATTER = createIDFormatter();
	public static final TextGridFormatter DATE_FORMATTER = createDateFormatter();
	public static final TextGridFormatter INTEGER_FORMATTER = createIntegerFormatter();
	public static final TextGridFormatter MONEY_FORMATTER = createMoneyFormatter();
	public static final TextGridFormatter STRING_FORMATTER = createStringFormatter();

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getSeparator() {
		return separator;
	}

	@Override
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public TextGridFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(TextGridFormatter formatter) {
		this.formatter = formatter;
	}
	
	public String getLineGetterMethod() {
		return lineGetterMethod;
	}
	
	public void setLineGetterMethod(String lineGetterMethod) {
		this.lineGetterMethod = lineGetterMethod;
	}

	@Override
	public Object getCellValue(Object line) throws TextGridException {
		Method getter;
		
		Object cellValue;
		
		try {
			getter = line.getClass().getMethod(lineGetterMethod);

			cellValue = getter.invoke(line);
		} catch (Exception e) {
			throw new TextGridException(e);
		}
		
		return cellValue;
	}

	public static TextGridFormatter createIDFormatter() {
		TextGridFormatter idFormatter = new TextGridFormatter() {
			
			@Override
			public String parse(Object cellValue) throws TextGridException {
				return "#" + INTEGER_FORMATTER.parse(cellValue);
			}
		};
		
		
		return idFormatter;
	}

	private static TextGridFormatter createDateFormatter() {
		TextGridFormatter dateFormatter = new TextGridFormatter() {
			
			@Override
			public String parse(Object cellValue) throws TextGridException {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

				if (cellValue instanceof Date) {
					Date value = (Date) cellValue;
					
					return sdf.format(value);
				} else if (cellValue instanceof Calendar) {
					Calendar value = (Calendar) cellValue;
					
					return sdf.format(value.getTime());
				} else {
					throw new TextGridException("DATE_FORMATTER: Must be used only with java.util.Date or java.util.Calendar fields.");
				}
			}
		};
		return dateFormatter;
	}

	private static TextGridFormatter createIntegerFormatter() {
		TextGridFormatter integerFormatter = new TextGridFormatter() {
			
			@Override
			public String parse(Object cellValue) throws TextGridException {
				if (!(cellValue instanceof Integer)) {
					throw new TextGridException("INTEGER_FORMATTER: Must be used only with java.lang.Integer fields.");
				}
				
				Integer value = (Integer) cellValue;
				
				String finalValue = value.toString();
				
				return finalValue;
			}
		};
		
		
		return integerFormatter;
	}

	private static TextGridFormatter createMoneyFormatter() {
		TextGridFormatter moneyFormatter = new TextGridFormatter() {
			
			@Override
			public String parse(Object cellValue) throws TextGridException {
				if (!(cellValue instanceof Double)) {
					throw new TextGridException("MONEY_FORMATTER: Must be used only with java.lang.Integer fields.");
				}
				
				Double value = (Double) cellValue;
				
				String finalValue = "$" + value.toString();
				
				return finalValue;
			}
		};
		
		
		return moneyFormatter;
	}

	private static TextGridFormatter createStringFormatter() {
		TextGridFormatter stringFormatter = new TextGridFormatter() {
			
			@Override
			public String parse(Object cellValue) throws TextGridException {
				
				if (cellValue == null) {
					return "";
				} else if (!(cellValue instanceof String)) {
					throw new TextGridException("STRING_FORMATTER: Must be used only with java.lang.String fields.");
				}
				
				return (String) cellValue;
			}
		};
		
		
		return stringFormatter;
	}

}
