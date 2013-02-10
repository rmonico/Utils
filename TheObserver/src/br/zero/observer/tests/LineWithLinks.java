package br.zero.observer.tests;

import br.zero.observer.Link;
import br.zero.observer.TableColumn;

public class LineWithLinks {
	private int id;
	private String name;
	private Link inserir = createInserirLink();
	private Link excluir = createExcluirLink();

	@TableColumn(title = "id", index = 0)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@TableColumn(title = "name", index = 1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@TableColumn(title = "Inserir", index = 2)
	public Link getInserir() {
		return inserir;
	}

	private Link createInserirLink() {
		Link inserir = new Link();
		inserir.setLabel("Inserir");
		inserir.setHandler("inserir-handler");
		
		return inserir;
	}

	@TableColumn(title = "Excluir", index = 3)
	public Link getExcluir() {
 		return excluir;
	}

	private Link createExcluirLink() {
		Link excluir = new Link();
		excluir.setLabel("Excluir");
		excluir.setHandler("excluir-handler");
		
		return excluir;
	}

}
