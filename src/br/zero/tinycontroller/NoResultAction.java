package br.zero.tinycontroller;

public interface NoResultAction<ParamType> extends BaseAction {

	public void run(ParamType arg) throws Exception;
}
