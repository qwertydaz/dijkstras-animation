package src.main.java.gui.javafx;

import javafx.scene.layout.Pane;

public class Table
{
	private Pane tablePane;

	public Table()
	{
		setupTablePane();
	}

	private void setupTablePane()
	{
		tablePane = new Pane();
	}

	public Pane getTablePane()
	{
		return tablePane;
	}
}
