package br.zero.observer.tests;

import static br.zero.utils.JUnitUtils.assertListsEquals;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import br.zero.observer.Document;
import br.zero.observer.Link;
import br.zero.observer.ObserverException;
import br.zero.observer.Renderer;
import br.zero.observer.Table;
import br.zero.observer.htmlrenderer.HTMLDefaultStyle;
import br.zero.observer.htmlrenderer.HTMLRenderer;
import br.zero.observer.htmlrenderer.HTMLStyle;

public class HTMLRendererTests {

	private List<String> prepareAndRenderizeDocument(Document document) throws ObserverException {
		HTMLStyle defaultStyle = new HTMLDefaultStyle();

		Renderer renderer;
		renderer = new HTMLRenderer(defaultStyle);

		renderer.setDocument(document);

		StringBufferOutputStream out;
		out = new StringBufferOutputStream();

		PrintStream stream = new PrintStream(out);
		renderer.setOutput(stream);

		renderer.renderize();

		return out.getBuffer();
	}

	@Test
	public void should_return_a_empty_html_document() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() {

			}
		};

		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");

		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Empty Document", expected, buffer);
	}

	@Test
	public void should_return_a_document_with_title() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() {
				setTitle("Title");
			}

		};

		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("  <title>Title</title>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("<h1>Title</h1><br/>");
		expected.add("<br/>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");

		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Document with title", expected, buffer);
	}

	@Test
	public void should_return_a_empty_table_without_header() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() throws ObserverException {
				Table<Object> table = new Table<Object>(Object.class);

				addElement(table);
			}

		};


		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("  <table>");
		expected.add("    <!-- header -->");
		expected.add("    <tr>");
		expected.add("    </tr>");
		expected.add("  </table>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");
		
		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Table without header", expected, buffer);
	}

	@Test
	public void should_return_a_empty_table_with_header() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() throws ObserverException {
				Table<SimpleLine> table = new Table<SimpleLine>(SimpleLine.class);

				addElement(table);
			}

		};


		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("  <table>");
		expected.add("    <!-- header -->");
		expected.add("    <tr>");
		expected.add("      <td>id</td>");
		expected.add("      <td>name</td>");
		expected.add("    </tr>");
		expected.add("  </table>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");

		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Empty table with header", expected, buffer);
	}

	@Test
	public void should_return_a_table_with_two_rows() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() throws ObserverException {
				Table<SimpleLine> table = new Table<SimpleLine>(SimpleLine.class);

				SimpleLine line1 = new SimpleLine();

				line1.setId(1);
				line1.setName("Row 1");

				SimpleLine line2 = new SimpleLine();

				line2.setId(2);
				line2.setName("Row 2");

				table.addRow(line1);
				table.addRow(line2);
				
				addElement(table);
			}

		};

		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("  <table>");
		expected.add("    <!-- header -->");
		expected.add("    <tr>");
		expected.add("      <td>id</td>");
		expected.add("      <td>name</td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("    <!-- body -->");
		expected.add("    <tr>");
		expected.add("      <td>");
		expected.add("        1");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        Row 1");
		expected.add("      </td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("    <tr>");
		expected.add("      <td>");
		expected.add("        2");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        Row 2");
		expected.add("      </td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("  </table>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");

		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Table with two rows", expected, buffer);
	}

	@Test
	public void should_return_a_document_with_ok_link() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() throws ObserverException {
				Link link = new Link();

				link.setHandler("ok-handler");
				link.setLabel("Ok Link Label");

				addElement(link);
			}

		};

		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("  <a href=\"ok-handler\">Ok Link Label</a>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");

		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Document with link", expected, buffer);
	}

	@Test
	public void should_return_a_document_with_parametrized_link() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() throws ObserverException {
				Link link = new Link();

				link.setHandler("ok-handler");
				link.setLabel("Ok Link Label");
				
				Map<String, Object> params = link.getParams();
				
				params.put("param1", "value1");
				params.put("param2", "value2");

				addElement(link);
			}

		};

		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("  <a href=\"ok-handler?param1=value1&param2=value2&\">Ok Link Label</a>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");

		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Document with parametrized link", expected, buffer);
	}

	@Test
	public void should_return_a_table_with_links_inside_cells() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() throws ObserverException {
				Table<LineWithLinks> table = new Table<LineWithLinks>(LineWithLinks.class);

				LineWithLinks line1 = new LineWithLinks();

				line1.setId(1);
				line1.setName("Row 1");

				LineWithLinks line2 = new LineWithLinks();

				line2.setId(2);
				line2.setName("Row 2");
				
				table.addRow(line1);
				table.addRow(line2);

				addElement(table);
			}

		};

		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("  <table>");
		expected.add("    <!-- header -->");
		expected.add("    <tr>");
		expected.add("      <td>id</td>");
		expected.add("      <td>name</td>");
		expected.add("      <td>Inserir</td>");
		expected.add("      <td>Excluir</td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("    <!-- body -->");
		expected.add("    <tr>");
		expected.add("      <td>");
		expected.add("        1");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        Row 1");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        <a href=\"inserir-handler\">Inserir</a>");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        <a href=\"excluir-handler\">Excluir</a>");
		expected.add("      </td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("    <tr>");
		expected.add("      <td>");
		expected.add("        2");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        Row 2");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        <a href=\"inserir-handler\">Inserir</a>");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        <a href=\"excluir-handler\">Excluir</a>");
		expected.add("      </td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("  </table>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");

		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Table with links inside", expected, buffer);

	}
	
	@Test
	public void should_return_a_table_with_parametrized_links_inside_cells() throws ObserverException {
		Document document = new Document() {

			@Override
			public void create() throws ObserverException {
				Table<LineWithParametrizedLinks> table = new Table<LineWithParametrizedLinks>(LineWithParametrizedLinks.class);

				LineWithParametrizedLinks line1 = new LineWithParametrizedLinks();

				line1.setId(1);
				line1.setName("Row 1");

				LineWithParametrizedLinks line2 = new LineWithParametrizedLinks();

				line2.setId(2);
				line2.setName("Row 2");
				
				table.addRow(line1);
				table.addRow(line2);
				
				addElement(table);
			}

		};

		List<String> expected = new ArrayList<String>();

		expected.add("<html>");
		expected.add("<head>");
		expected.add("</head>");
		expected.add("<body>");
		expected.add("  <table>");
		expected.add("    <!-- header -->");
		expected.add("    <tr>");
		expected.add("      <td>id</td>");
		expected.add("      <td>name</td>");
		expected.add("      <td>Inserir</td>");
		expected.add("      <td>Excluir</td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("    <!-- body -->");
		expected.add("    <tr>");
		expected.add("      <td>");
		expected.add("        1");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        Row 1");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        <a href=\"inserir-handler?id=1&\">Inserir</a>");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        <a href=\"excluir-handler?id=1&\">Excluir</a>");
		expected.add("      </td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("    <tr>");
		expected.add("      <td>");
		expected.add("        2");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        Row 2");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        <a href=\"inserir-handler?id=2&\">Inserir</a>");
		expected.add("      </td>");
		expected.add("      <td>");
		expected.add("        <a href=\"excluir-handler?id=2&\">Excluir</a>");
		expected.add("      </td>");
		expected.add("    </tr>");
		expected.add("");
		expected.add("  </table>");
		expected.add("</body>");
		expected.add("</html>");
		expected.add("");

		List<String> buffer = prepareAndRenderizeDocument(document);

		assertListsEquals("Table with parametrized links inside", expected, buffer);

	}
	
	@Test
	public void should_return_a_table_with_formatted_values() {
		// TODO Testar os formatadores
	}
}
