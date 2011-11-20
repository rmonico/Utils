package br.zero.tinycontroller;

public interface Action {
	public void setParams(Object o);
	public void run() throws ActionException;
}
