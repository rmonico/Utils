package br.zero.tinycontroller;

public interface Action<ParamType, ResultType> {
	public ResultType run(ParamType param) throws Exception;
}
