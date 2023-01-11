package src.main.java.gui.javafx;

import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Buttons
{
	private Pane buttonsPane;
	private Graph graph;
	private Table table;

	private Button refreshTableButton;
	private Button fillTableButton;

	private Button clearGraphButton;

	public Buttons(Graph graph, Table table)
	{
		this.graph = graph;
		this.table = table;

		setupButtonsPane();
	}

	private void setupButtonsPane()
	{
		buttonsPane = new FlowPane();

		buttonsPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		setupButtons();
		setupButtonsActions();
	}

	public Pane getButtonsPane()
	{
		return buttonsPane;
	}

	private void setupButtons()
	{
		refreshTableButton = new Button("Refresh Table");
		fillTableButton = new Button("Fill Table");
		clearGraphButton = new Button("Clear Graph");

		buttonsPane.getChildren().addAll(refreshTableButton, fillTableButton, clearGraphButton);
	}

	private void setupButtonsActions()
	{
		refreshTableButton.setOnAction(actionEvent -> table.refreshTable());
		fillTableButton.setOnAction(actionEvent -> table.fillTable());
		clearGraphButton.setOnAction(actionEvent -> graph.clearGraph());
	}
}
