package br.zero.tinycontroller;

// TODO Depois criar uma annotation indicando se a ação deve ir ou não para o pool
// TODO Depois criar VoidResultAction<ParamType> public void run(ParamType param); NoParamAction<ResultType> public ResultType run(); VoidResultNoParamAction public void run()
public interface Action<ParamType, ResultType> {

	public ResultType run(ParamType param) throws Exception;
}
