package br.zero.textgrid;

import java.util.List;

public class TextGrid {

	private TextGridData data = new TextGridData();
	private List<?> values;
	
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

}
