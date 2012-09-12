package br.zero.tinycontroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TinyController {

	private Map<List<?>, Class<? extends Action<?, ?>>> registeredActions = new HashMap<List<?>, Class<? extends Action<?, ?>>>();
	private Class<? extends Action<?, ?>> selectedActionClass;
	@SuppressWarnings("rawtypes")
	private Map<Class<Action>, Action<?, ?>> actionPool = new HashMap<Class<Action>, Action<?, ?>>();

	public void registerAction(Class<? extends Action<?, ?>> action, Object... values) {
		registeredActions.put(Arrays.asList(values), action);
	}

	public Class<? extends Action<?, ?>> selectAction(Object... values) {
		selectedActionClass = registeredActions.get(Arrays.asList(values));

		return selectedActionClass;
	}

	public boolean isActionFound() {
		return selectedActionClass != null;
	}

	// TODO Mudar isso para rodar a ação selecionada. Mudar testes depois
	@SuppressWarnings({ "rawtypes" })
	public Object runAction(Object param, Class<Action> actionClass) throws TinyControllerException {
		Action action = getAction(actionClass);

		Object result = runAction(param, action);

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object runAction(Object param, Action action) throws TinyControllerException {
		Object result;

		try {
			result = action.run(param);
		} catch (Exception e) {
			throw new TinyControllerException("Exception launched running action.", e);
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private Action getAction(Class<Action> actionClass) throws TinyControllerException {
		Action action;

		try {
			Action<?, ?> actionFromPool = actionPool.get(actionClass);
			
			if (actionFromPool == null) {
				action = actionClass.newInstance();
				if (action instanceof SetupableAction) {
					((SetupableAction) action).setupAction();
				}
				actionPool.put(actionClass, action);
			} else {
				action = actionFromPool;
			}
			
		} catch (Exception e) {
			throw new TinyControllerException(e);
		}
		
		return action;
	}

}
