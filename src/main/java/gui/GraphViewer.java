package src.main.java.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GraphViewer extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		StackPane root = new StackPane();

		Circle circle = new Circle(100, 100, 50, Color.LIGHTBLUE);

		ContextMenu contextMenu = new ContextMenu();

		// Menu options
		MenuItem menuItem1 = new MenuItem("Add Node");
		MenuItem menuItem2 = new MenuItem("Add Edge");

		// Listeners

		// Left click
		root.setOnMouseClicked(mouseEvent -> contextMenu.hide());

		// Right click
		root.setOnContextMenuRequested(event ->
		{
			// Determine the type of the node that was right-clicked
			Object source = event.getPickResult().getIntersectedNode();
			contextMenu.getItems().clear();

			if (source instanceof Circle)
			{
				// Show only the second menu option if the user right-clicked on a Circle
				contextMenu.getItems().add(menuItem2);
			}
			else
			{
				// Show only the first menu option if the user right-clicked on a node other than a Circle
				contextMenu.getItems().add(menuItem1);
			}

			contextMenu.show(root, event.getScreenX(), event.getScreenY());
		});

		// Stage setup
		root.getChildren().add(circle);

		Scene scene = new Scene(root, 800, 600);

		primaryStage.setTitle("Graph Viewer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
