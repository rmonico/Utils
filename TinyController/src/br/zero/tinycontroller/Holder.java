package br.zero.tinycontroller;

/*
 * A class for just Hold a reference :)
 */
public class Holder<ReferenceType> {
	private ReferenceType value;

	public Holder(ReferenceType value) {
		this.value = value;
	}
	
	public ReferenceType getValue() {
		return value;
	}

}
