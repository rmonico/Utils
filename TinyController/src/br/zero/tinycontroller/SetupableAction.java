package br.zero.tinycontroller;

public interface SetupableAction extends BaseAction {
	
	/**
	 * Called before the first time the action is called.
	 * 
	 * @throws Exception
	 */
	public void setupAction() throws Exception;

}
