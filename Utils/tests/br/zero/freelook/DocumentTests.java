package br.zero.freelook;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DocumentTests {
	
	private class TestDocument extends AbstractDocument {

		@Override
		public void build() {
			setTitle("Document Title");
			
			Form form = createForm();
			
			form.setTitle("Form Title");
			
			Table table = form.createTable();
			
			TableColumn column1 = table.createColumn();
			
			column1.setTitle("Column 1");
			column1.setDataMethod("getColumn1");
			
			TableColumn column2 = table.createColumn();
			
			column2.setTitle("Column 2");
			column2.setDataMethod("getColumn2");

			ColumnSummary summary = column2.createSummary();
			
			summary.setDataMethod("getColumn2Summary");
		}

	}
	
	private AbstractDocument docBuilder = new TestDocument();
	
	@Test
	public void doDocumentTests() throws DocumentRendererException {
		List<StringBuilder> sbl = new ArrayList<StringBuilder>();
		
		PrintStream ps = new PrintStream(new StringBuilderListOutputStream(sbl));
		PrintStreamRenderer renderer = new PrintStreamRenderer(ps);
		
		renderer.render(docBuilder);
		

		// TODO Criar assert's aqui
	}
}
