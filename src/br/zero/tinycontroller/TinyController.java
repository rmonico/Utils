package br.zero.tinycontroller;

import java.util.HashMap;
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

	private Map<Object[], ConfiguredAction> registeredActions = new HashMap<Object[], ConfiguredAction>();
	private ConfiguredAction selectedAction;

	public void registerAction(Class<? extends Action> action, Object actionParam, Object... values) {
		ConfiguredAction configuredAction = new ConfiguredAction(action, actionParam);
		
		registeredActions.put(values, configuredAction);
	}

	public void selectAction(Object... params) {
		selectedAction = registeredActions.get(params);
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
		
		action.setParams(selectedAction.getParam());
	}
}
