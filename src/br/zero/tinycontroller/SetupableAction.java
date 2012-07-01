package br.zero.tinycontroller;

public interface SetupableAction<ParamType, ParamResult> extends Action<ParamType, ParamResult> {
	
	/**
	 * Called before the first time the action is called.
	 * 
	 * @throws Exception
	 */
	public void setupAction() throws Exception;

}
