package br.zero.tinycontroller;

public interface NoParamAction<ResultType> extends BaseAction {

	public ResultType run();
}
