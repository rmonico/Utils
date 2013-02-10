package br.zero.tinycontroller;

public interface ParamAction<ParamType> extends BaseAction {

	public void run(ParamType arg) throws Exception;
}
