package project.gui.javafx;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.Objects;

public class Buttons
{
	private final Controller controller;

	private HBox buttonsPane;
	private final Graph graph;
	private final Table table;
	private final Animation animation;

	private final Pane mainPane;
	private final HBox animationBox;
	private final HBox chartBox;

	private Button runButton;
	private Button resetButton;
	private Button saveButton;
	private Button loadButton;
	private Button forwardButton;
	private Button backButton;
	private Button stopButton;
	private Button swapButton;

	private boolean isSwapped = false;

	public Buttons(Controller controller, Graph graph, Table table, Animation animation, Pane mainPane,
	               HBox animationBox, HBox chartBox)
	{
		this.controller = controller;
		this.graph = graph;
		this.table = table;
		this.animation = animation;

		this.mainPane = mainPane;
		this.animationBox = animationBox;
		this.chartBox = chartBox;

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

	public Pane getPane()
	{
		return buttonsPane;
	}

	private void setupButtons()
	{
		// Load Icons
		Image swapIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/swap.png")));
		ImageView swapIconView = new ImageView(swapIcon);
		swapIconView.setFitHeight(50);
		swapIconView.setFitWidth(50);

		// Button 1
		runButton = new Button("Run");

		// Button 2
		resetButton = new Button("Reset");

		// Button 3
		saveButton = new Button("Save");

		// Button 4
		loadButton = new Button("Load");

		// Button 5
		swapButton = new Button("\u200E");
		swapButton.setGraphic(swapIconView);

		// Button 6
		forwardButton = new Button("→");

		// Button 7
		backButton = new Button("←");

		// Button 8
		stopButton = new Button("Stop");

		// Add buttons to buttonsPane
		buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton, swapButton);

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
		swapButton.setOnAction(actionEvent -> swap());

		// Button 6
		forwardButton.setOnAction(actionEvent -> forward());

		// Button 7
		backButton.setOnAction(actionEvent -> backward());

		// Button 8
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

	private void start()
	{
		// Toggle button visibility
		buttonsPane.getChildren().removeAll(runButton, resetButton, saveButton, loadButton, swapButton);
		buttonsPane.getChildren().addAll(backButton, forwardButton, stopButton);

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
		if (Util.displayOptionDialog("Stop", "Are you sure you want to stop the animation?"))
		{
			// Toggle button visibility
			buttonsPane.getChildren().removeAll(backButton, forwardButton, stopButton);
			buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton, swapButton);

			animation.stop();
		}
	}

	private void swap()
	{
		if (isSwapped)
		{
			mainPane.getChildren().remove(chartBox);
			mainPane.getChildren().add(animationBox);

			buttonsPane.getChildren().remove(swapButton);
			buttonsPane.getChildren().addAll(runButton, resetButton, saveButton, loadButton, swapButton);

			isSwapped = false;
		}
		else
		{
			mainPane.getChildren().remove(animationBox);
			mainPane.getChildren().add(chartBox);

			buttonsPane.getChildren().removeAll(runButton, resetButton, saveButton, loadButton);

			isSwapped = true;
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
}
