package br.zero.observer.tests;

import br.zero.observer.TableColumn;

public class SimpleLine {
	private Integer id;
	private String name;

	@TableColumn(title="id", index=0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@TableColumn(title="name", index=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

