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
	private TextGridColumnAlignment alignment;

	public static final TextGridFormatter ID_FORMATTER = createIDFormatter();
	public static final TextGridFormatter DATE_FORMATTER = createDateFormatter();
	public static final TextGridFormatter INTEGER_FORMATTER = createIntegerFormatter();
	public static final TextGridFormatter MONEY_FORMATTER = createMoneyFormatter();
	public static final TextGridFormatter STRING_FORMATTER = createStringFormatter();
	public static final TextGridFormatter BOOLEAN_FORMATTER = createBooleanFormatter();
	public static final TextGridFormatter NULL_FORMATTER = createNullFormatter();

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
	public Object getCellObject(Object line) throws TextGridException {
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

	@Override
	public StringBuilder parse(Object cellValue) throws TextGridException {
		TextGridFormatter formatter = getFormatter();

		StringBuilder formattedCellValue = formatter.parse(cellValue);

		return formattedCellValue;
	}

	public static TextGridFormatter createIDFormatter() {
		TextGridFormatter idFormatter = new TextGridFormatter() {

			@Override
			public StringBuilder parse(Object cellValue) throws TextGridException {
				if (cellValue == null) {
					return NULL_FORMATTER.parse(cellValue);
				}
				
				return new StringBuilder("#" + INTEGER_FORMATTER.parse(cellValue));
			}
		};

		return idFormatter;
	}

	private static TextGridFormatter createDateFormatter() {
		TextGridFormatter dateFormatter = new TextGridFormatter() {

			@Override
			public StringBuilder parse(Object cellValue) throws TextGridException {
				
				if (cellValue == null) {
					return NULL_FORMATTER.parse(cellValue);
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

				if (cellValue instanceof Date) {
					Date value = (Date) cellValue;

					return new StringBuilder(sdf.format(value));
				} else if (cellValue instanceof Calendar) {
					Calendar value = (Calendar) cellValue;

					return new StringBuilder(sdf.format(value.getTime()));
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
			public StringBuilder parse(Object cellValue) throws TextGridException {
				if (cellValue == null) {
					return NULL_FORMATTER.parse(cellValue);
				}
				
				if (!(cellValue instanceof Integer)) {
					throw new TextGridException("INTEGER_FORMATTER: Must be used only with java.lang.Integer fields.");
				}

				Integer value = (Integer) cellValue;

				StringBuilder finalValue = new StringBuilder(value.toString());

				return finalValue;
			}
		};

		return integerFormatter;
	}

	private static TextGridFormatter createMoneyFormatter() {
		TextGridFormatter moneyFormatter = new TextGridFormatter() {

			@Override
			public StringBuilder parse(Object cellValue) throws TextGridException {
				if (cellValue == null) {
					return NULL_FORMATTER.parse(cellValue);
				}

				if (!(cellValue instanceof Double)) {
					throw new TextGridException("MONEY_FORMATTER: Must be used only with java.lang.Double fields.");
				}

				Double value = (Double) cellValue;

				StringBuilder finalValue = new StringBuilder("$" + value.toString());

				return finalValue;
			}
		};

		return moneyFormatter;
	}

	private static TextGridFormatter createStringFormatter() {
		TextGridFormatter stringFormatter = new TextGridFormatter() {

			@Override
			// TODO Trocar \t (caracteres tab) pelo texto <tab>
			public StringBuilder parse(Object cellValue) throws TextGridException {

				if (cellValue == null) {
					return NULL_FORMATTER.parse(cellValue);
				} else if (cellValue instanceof String) {
					return new StringBuilder((String) cellValue);
				} else if (cellValue instanceof StringBuilder) {
					return (StringBuilder) cellValue;
				} else if (cellValue instanceof Character) {
					return new StringBuilder((Character) cellValue);
				} else {
					throw new TextGridException("STRING_FORMATTER: Must be used only with java.lang.String fields.");
				}
			}
		};

		return stringFormatter;
	}

	private static TextGridFormatter createBooleanFormatter() {
		TextGridFormatter booleanFormatter = new TextGridFormatter() {

			@Override
			public StringBuilder parse(Object cellValue) throws TextGridException {

				if (cellValue == null) {
					return NULL_FORMATTER.parse(cellValue);
				} else if (cellValue instanceof Boolean) {
					Boolean b = (Boolean) cellValue;
					
					return new StringBuilder(b ? "[ X ]" : "[   ]");
				} else {
					throw new TextGridException("BOOLEAN_FORMATTER: Must be used only with java.lang.Boolean fields.");
				}
			}
		};

		return booleanFormatter;
	}

	private static TextGridFormatter createNullFormatter() {
		TextGridFormatter nullFormatter = new TextGridFormatter() {

			@Override
			public StringBuilder parse(Object cellValue) {
				return new StringBuilder("[null]");
			}
		};

		return nullFormatter;
	}

	public static TextGridFormattedColumn createFormattedColumn(TextGrid grid, String title, TextGridFormatter formatter, TextGridColumnAlignment alignment, String getterMethod, String columnSeparator) {
		TextGridFormattedColumn newColumn = new TextGridFormattedColumn();

		newColumn.setTitle(title);
		newColumn.setSeparator(columnSeparator);
		newColumn.setFormatter(formatter);
		newColumn.setAlignment(alignment);
		newColumn.setLineGetterMethod(getterMethod);

		grid.getData().registerColumn(newColumn);

		return newColumn;
	}

	public static TextGridFormattedColumn createFormattedColumn(TextGrid grid, String title, TextGridFormatter formatter, TextGridColumnAlignment alignment, String getterMethod) {
		return createFormattedColumn(grid, title, formatter, alignment, getterMethod, grid.getData().getDefaultColumnSeparator());
	}

	@Override
	public TextGridColumnAlignment getAlignment() {
		return alignment;
	}

	@Override
	public void setAlignment(TextGridColumnAlignment alignment) {
		this.alignment = alignment;
	}

}
