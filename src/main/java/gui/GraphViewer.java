package src.main.java.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GraphViewer extends Application
{
	StackPane root;
	ContextMenu contextMenu;
	MenuItem addNodeMenuItem;
	MenuItem addEdgeMenuItem;
	List<Circle> nodes;
	List<Line> edges;
	List<Circle> selectedNodes = new ArrayList<>();
	Object source;

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		nodes = new ArrayList<>();
		edges = new ArrayList<>();

		setupContextMenu();

		setupRoot();

		Scene scene = new Scene(root, 800, 600);

		primaryStage.setTitle("Graph Viewer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Right-click menu
	private void setupContextMenu()
	{
		contextMenu = new ContextMenu();

		// Menu option 1
		addNodeMenuItem = new MenuItem("Add Node");
		addNodeMenuItem.setOnAction(actionEvent -> addNode());

		// Menu option 2
		addEdgeMenuItem = new MenuItem("Add Edge");
		addEdgeMenuItem.setOnAction(actionEvent -> addEdge());
	}

	// Handles events in the window ("root")
	private void setupRoot()
	{
		root = new StackPane();

		// Left click
		root.setOnMouseClicked(mouseEvent ->
		{
			contextMenu.hide();

			source = mouseEvent.getPickResult().getIntersectedNode();

			if (source instanceof Circle)
			{
				selectNode((Circle) source);
			}
			else
			{
				clearSelectedNodes();
			}
		});

		// Right click
		root.setOnContextMenuRequested(event ->
		{
			// Determine what was right-clicked
			source = event.getPickResult().getIntersectedNode();
			contextMenu.getItems().clear();

			if (source instanceof Circle)
			{
				// Show only the second menu option if the user right-clicked on a node
				contextMenu.getItems().add(addEdgeMenuItem);
			}
			else
			{
				// Show only the first menu option if the user right-clicked elsewhere on the window
				contextMenu.getItems().add(addNodeMenuItem);
			}

			contextMenu.show(root, event.getScreenX(), event.getScreenY());
		});
	}

	private void addNode()
	{
		System.out.println("Add Node");
		Circle node = new Circle(100, 100, 50, Color.LIGHTBLUE);
		root.getChildren().add(node);
	}

	private void addEdge()
	{
		System.out.println("Add Edge");
	}

	// Selects a node by changing colour and adding to list
	private void selectNode(Circle node)
	{
		if (selectedNodes.size() == 2)
		{
			selectedNodes.remove(0);
		}

		node.setFill(Color.RED);
		selectedNodes.add(node);
	}

	// Deselects all nodes by changing colours and emptying list
	private void clearSelectedNodes()
	{
		// This avoids ConcurrentModificationException being thrown
		List<Circle> tempList = new ArrayList<>(selectedNodes);

		for (Circle node : tempList)
		{
			node.setFill(Color.LIGHTBLUE);
			selectedNodes.remove(node);
		}
	}
}
