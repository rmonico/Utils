package br.zero.textgrid;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TextGrid {

	private TextGridData data = new TextGridData();
	private List<?> values;
	
	public static final TextGridFormatter ID_FORMATTER = createIDFormatter();
	public static final TextGridFormatter DATE_FORMATTER = createDateFormatter();
	public static final TextGridFormatter INTEGER_FORMATTER = createIntegerFormatter();
	public static final TextGridFormatter MONEY_FORMATTER = createMoneyFormatter();
	public static final TextGridFormatter STRING_FORMATTER = createStringFormatter();

	public TextGridData getData() {
		return data;
	}

	public void setValues(List<?> values) {
		this.values = values;
	}

	public void show() throws TextGridException {
		makeListHeader();

		for (Object o : values) {

			StringBuilder line = new StringBuilder();

			for (TextGridFormattedColumn column : getData().getColumns()) {

				Object cellValue = column.getCellValue(o);
				
				TextGridFormatter formatter = column.getFormatter();
				
				String formattedCellValue = formatter.parse(cellValue);
				
				line.append(formattedCellValue);

				if (column != getData().getColumns().get(getData().getColumns().size() - 1)) {
					line.append(column.getSeparator());
				}
			}

			System.out.println(line.toString());
		}

		System.out.println("");

		System.out.println("-- Fim");

	}

	private void makeListHeader() {
		System.out.println("-- " + data.getTitle() + " --");
		System.out.println("");
		System.out.println("");

		StringBuilder columnTitles = new StringBuilder();

		for (TextGridFormattedColumn column : getData().getColumns()) {
			columnTitles.append(column.getTitle());

			// Se não é a última coluna...
			if (column != getData().getColumns().get(getData().getColumns().size() - 1)) {
				columnTitles.append(column.getSeparator());
			}
		}

		System.out.println(columnTitles);

		StringBuilder headerSeparator = new StringBuilder();

		for (int i = 0; i < columnTitles.length(); i++) {
			headerSeparator.append(getData().getHeaderSeparatorChar());
		}

		System.out.println(headerSeparator.toString());
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
