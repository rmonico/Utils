package br.zero.textgrid;

// Não usei enums aqui para permitir extensões da classe
public abstract class ColumnAlignment {

	public abstract StringBuilder getAlignedString(Integer finalLength, StringBuilder value);
	
	private static StringBuilder replicateChar(char ch, int count) {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<count; i++) {
			sb.append(ch);
		}
		
		return sb;
	}

	public static final ColumnAlignment LEFT = new ColumnAlignment() {
		@Override
		public StringBuilder getAlignedString(Integer finalLength, StringBuilder value) {
			StringBuilder returnValue;
			
			int valueLength = value.length();
			
			if (valueLength < finalLength) {
				StringBuilder spaces = new StringBuilder(replicateChar(' ', finalLength - valueLength));
				
				returnValue = new StringBuilder(value);
				
				returnValue.append(spaces);
				
			} else if (valueLength > finalLength) {
				returnValue = new StringBuilder(value);
				
				returnValue.delete(finalLength-3, returnValue.length());
				returnValue.append("...");
			} else {
				// valueLength == finalLength, nada a fazer
				returnValue = value;
			}
			
			return returnValue;
		}
	};
	public static final ColumnAlignment CENTER = new ColumnAlignment() {
		public StringBuilder getAlignedString(Integer finalLength, StringBuilder value) {
			return null;
		};
	};
	public static final ColumnAlignment RIGHT = new ColumnAlignment() {
		public StringBuilder getAlignedString(Integer finalLength, StringBuilder value) {
			StringBuilder returnValue;
			
			int valueLength = value.length();
			
			if (valueLength < finalLength) {
				StringBuilder spaces = new StringBuilder(replicateChar(' ', finalLength - valueLength));
				
				returnValue = new StringBuilder(spaces);
				
				returnValue.append(value);
				
			} else if (valueLength > finalLength) {
				returnValue = new StringBuilder(value);
				
				returnValue.insert(0, "...");
				returnValue.delete(finalLength-3, returnValue.length());
			} else {
				// valueLength == finalLength, nada a fazer
				returnValue = value;
			}
			
			return returnValue;
		};
	};

}
