package br.zero.tinycontroller;

import java.util.HashMap;
import java.util.Map;

public class TinyController {

	private class ConfiguredAction {
		private Action action;
		private Object param;
		
		public ConfiguredAction(Action action, Object param) {
			this.action = action;
			this.param = param;
		}

		public Action getAction() {
			return action;
		}

		public Object getParam() {
			return param;
		}

	}

	private Map<Object[], ConfiguredAction> registeredActions = new HashMap<Object[], ConfiguredAction>();
	private ConfiguredAction selectedAction;

	public void registerAction(Action action, Object actionParam, Object... values) {
		ConfiguredAction configuredAction = new ConfiguredAction(action, actionParam);
		
		registeredActions.put(values, configuredAction);
	}

	public void selectAction(Object... params) {
		selectedAction = registeredActions.get(params);
	}

	public boolean isActionFound() {
		return selectedAction != null;
	}

	public void runSelectedAction() {
		selectedAction.getAction().setParams(selectedAction.getParam());
	}
}
