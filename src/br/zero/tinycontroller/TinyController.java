package br.zero.tinycontroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TinyController {

	private class ConfiguredAction {
		private Class<? extends Action> action;
		private Object param;
		
		public ConfiguredAction(Class<? extends Action> action, Object param) {
			this.action = action;
			this.param = param;
		}

		public Class<? extends Action> getActionClass() {
			return action;
		}

		public Object getParam() {
			return param;
		}

	}

	private Map<List<?>, ConfiguredAction> registeredActions = new HashMap<List<?>, ConfiguredAction>();
	private ConfiguredAction selectedAction;

	public void registerAction(Class<? extends Action> action, Object actionParam, Object... values) {
		ConfiguredAction configuredAction = new ConfiguredAction(action, actionParam);
		
		registeredActions.put(Arrays.asList(values), configuredAction);
	}

	public void selectAction(Object... params) {
		selectedAction = registeredActions.get(Arrays.asList(params));
	}

	public boolean isActionFound() {
		return selectedAction != null;
	}

	public void runSelectedAction() throws TinyControllerException {
		Class<? extends Action> actionClass = selectedAction.getActionClass();
		
		Action action;
		try {
			action = actionClass.newInstance();
		} catch (Exception e) {
			throw new TinyControllerException(e);
		}
		
		Object param = selectedAction.getParam();
		
		try {
			action.run(param);
		} catch (Exception e) {
			throw new TinyControllerException("Exception launched running action.", e);
		}
	}
}
