package br.zero.observer;

@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value = { java.lang.annotation.ElementType.METHOD })
public @interface TableColumn {

	String title();

	/**
	 * When informed, turns the table column into a link.
	 * 
	 * @return
	 */
	// TODO Tests!
	String linkHandler() default "";

	int index();

	Class<? extends Formatter> formatterClass() default DefaultFormatter.class;

}
