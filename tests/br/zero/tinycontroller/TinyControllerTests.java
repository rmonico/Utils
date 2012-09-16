package br.zero.tinycontroller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TinyControllerTests {

	private TinyController controller = new TinyController();

	public static class ConcreteNoResultNoParamAction implements NoResultNoParamAction {
		
		public static boolean concreteNoResultNoParamActionRunned;

		@Override
		public void run() {
			concreteNoResultNoParamActionRunned = true;
		}

	}

	@Test
	public void should_found_the_action() {
		controller.registerAction(ConcreteNoResultNoParamAction.class, "action1");

		boolean actionFound = controller.findActionFor("action1");

		assertTrue(actionFound);
	}

	@Test
	public void should_run_the_action() throws TinyControllerException {
		controller.registerAction(ConcreteNoResultNoParamAction.class, "action1");

		controller.findActionFor("action1");

		ConcreteNoResultNoParamAction.concreteNoResultNoParamActionRunned = false;
		
		controller.runAction(null);
		
		assertTrue(ConcreteNoResultNoParamAction.concreteNoResultNoParamActionRunned);
	}
	
	public static class ConcreteNoResultAction implements NoResultAction<String> {

		public static String receivedParam;
		
		@Override
		public void run(String arg) throws Exception {
			receivedParam = arg;
		}
		
	}
	
	@Test
	public void should_run_NoResultAction() throws TinyControllerException {
		controller.registerAction(ConcreteNoResultAction.class, "ConcreteNoResultAction");

		controller.findActionFor("ConcreteNoResultAction");

		controller.runAction("param sent to action");
		
		assertTrue("param sent to action".equals(ConcreteNoResultAction.receivedParam));
	}
	
	public static class ConcreteNoParamAction implements NoParamAction<String> {

		@Override
		public String run() {
			return "result sent from ConcreteNoParamAction";
		}
		
	}
	
	@Test
	public void should_run_NoParamAction() throws TinyControllerException {
		controller.registerAction(ConcreteNoParamAction.class, "ConcreteNoParamAction");

		controller.findActionFor("ConcreteNoParamAction");

		Object actionResult = controller.runAction(null);
		
		assertTrue("result sent from ConcreteNoParamAction".equals(actionResult));
	}
	
	// private static class Action1 implements Action<Object, Object> {
	//
	// @Override
	// public Object run(Object param) throws Exception {
	// return null;
	// }
	//
	// }
	//
	// @Test
	// public void shoud_found_a_action() {
	// TinyController controller = new TinyController();
	//
	// controller.registerAction(Action1.class, "Action1");
	//
	// controller.selectAction("Action1");
	//
	// assertTrue(controller.isActionFound());
	// }
	//
	// @Test
	// public void actionclass_found_should_be_same_action_registered() {
	// TinyController controller = new TinyController();
	//
	// controller.registerAction(Action1.class, "Action1");
	//
	// Class<? extends Action<?, ?>> action =
	// controller.selectAction("Action1");
	//
	// assertTrue(action.equals(Action1.class));
	// }
	//
	// private static class Action2Result {
	//
	// private String wrappedObject;
	//
	// public Action2Result(String wrappedObject) {
	// this.wrappedObject = wrappedObject;
	// }
	//
	// public String wrappedObject() {
	// return wrappedObject;
	// }
	//
	// }
	//
	// public static class Action2 implements Action<String, Action2Result> {
	//
	// @Override
	// public Action2Result run(String param) throws Exception {
	// return new Action2Result(param);
	// }
	//
	// }
	//
	// @Test
	// public void should_run_single_registered_action() throws
	// TinyControllerException {
	// TinyController controller = new TinyController();
	//
	// controller.registerAction(Action2.class, "Action2");
	//
	// @SuppressWarnings("rawtypes")
	// Class action = controller.selectAction("Action2");
	//
	// @SuppressWarnings("unchecked")
	// Object untypedResult = controller.runAction("param object", action);
	//
	// Action2Result result = (Action2Result) untypedResult;
	//
	// assertTrue("param object".equals(result.wrappedObject()));
	// }

	// TODO Testes do SetupableAction

	// TODO Testes do pool de ações

	// TODO Testes do NoResultNoParamAction, NoResultAction, NoParamAction
}
