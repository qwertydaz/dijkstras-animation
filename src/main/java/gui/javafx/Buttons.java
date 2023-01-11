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
	private final Controller controller;

	private GridPane buttonsPane;
	private final Graph graph;
	private final Table table;

	private Button dijkstraButton;
	private Button clearGraphButton;

	public Buttons(Controller controller, Graph graph, Table table)
	{
		this.controller = controller;
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
		dijkstraButton = new Button("Run Dijkstra's Algorithm");
		dijkstraButton.prefHeight(Double.MAX_VALUE);
		dijkstraButton.prefWidth(Double.MAX_VALUE);
		GridPane.setHgrow(dijkstraButton, Priority.ALWAYS);
		GridPane.setVgrow(dijkstraButton, Priority.ALWAYS);

		// Button 2
		clearGraphButton = new Button("Clear Graph");
		clearGraphButton.prefHeight(Double.MAX_VALUE);
		clearGraphButton.prefWidth(Double.MAX_VALUE);
		GridPane.setHgrow(clearGraphButton, Priority.ALWAYS);
		GridPane.setVgrow(clearGraphButton, Priority.ALWAYS);

		buttonsPane.add(dijkstraButton, 0, 0);
		buttonsPane.add(clearGraphButton, 1, 0);
	}

	private void setupButtonsActions()
	{
		// Button 1
		dijkstraButton.setOnAction(actionEvent ->
		{
			if (controller.getStartNode() != null)
			{
				table.fillTable();
			}
			else
			{
				Util.displayErrorMessage("No start node selected!",
						"Right click on a node to select it as the start node.");
			}
		});

		// TODO: Clear table as well
		// Button 2
		clearGraphButton.setOnAction(actionEvent -> graph.clearGraph());
	}
}
