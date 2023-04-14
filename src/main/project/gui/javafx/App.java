package project.gui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import project.gui.javafx.buttons.Buttons;
import project.gui.javafx.controller.Controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class App extends Application
{
	private final Graph graph;
	private final Table table;
	private final ComparisonChart comparisonChart;
	private Controller controller;
	private Scene scene;

	public App()
	{
		try
		{
			controller = new Controller();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}

		graph = new Graph(controller);
		table = new Table();
		comparisonChart = new ComparisonChart(controller);

		setupPanes();
	}

	private void setupPanes()
	{
		// Graph
		Pane graphPane = graph.getPane();
		graphPane.setPrefSize(600, 600);

		// Table
		ScrollPane tablePane = table.getPane();
		tablePane.setPrefSize(600, 600);

		// Main Pane
		Pane mainPane = new Pane();

		// Animation Box
		HBox animationBox = new HBox(graphPane, tablePane);
		HBox.setHgrow(graphPane, Priority.ALWAYS);
		HBox.setHgrow(tablePane, Priority.ALWAYS);
		animationBox.prefHeightProperty().bind(mainPane.heightProperty());
		animationBox.prefWidthProperty().bind(mainPane.widthProperty());

		// Chart
		Pane chartPane = comparisonChart.getPane();
		chartPane.prefHeightProperty().bind(mainPane.heightProperty());
		chartPane.prefWidthProperty().bind(mainPane.widthProperty());

		mainPane.getChildren().addAll(animationBox);
		mainPane.setPrefSize(1200, 600);

		// Buttons
		Buttons buttons = new Buttons(controller, graph, table, comparisonChart, mainPane, animationBox);
		Pane buttonsPane = buttons.getPane();
		buttonsPane.setPrefSize(1200, 100);
		buttonsPane.setMaxHeight(100);
		buttonsPane.setMinHeight(100);

		// Full Pane
		VBox fullPane = new VBox(mainPane, buttonsPane);
		VBox.setVgrow(mainPane, Priority.ALWAYS);
		VBox.setVgrow(buttonsPane, Priority.ALWAYS);

		// Scene & CSS
		scene = new Scene(fullPane, 1200, 700);
		String cssPath = Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm();
		scene.getStylesheets().add(cssPath);
	}

	@Override
	public void start(Stage mainStage)
	{
		// Main Stage
		mainStage.setTitle("Dijkstra's Animation");
		mainStage.setScene(scene);

		mainStage.show();
		mainStage.setOnCloseRequest(event ->
		{
			try
			{
				controller.disconnect();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			System.exit(0);
		});
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
