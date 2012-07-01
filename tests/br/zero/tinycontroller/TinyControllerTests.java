package br.zero.tinycontroller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TinyControllerTests {

	private static class Action1 implements Action<Object, Object> {

		@Override
		public Object run(Object param) throws Exception {
			return null;
		}

	}

	@Test
	public void shoud_found_a_action() {
		TinyController controller = new TinyController();

		controller.registerAction(Action1.class, "Action1");

		controller.selectAction("Action1");

		assertTrue(controller.isActionFound());
	}

	@Test
	public void actionclass_found_should_be_same_action_registered() {
		TinyController controller = new TinyController();

		controller.registerAction(Action1.class, "Action1");

		Class<? extends Action<?, ?>> action = controller.selectAction("Action1");

		assertTrue(action.equals(Action1.class));
	}

	private static class Action2Return {
		
		private String wrappedObject;

		public Action2Return(String wrappedObject) {
			this.wrappedObject = wrappedObject;
		}

		public String wrappedObject() {
			return wrappedObject;
		}

	}

	public static class Action2 implements Action<String, Action2Return> {

		@Override
		public Action2Return run(String param) throws Exception {
			return new Action2Return(param);
		}

	}

	@Test
	public void should_run_single_registered_action() throws TinyControllerException {
		TinyController controller = new TinyController();
		
		controller.registerAction(Action2.class, "Action2");
		
		Class<? extends Action<?, ?>> action = controller.selectAction("Action2");
		
		Action2Return result = controller.runAction("param object", Action2Return.class, action);
		
		assertTrue("param object".equals(result.wrappedObject()));
	}
}
