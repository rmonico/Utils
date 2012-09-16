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

		boolean actionFound = controller.selectAction("action1");

		assertTrue(actionFound);
	}

	@Test
	public void should_run_the_action() throws TinyControllerException {
		controller.registerAction(ConcreteNoResultNoParamAction.class, "action1");

		controller.selectAction("action1");

		ConcreteNoResultNoParamAction.concreteNoResultNoParamActionRunned = false;
		
		controller.runSelectedAction(null);
		
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

		controller.selectAction("ConcreteNoResultAction");

		controller.runSelectedAction("param sent to action");
		
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

		controller.selectAction("ConcreteNoParamAction");

		String actionResult = controller.runSelectedAction(null);
		
		assertTrue("result sent from ConcreteNoParamAction".equals(actionResult));
	}
	
	public static class ConcreteAction implements Action<String, String> {

		public static Object receivedParam;

		@Override
		public String run(String arg) throws Exception {
			receivedParam = arg;
			return "result sent from ConcreteAction";
		}
		
	}
	
	@Test
	public void shoud_run_action() throws TinyControllerException {
		controller.registerAction(ConcreteAction.class, "ConcreteAction");

		controller.selectAction("ConcreteAction");

		String actionResult = controller.runSelectedAction("param sent to ConcreteAction");
		
		assertTrue("result sent from ConcreteAction".equals(actionResult));
		assertTrue("param sent to ConcreteAction".equals(ConcreteAction.receivedParam));
	}
	
	// TODO Testes do SetupableAction

	// TODO Testes do pool de ações
}
