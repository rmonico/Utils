package br.zero.tinycontroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TinyController {

	private Map<List<?>, Class<? extends BaseAction>> registeredActions = new HashMap<List<?>, Class<? extends BaseAction>>();
	private Class<? extends BaseAction> selectedActionClass;
	private Map<Class<? extends BaseAction>, BaseAction> actionPool = new HashMap<Class<? extends BaseAction>, BaseAction>();

	public void registerAction(Class<? extends BaseAction> action, Object... values) {
		registeredActions.put(Arrays.asList(values), action);
	}

	public boolean findActionFor(Object... values) {
		selectedActionClass = registeredActions.get(Arrays.asList(values));

		return selectedActionClass != null;
	}

	// TODO Mudar isso para rodar a ação selecionada. Mudar testes depois
	public Object runAction(Object arg) throws TinyControllerException {
		BaseAction action = getAction(selectedActionClass);

		Object result = runInstantiatedAction(arg, action);

		return result;
	}

	// TODO Ve depois se vale a pena mudar esse método para usar genéricos
	protected Object runInstantiatedAction(Object param, BaseAction action) throws TinyControllerException {

		try {
			if (action instanceof NoResultNoParamAction) {
				((NoResultNoParamAction) action).run();

				return null;
			} else if (action instanceof NoResultAction) {
				((NoResultAction) action).run(param);

				return null;
			} else if (action instanceof NoParamAction) {
				return ((NoParamAction) action).run();
			} else if (action instanceof Action) {
				return ((Action) action).run(param);
			}
		} catch (Exception e) {
			throw new TinyControllerException("Exception launched running action.", e);
		}

			throw new TinyControllerException("Action implementation not supported (" + action.getClass() + ").");
	}

	private BaseAction getAction(Class<? extends BaseAction> actionClass) throws TinyControllerException {
		BaseAction action;

		try {
			BaseAction actionFromPool = actionPool.get(actionClass);

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
