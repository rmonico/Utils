package br.zero.textgrid;

// Não usei enums aqui para permitir extensões da classe
public abstract class ColumnAlignment {

	public abstract StringBuilder getAlignedString(Integer integer, StringBuilder value);

	public static final ColumnAlignment LEFT = new ColumnAlignment() {
		@Override
		public StringBuilder getAlignedString(Integer integer, StringBuilder value) {
			return null;
		}
	};
	public static final ColumnAlignment CENTER = new ColumnAlignment() {
		public StringBuilder getAlignedString(Integer integer, StringBuilder value) {
			return null;
		};
	};
	public static final ColumnAlignment RIGHT = new ColumnAlignment() {
		public StringBuilder getAlignedString(Integer integer, StringBuilder value) {
			return null;
		};
	};

}
