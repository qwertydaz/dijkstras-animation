package src.main.java.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GraphViewer extends Application
{
	StackPane root;

	ContextMenu contextMenu;
	SeparatorMenuItem separator;
	MenuItem addNodeMenuItem;
	MenuItem removeNodeMenuItem;
	MenuItem addEdgeMenuItem;
	MenuItem removeEdgeMenuItem;

	List<Circle> nodes;
	List<Line> edges;

	List<Circle> selectedNodes = new ArrayList<>();
	Line selectedEdge;

	Color selectedNodeColour = Color.RED;
	Color unselectedNodeColour = Color.LIGHTBLUE;
	Color selectedEdgeColour = Color.RED;
	Color unselectedEdgeColour = Color.BLACK;

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

		separator = new SeparatorMenuItem();

		// Menu option 1
		addNodeMenuItem = new MenuItem("Add Node");
		addNodeMenuItem.setOnAction(actionEvent -> addNode());

		// Menu option 2
		removeNodeMenuItem = new MenuItem("Remove Node");
		removeNodeMenuItem.setOnAction(actionEvent -> removeNode((Circle) actionEvent.getSource()));

		// Menu option 3
		addEdgeMenuItem = new MenuItem("Add Edge");
		addEdgeMenuItem.setOnAction(actionEvent -> addEdge());

		// Menu option 4
		removeEdgeMenuItem = new MenuItem("Remove Edge");
		removeEdgeMenuItem.setOnAction(actionEvent -> removeEdge((Line) actionEvent.getSource()));
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

			// Clear selections
			if (!(source instanceof Circle) && !(source instanceof Line))
			{
				clearSelectedNodes();

				if (selectedEdge != null)
				{
					clearSelectedEdge();
				}
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
				contextMenu.getItems().add(removeNodeMenuItem);
				contextMenu.getItems().add(separator);
				contextMenu.getItems().add(addEdgeMenuItem);
			}
			else if (source instanceof Line)
			{
				contextMenu.getItems().add(removeEdgeMenuItem);
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

		Circle node = new Circle(100, 100, 50, unselectedNodeColour);

		// Event listeners
		node.setOnMouseClicked(event ->
		{
			if (event.getClickCount() == 2)
			{
				// The circle has been double-clicked
				selectNode(node);
			}
		});

		// Tracks position of node when being dragged
		AtomicReference<Double> orgSceneX = new AtomicReference<>((double) 0);
		AtomicReference<Double> orgSceneY = new AtomicReference<>((double) 0);
		AtomicReference<Double> orgTranslateX = new AtomicReference<>((double) 0);
		AtomicReference<Double> orgTranslateY = new AtomicReference<>((double) 0);

		node.setOnMousePressed(event ->
		{
			orgSceneX.set(event.getSceneX());
			orgSceneY.set(event.getSceneY());
			orgTranslateX.set(((Circle) (event.getSource())).getTranslateX());
			orgTranslateY.set(((Circle) (event.getSource())).getTranslateY());

			if (selectedNodes.contains(node))
			{
				node.setFill(Color.DARKRED);
			}
			else
			{
				node.setFill(Color.DARKBLUE);
			}

			node.toFront();
		});

		node.setOnMouseDragged(event ->
		{
			double offsetX = event.getSceneX() - orgSceneX.get();
			double offsetY = event.getSceneY() - orgSceneY.get();
			double newTranslateX = orgTranslateX.get() + offsetX;
			double newTranslateY = orgTranslateY.get() + offsetY;

			((Circle)(event.getSource())).setTranslateX(newTranslateX);
			((Circle)(event.getSource())).setTranslateY(newTranslateY);
		});

		node.setOnMouseReleased(event ->
		{
			if (selectedNodes.contains(node))
			{
				node.setFill(selectedNodeColour);
			}
			else
			{
				node.setFill(unselectedNodeColour);
			}
		});

		root.getChildren().add(node);
		nodes.add(node);
	}

	private void removeNode(Circle node)
	{
		root.getChildren().remove(node);
		nodes.remove(node);
	}

	private void addEdge()
	{
		System.out.println("Add Edge");

		Line edge = new Line(0, 0, 100, 200);
		edge.setFill(unselectedEdgeColour);

		// Event listeners
		edge.setOnMouseClicked(event ->
		{
			if (event.getClickCount() == 2)
			{
				// The circle has been double-clicked
				selectEdge(edge);
			}
		});

		root.getChildren().add(edge);
		edges.add(edge);
	}

	private void removeEdge(Line edge)
	{
		root.getChildren().remove(edge);
		edges.remove(edge);
	}

	// Selects a node by changing colour and adding to list
	private void selectNode(Circle node)
	{
		// Unselects first node selected. Only 2 nodes can be selected at once
		if (selectedNodes.size() == 2)
		{
			selectedNodes.get(0).setFill(unselectedNodeColour);
			selectedNodes.remove(0);
		}

		node.setFill(selectedNodeColour);
		selectedNodes.add(node);
	}

	// Deselects all nodes by changing colours and emptying list
	private void clearSelectedNodes()
	{
		// This avoids ConcurrentModificationException being thrown
		List<Circle> tempList = new ArrayList<>(selectedNodes);

		for (Circle node : tempList)
		{
			node.setFill(unselectedNodeColour);
			selectedNodes.remove(node);
		}
	}

	// Selects an edge by changing colour and adding to variable
	private void selectEdge(Line edge)
	{
		edge.setFill(selectedEdgeColour);
		selectedEdge = edge;
	}

	// Deselects edge by changing colour and emptying variable
	private void clearSelectedEdge()
	{
		selectedEdge.setFill(unselectedEdgeColour);
		selectedEdge = null;
	}
}
