package src.main.java.gui.javafx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class Buttons
{
	private final Controller controller;

	private HBox buttonsPane;
	private final Graph graph;
	private final Table table;

	private Button dijkstraButton;
	private Button resetButton;
	private Button saveButton;
	private Button loadButton;

	public Buttons(Controller controller, Graph graph, Table table)
	{
		this.controller = controller;
		this.graph = graph;
		this.table = table;

		setupButtonsPane();
	}

	private void setupButtonsPane()
	{
		buttonsPane = new HBox();

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
		dijkstraButton = new Button("Run");

		// Button 2
		resetButton = new Button("Reset");

		// Button 3
		saveButton = new Button("Save");

		// Button 4
		loadButton = new Button("Load");

		// Add buttons to buttonsPane
		buttonsPane.getChildren().addAll(dijkstraButton, resetButton, saveButton, loadButton);

		// Centre buttons
		buttonsPane.setAlignment(Pos.CENTER);
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

		// Button 2
		resetButton.setOnAction(actionEvent -> reset());

		// Button 3
		saveButton.setOnAction(actionEvent -> save());

		// Button 4
		loadButton.setOnAction(actionEvent -> load());
	}

	private void reset()
	{
		table.clearAll();
		graph.clearAll();
	}

	private void save()
	{
		connect();

		try
		{
			controller.saveNodes();
			controller.saveEdges();
		}
		catch (SQLException e)
		{
			Util.displayErrorMessage("Error saving to database", e.getMessage());
		}
	}

	private void load()
	{
		connect();

		try
		{
			controller.loadNodes();
			controller.loadEdges();

			table.refresh();
			graph.refresh();
		}
		catch (SQLException e)
		{
			Util.displayErrorMessage("Error loading from database", e.getMessage());
		}
	}

	private void errorMessagePrompt()
	{
		Util.displayErrorMessage("Not implemented yet!", "This feature is not implemented yet.");
	}

	private void connect()
	{
		try
		{
			controller.connect();
		}
		catch (Exception e)
		{
			Util.displayErrorMessage("Error connecting to database", e.getMessage());
		}
	}
}
