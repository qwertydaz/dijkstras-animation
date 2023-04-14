package project.gui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class App extends Application
{
	private final Graph graph;
	private final Table table;
	private final ControlLineChart controlChart;
	private final ComparisonLineChart comparisonChart;
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
		controlChart = new ControlLineChart();
		comparisonChart = new ComparisonLineChart(controller);

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

		// Control Chart
		Pane controlChartPane = controlChart.getPane();
		controlChartPane.setPrefSize(600, 600);

		// Comparison Chart
		Pane comparisonChartPane = comparisonChart.getPane();
		comparisonChartPane.setPrefSize(600, 600);

		// Animation Box
		HBox animationBox = new HBox(graphPane, tablePane);
		HBox.setHgrow(graphPane, Priority.ALWAYS);
		HBox.setHgrow(tablePane, Priority.ALWAYS);

		// Chart Box
		HBox chartBox = new HBox(controlChartPane, comparisonChartPane);
		HBox.setHgrow(controlChartPane, Priority.ALWAYS);
		HBox.setHgrow(comparisonChartPane, Priority.ALWAYS);

		// Main Pane
		Pane mainPane = new Pane(animationBox);

		// Buttons
		Buttons buttons = new Buttons(controller, graph, table, mainPane, animationBox, chartBox);
		Pane buttonsPane = buttons.getPane();
		buttonsPane.setPrefSize(1200, 100);

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
