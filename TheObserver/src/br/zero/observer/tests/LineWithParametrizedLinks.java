package br.zero.observer.tests;


public class LineWithParametrizedLinks extends LineWithLinks {

	@Override
	public void setId(int id) {
		super.setId(id);
		
		getInserir().getParams().put("id", id);
		getExcluir().getParams().put("id", id);
	}

}
