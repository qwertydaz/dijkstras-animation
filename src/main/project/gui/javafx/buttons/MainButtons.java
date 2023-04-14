package project.gui.javafx.buttons;

import javafx.scene.control.Button;
import project.gui.javafx.controller.Controller;
import project.gui.javafx.Graph;
import project.gui.javafx.Table;
import project.gui.javafx.util.Util;

import java.sql.SQLException;

public class MainButtons
{
	private final Buttons buttons;
	private final Controller controller;
	private final Graph graph;
	private final Table table;

	private Button runButton;
	private Button resetButton;
	private Button saveButton;
	private Button loadButton;

	public MainButtons(Buttons buttons, Controller controller, Graph graph, Table table)
	{
		this.buttons = buttons;
		this.controller = controller;
		this.graph = graph;
		this.table = table;

		setupButtons();
		setupButtonActions();
	}

	public void setupButtons()
	{
		// Button 1
		runButton = new Button("Run");

		// Button 2
		resetButton = new Button("Reset");

		// Button 3
		saveButton = new Button("Save");

		// Button 4
		loadButton = new Button("Load");
	}

	public void setupButtonActions()
	{
		// Button 1
		runButton.setOnAction(actionEvent -> run());

		// Button 2
		resetButton.setOnAction(actionEvent -> reset());

		// Button 3
		saveButton.setOnAction(actionEvent -> save());

		// Button 4
		loadButton.setOnAction(actionEvent -> load());
	}

	private void run()
	{
		if (controller.getStartNode() != null)
		{
			startAnimation();
		}
		else
		{
			Util.displayErrorMessage("No start node selected!",
					"Right click on a node to select it as the start node.");
		}
	}

	private void startAnimation()
	{
		buttons.startAnimation();
	}

	private void reset()
	{
		if (Util.displayOptionDialog("Reset", "Are you sure you want to reset?"))
		{
			table.clearAll();
			graph.clearAll();
		}
	}

	private void save()
	{
		if (Util.displayOptionDialog("Save", "Are you sure you want to save this graph?"))
		{
			connect();

			try
			{
				controller.saveNodes();
				controller.saveEdges();

				Util.displayInfoMessage("Success", "Saved to database");
			}
			catch (SQLException e)
			{
				Util.displayErrorMessage("Error saving to database", e.getMessage());
			}
		}
	}

	private void load()
	{
		if (Util.displayOptionDialog("Load", "Are you sure you want to load your saved graph?"))
		{
			connect();

			try
			{
				controller.loadNodes();
				controller.loadEdges();

				table.clearAll();
				graph.refresh();

				Util.displayInfoMessage("Success", "Loaded from database");
			}
			catch (SQLException e)
			{
				Util.displayErrorMessage("Error loading from database", e.getMessage());
			}
		}
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

	public Button getRunButton()
	{
		return runButton;
	}

	public Button getResetButton()
	{
		return resetButton;
	}

	public Button getSaveButton()
	{
		return saveButton;
	}

	public Button getLoadButton()
	{
		return loadButton;
	}
}
