package br.zero.tinycontroller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TinyControllerTests {

	private TinyController controller = new TinyController();

	public static class ConcreteRunnableAction implements RunnableAction {
		
		public static boolean runned;

		@Override
		public void run() {
			runned = true;
		}

	}

	@Test
	public void should_found_the_action() {
		controller.registerAction(ConcreteRunnableAction.class, "action1");

		boolean actionFound = controller.selectAction("action1");

		assertTrue(actionFound);
	}

	@Test
	public void should_run_the_action() throws TinyControllerException {
		controller.registerAction(ConcreteRunnableAction.class, "action1");

		controller.selectAction("action1");

		ConcreteRunnableAction.runned = false;
		
		controller.runSelectedAction(null);
		
		assertTrue(ConcreteRunnableAction.runned);
	}
	
	public static class ConcreteParamAction implements ParamAction<String> {

		public static String receivedParam;
		
		@Override
		public void run(String arg) throws Exception {
			receivedParam = arg;
		}
		
	}
	
	@Test
	public void should_run_ParamAction() throws TinyControllerException {
		controller.registerAction(ConcreteParamAction.class, "ConcreteParamAction");

		controller.selectAction("ConcreteParamAction");

		controller.runSelectedAction("param sent to action");
		
		assertTrue("param sent to action".equals(ConcreteParamAction.receivedParam));
	}
	
	public static class ConcreteResultAction implements ResultAction<String> {

		@Override
		public String run() {
			return "result sent from ConcreteResultAction";
		}
		
	}
	
	@Test
	public void should_run_ResultAction() throws TinyControllerException {
		controller.registerAction(ConcreteResultAction.class, "ConcreteResultAction");

		controller.selectAction("ConcreteResultAction");

		String actionResult = controller.runSelectedAction(null);
		
		assertTrue("result sent from ConcreteResultAction".equals(actionResult));
	}
	
	public static class ConcreteAction implements Action<String, String> {

		public static Object receivedParam;

		@Override
		public String run(String arg) throws ActionException {
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
