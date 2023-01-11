package src.main.java.gui.javafx;

import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class Buttons
{
	private GridPane buttonsPane;
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
		buttonsPane = new GridPane();

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
		// Button 1
		refreshTableButton = new Button("Refresh Table");
		refreshTableButton.prefHeight(Double.MAX_VALUE);
		refreshTableButton.prefWidth(Double.MAX_VALUE);
		GridPane.setHgrow(refreshTableButton, Priority.ALWAYS);
		GridPane.setVgrow(refreshTableButton, Priority.ALWAYS);

		// Button 2
		fillTableButton = new Button("Fill Table");
		fillTableButton.prefHeight(Double.MAX_VALUE);
		fillTableButton.prefWidth(Double.MAX_VALUE);
		GridPane.setHgrow(fillTableButton, Priority.ALWAYS);
		GridPane.setVgrow(fillTableButton, Priority.ALWAYS);

		// Button 3
		clearGraphButton = new Button("Clear Graph");
		clearGraphButton.prefHeight(Double.MAX_VALUE);
		clearGraphButton.prefWidth(Double.MAX_VALUE);
		GridPane.setHgrow(clearGraphButton, Priority.ALWAYS);
		GridPane.setVgrow(clearGraphButton, Priority.ALWAYS);

		buttonsPane.add(refreshTableButton, 0, 0);
		buttonsPane.add(fillTableButton, 1, 0);
		buttonsPane.add(clearGraphButton, 2, 0);
	}

	private void setupButtonsActions()
	{
		refreshTableButton.setOnAction(actionEvent -> table.refreshTable());
		fillTableButton.setOnAction(actionEvent -> table.fillTable());
		clearGraphButton.setOnAction(actionEvent -> graph.clearGraph());
	}
}
