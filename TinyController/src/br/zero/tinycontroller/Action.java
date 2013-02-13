package br.zero.tinycontroller;

// TODO Depois criar uma annotation indicando se a ação deve ir ou não para o pool
public interface Action<ParamType, ResultType> extends BaseAction {

	public ResultType run(ParamType arg) throws ActionException;
}
