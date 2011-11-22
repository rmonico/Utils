package br.zero.textgrid;

import br.zero.utils.StringUtils;

// Não usei enums aqui para permitir extensões da classe
public abstract class TextGridColumnAlignment {

	public abstract StringBuilder getAlignedString(Integer finalLength, StringBuilder value);

	public static final TextGridColumnAlignment LEFT = new TextGridColumnAlignment() {
		@Override
		public StringBuilder getAlignedString(Integer finalLength, StringBuilder value) {
			StringBuilder returnValue;

			int valueLength = value.length();

			if (valueLength < finalLength) {
				StringBuilder spaces = new StringBuilder(StringUtils.replicateChar(' ', finalLength - valueLength));

				returnValue = new StringBuilder(value);

				returnValue.append(spaces);

			} else if (valueLength > finalLength) {
				returnValue = new StringBuilder(value);

				returnValue.delete(finalLength - 3, returnValue.length());
				returnValue.append("...");
			} else {
				// valueLength == finalLength, nada a fazer
				returnValue = value;
			}

			return returnValue;
		}
	};
	
	public static final TextGridColumnAlignment CENTER = new TextGridColumnAlignment() {
		public StringBuilder getAlignedString(Integer finalLength, StringBuilder value) {
			StringBuilder returnValue;

			int valueLength = value.length();

			if (valueLength <= finalLength) {
				StringBuilder spacesBefore = StringUtils.replicateChar(' ', (finalLength - valueLength) / 2);
				StringBuilder spacesAfter = StringUtils.replicateChar(' ', finalLength - (spacesBefore.length() + value.length()));

				returnValue = new StringBuilder(spacesBefore.toString() + value.toString() + spacesAfter.toString());
			} else {
				returnValue = LEFT.getAlignedString(finalLength, value);
			}
			
			return returnValue;
		};
	};
	
	public static final TextGridColumnAlignment RIGHT = new TextGridColumnAlignment() {
		public StringBuilder getAlignedString(Integer finalLength, StringBuilder value) {
			StringBuilder returnValue;

			int valueLength = value.length();

			if (valueLength < finalLength) {
				StringBuilder spaces = new StringBuilder(StringUtils.replicateChar(' ', finalLength - valueLength));

				returnValue = new StringBuilder(spaces);

				returnValue.append(value);

			} else if (valueLength > finalLength) {
				returnValue = new StringBuilder(value.substring(valueLength - finalLength, valueLength));

				returnValue.replace(0, 3, "...");
			} else {
				// valueLength == finalLength, nada a fazer
				returnValue = value;
			}

			return returnValue;
		};
	};

}
