package project.gui.javafx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
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
	private final Animation animation;

	private Button runButton;
	private Button resetButton;
	private Button saveButton;
	private Button loadButton;
	private Button forwardButton;
	private Button backButton;
	private Button stopButton;

	public Buttons(Controller controller, Graph graph, Table table, Animation animation)
	{
		this.controller = controller;
		this.graph = graph;
		this.table = table;
		this.animation = animation;

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
		runButton = new Button("Run");

		// Button 2
		resetButton = new Button("Reset");

		// Button 3
		saveButton = new Button("Save");

		// Button 4
		loadButton = new Button("Load");

		// Button 5
		forwardButton = new Button("Forward");

		// Button 6
		backButton = new Button("Back");

		// Button 7
		stopButton = new Button("Stop");

		// Add buttons to buttonsPane
		buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton);

		// Centre buttons
		buttonsPane.setAlignment(Pos.CENTER);
	}

	private void setupButtonsActions()
	{
		// Button 1
		runButton.setOnAction(actionEvent -> run());

		// Button 2
		resetButton.setOnAction(actionEvent -> reset());

		// Button 3
		saveButton.setOnAction(actionEvent -> save());

		// Button 4
		loadButton.setOnAction(actionEvent -> load());

		// Button 5
		forwardButton.setOnAction(actionEvent -> forward());

		// Button 6
		backButton.setOnAction(actionEvent -> backward());

		// Button 7
		stopButton.setOnAction(actionEvent -> stop());
	}

	private void run()
	{
		if (controller.getStartNode() != null)
		{
			start();
		}
		else
		{
			Util.displayErrorMessage("No start node selected!",
					"Right click on a node to select it as the start node.");
		}
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

			table.clearAll();
			graph.refresh();
		}
		catch (SQLException e)
		{
			Util.displayErrorMessage("Error loading from database", e.getMessage());
		}
	}

	private void start()
	{
		// Toggle button visibility
		buttonsPane.getChildren().removeAll(runButton, resetButton, saveButton, loadButton);
		buttonsPane.getChildren().addAll(forwardButton, backButton, stopButton);

		animation.start();
	}

	private void forward()
	{
		animation.forward();
	}

	private void backward()
	{
		animation.backward();
	}

	private void stop()
	{
		// Toggle button visibility
		buttonsPane.getChildren().removeAll(forwardButton, backButton, stopButton);
		buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton);

		animation.stop();
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
