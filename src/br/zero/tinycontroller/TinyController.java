package br.zero.tinycontroller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TinyController {

	private Map<List<?>, Class<? extends Action<?, ?>>> registeredActions = new HashMap<List<?>, Class<? extends Action<?, ?>>>();
	private Class<? extends Action<?, ?>> selectedActionClass;

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

	/// Both warnings are treated on first catch block
	@SuppressWarnings("unchecked")
	public <ParamType, ReturnType> ReturnType runAction(ParamType param, Class<? extends ReturnType> returnTypeClass, Class<? extends Action<?, ?>> untypedActionClass) throws TinyControllerException {
		Class<? extends Action<ParamType, ReturnType>> actionClass;
		
		try {
			actionClass = (Class<? extends Action<ParamType, ReturnType>>) untypedActionClass;
		} catch (ClassCastException e) {
			throw new TinyControllerException("\"param\" class and returnTypeClass must be the same as generic parameters at \"actionClass\". Look corresponding registerAction call for \"" + untypedActionClass.getClass() + "\".", e);
		}
		
		Action<ParamType, ReturnType> action;

		try {
			action = actionClass.newInstance();
		} catch (Exception e) {
			throw new TinyControllerException(e);
		}

		Object untypedResult;

		try {
			untypedResult = action.run(param);
		} catch (Exception e) {
			throw new TinyControllerException("Exception launched running action.", e);
		}

		ReturnType result = null;
		
		try {
			result = (ReturnType) untypedResult;
		} catch(ClassCastException e) {
			assert false : "Treated on first catch, should never happen!";
		}
		
		return result;
	}
}
