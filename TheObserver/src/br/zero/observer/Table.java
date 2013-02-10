package br.zero.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Indica a presença de uma tabela no documento.
 * 
 * @author Rafael Monico
 * 
 * @param <T>
 *            Indica a classe que representa a linha da tabela. Os métodos que
 *            representarem colunas devem anotados com <code>TableColumn</code>.
 */
public class Table<T> implements Element {

	private Class<T> lineClass;
	private List<T> rows;

	public Table(Class<T> lineClass) {
		this.lineClass = lineClass;
		rows = new ArrayList<T>();
	}

	@Override
	public ElementType getType() {
		return ElementType.TABLE;
	}

	public Class<T> getLineClass() {
		return lineClass;
	}

	public List<T> getRows() {
		return rows;
	}
	
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public void addRow(T row) {
		rows.add(row);
	}

}