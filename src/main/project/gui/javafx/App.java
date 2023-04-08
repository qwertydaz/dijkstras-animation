package project.gui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application
{
	Graph graph;
	Table table;
	Buttons buttons;
	Controller controller;

	public App()
	{
		controller = new Controller();

		graph = new Graph(controller);
		table = new Table(controller);
		buttons = new Buttons(controller, graph, table);
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage mainStage)
	{
		// Graph
		Pane graphPane = graph.getGraphPane();
		graphPane.setPrefSize(600, 600);

		// Table
		ScrollPane tablePane = table.getTablePane();
		tablePane.setPrefSize(600, 600);

		// Buttons
		Pane buttonsPane = buttons.getButtonsPane();
		buttonsPane.setPrefSize(1200, 100);

		// Full Pane
		HBox graphAndTablePane = new HBox(graphPane, tablePane);
		HBox.setHgrow(graphPane, Priority.ALWAYS);
		HBox.setHgrow(tablePane, Priority.ALWAYS);

		VBox fullPane = new VBox(graphAndTablePane, buttonsPane);
		VBox.setVgrow(graphAndTablePane, Priority.ALWAYS);
		VBox.setVgrow(buttonsPane, Priority.ALWAYS);

		Scene scene = new Scene(fullPane, 1200, 700);
		String cssPath = getClass().getResource("/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);

		// Main Stage
		mainStage.setTitle("Dijkstra's Animation");
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.setOnCloseRequest(e ->
		{
			controller.disconnect();
			System.exit(0);
		});
	}
}
