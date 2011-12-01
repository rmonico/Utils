package br.zero.textgrid;

import java.util.ArrayList;
import java.util.List;

import br.zero.utils.StringUtils;

public class TextGrid {

	private TextGridData data = new TextGridData();
	private List<?> values;
	private List<List<StringBuilder>> matrix;

	public TextGridData getData() {
		return data;
	}

	public void setValues(List<?> values) {
		this.values = values;
	}

	public void show() throws TextGridException {
		calcValuesMatrix();

		makeList();

		int valueCount = values.size();
		
		System.out.println("(" + valueCount + " line" + (valueCount == 1 ? "" : "s") + ")");

		System.out.println("--");

	}

	private void makeList() {
		System.out.println("-- " + data.getTitle() + " --");
		System.out.println("");
		System.out.println("");

		List<StringBuilder> headerLine = matrix.get(0);

		List<? extends TextGridColumn> columns = getData().getColumns();
		TextGridColumn lastColumn = columns.get(columns.size() - 1);

		for (List<StringBuilder> line : matrix) {

			StringBuilder outputLine = new StringBuilder();
			for (int i = 0; i < line.size(); i++) {
				StringBuilder cell = line.get(i);
				TextGridColumn column = columns.get(i);

				outputLine.append(cell);

				// Não adiciona separador depois da última coluna
				if (column != lastColumn) {
					outputLine.append(column.getSeparator());
				}
			}

			System.out.println(outputLine);

			// Se é a primeira linha, imprime o separador
			if (line == headerLine) {
				System.out.println(StringUtils.replicateChar(getData().getHeaderSeparatorChar(), outputLine.length()));
			}
		}
	}

	private void calcValuesMatrix() throws TextGridException {
		List<List<StringBuilder>> baseMatrix = new ArrayList<List<StringBuilder>>();

		List<StringBuilder> headerBaseMatrix = new ArrayList<StringBuilder>();

		for (TextGridColumn column : getData().getColumns()) {
			headerBaseMatrix.add(new StringBuilder(column.getTitle()));
		}

		baseMatrix.add(headerBaseMatrix);

		List<? extends TextGridColumn> columns = getData().getColumns();

		for (Object o : values) {
			List<StringBuilder> lineBaseMatrix = new ArrayList<StringBuilder>();

			for (TextGridColumn column : columns) {

				Object cellObject = column.getCellObject(o);

				StringBuilder cellValue = column.parse(cellObject);

				lineBaseMatrix.add(cellValue);
			}

			baseMatrix.add(lineBaseMatrix);
		}

		if (!getData().isAligned()) {
			matrix = baseMatrix;
			return;
		}

		// Faz o alinhamento das colunas através da inclusão de espaços nos
		// valores

		List<Integer> columnsWidth = new ArrayList<Integer>();

		for (int i = 0; i < baseMatrix.get(0).size(); i++) {
			columnsWidth.add(-1);
		}

		for (List<StringBuilder> line : baseMatrix) {
			// Não posso usar for melhorado aqui, preciso saber o índice
			for (int i = 0; i < line.size(); i++) {
				StringBuilder value = line.get(i);

				if (value.length() > columnsWidth.get(i)) {
					columnsWidth.set(i, value.length());
				}
			}
		}

		matrix = new ArrayList<List<StringBuilder>>();

		for (List<StringBuilder> baseLine : baseMatrix) {

			List<StringBuilder> matrixLine = new ArrayList<StringBuilder>();

			// Não posso usar for melhorado aqui, preciso saber o índice
			for (int i = 0; i < baseLine.size(); i++) {
				StringBuilder value = baseLine.get(i);

				TextGridColumnAlignment columnAlignment = columns.get(i).getAlignment();

				StringBuilder finalValue = columnAlignment.getAlignedString(columnsWidth.get(i), value);

				matrixLine.add(finalValue);
			}

			matrix.add(matrixLine);
		}

	}

}
