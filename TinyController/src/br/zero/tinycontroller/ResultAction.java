package br.zero.tinycontroller;

public interface ResultAction<ResultType> extends BaseAction {

	public ResultType run();
}
