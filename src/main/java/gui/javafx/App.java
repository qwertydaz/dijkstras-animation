package src.main.java.gui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application
{
	Graph graph;
	Table table;
	Buttons buttons;

	public App()
	{
		graph = new Graph();
		table = new Table();
		buttons = new Buttons();
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage mainStage)
	{
		Pane graphPane = graph.getGraphPane();
		graphPane.setPrefSize(600, 600);

		Pane tablePane = table.getTablePane();
		tablePane.setPrefSize(600, 600);

		Pane buttonsPane = buttons.getButtonsPane();
		buttonsPane.setPrefSize(1200, 100);

		HBox graphAndTablePane = new HBox(graphPane, tablePane);
		HBox.setHgrow(graphPane, Priority.ALWAYS);
		HBox.setHgrow(tablePane, Priority.ALWAYS);

		VBox fullPane = new VBox(graphAndTablePane, buttonsPane);
		VBox.setVgrow(graphAndTablePane, Priority.ALWAYS);
		VBox.setVgrow(buttonsPane, Priority.ALWAYS);

		Scene scene = new Scene(fullPane, 1200, 700);

		mainStage.setTitle("Graph Viewer");
		mainStage.setScene(scene);
		mainStage.show();
	}
}
