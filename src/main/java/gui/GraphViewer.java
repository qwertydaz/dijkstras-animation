package src.main.java.gui;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class GraphViewer extends Application
{
	Pane graphPane;

	ContextMenu contextMenu;
	SeparatorMenuItem separator;
	MenuItem addNodeMenuItem;
	MenuItem removeNodeMenuItem;
	MenuItem addEdgeMenuItem;
	MenuItem removeEdgeMenuItem;

	TextField inputBox;
	Scene popupScene;
	Stage popupStage;

	List<Circle> nodes;
	List<Line> edges;
	List<Text> nodeLabels;

	List<Circle> selectedNodes;
	Line selectedEdge;

	Color selectedNodeColour = Color.RED;
	Color unselectedNodeColour = Color.LIGHTBLUE;
	Color selectedEdgeColour = Color.RED;
	Color unselectedEdgeColour = Color.GRAY;

	Alert errorDialog;

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
		nodeLabels = new ArrayList<>();
		selectedNodes = new ArrayList<>();
		errorDialog = new Alert(Alert.AlertType.ERROR);
		inputBox = new TextField();
		popupScene = new Scene(inputBox, 300, 50);
		popupStage = new Stage();
		popupStage.setScene(popupScene);

		setupContextMenu();

		setupGraphPane();

		Scene scene = new Scene(graphPane, 800, 600);

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

		// TODO:
		// Menu option 2
		removeNodeMenuItem = new MenuItem("Remove Node");
		removeNodeMenuItem.setOnAction(actionEvent -> removeNode((Circle) actionEvent.getSource()));

		// Menu option 3
		addEdgeMenuItem = new MenuItem("Add Edge");
		addEdgeMenuItem.setOnAction(actionEvent -> addEdge());

		// TODO:
		// Menu option 4
		removeEdgeMenuItem = new MenuItem("Remove Edge");
		removeEdgeMenuItem.setOnAction(actionEvent -> removeEdge((Line) actionEvent.getSource()));
	}

	// Handles events in the graph pane
	private void setupGraphPane()
	{
		graphPane = new Pane();

		// Left click
		graphPane.setOnMouseClicked(mouseEvent ->
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

			handleDoubleClick(mouseEvent);
		});

		// Right click
		graphPane.setOnContextMenuRequested(event ->
		{
			// Determine what was right-clicked
			source = event.getPickResult().getIntersectedNode();
			contextMenu.getItems().clear();

			if (source instanceof Circle)
			{
				// Show only the second menu option if the user right-clicked on a node
				contextMenu.getItems().add(removeNodeMenuItem);

				if (selectedNodes.size() == 2)
				{
					contextMenu.getItems().add(separator);
					contextMenu.getItems().add(addEdgeMenuItem);
				}
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

			contextMenu.show(graphPane, event.getScreenX(), event.getScreenY());
		});
	}

	private void handleDoubleClick(MouseEvent mouseEvent)
	{
		if (mouseEvent.getClickCount() == 2)
		{
			if (source instanceof Circle)
			{
				selectNode((Circle) source);
			}
			else if (source instanceof Text)
			{
				popupStage.setTitle("Edit Label");
				popupStage.show();

				inputBox.setOnAction(event ->
				{
					((Text) source).setText(inputBox.getText());
					popupStage.close();
				});
			}
		}
	}

	private void addNode()
	{
		Circle node = new Circle(graphPane.getWidth()/2, graphPane.getHeight()/2, 50);
		node.setFill(unselectedNodeColour);

		Text label = new Text("Node");
		label.setFont(Font.font(24));

		label.xProperty().bind(node.centerXProperty().subtract(node.getRadius()/2));
		label.yProperty().bind(node.centerYProperty().add(node.getRadius()/8));

		setupNodeListeners(node);

		graphPane.getChildren().add(label);
		graphPane.getChildren().add(node);
		nodes.add(node);
		nodeLabels.add(label);

		label.toFront();
	}

	private void setupNodeListeners(Circle node)
	{
		setupDraggableNode(node);

		// Setup more event listeners for node...
	}
	
	private void setupDraggableNode(Circle node)
	{
		AtomicReference<Point2D> mouseLocation = new AtomicReference<>();

		node.setOnMousePressed(mouseEvent ->
		{
			// Record the current mouse position
			mouseLocation.set(new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY()));

			if (selectedNodes.contains(node))
			{
				node.setFill(Color.DARKRED);
			}
			else
			{
				node.setFill(Color.DARKBLUE);
			}

			node.toFront();
			displayNodeLabels();
		});

		node.setOnMouseDragged(mouseEvent ->
		{
			// Calculate the distance the mouse was dragged
			double deltaX = mouseEvent.getSceneX() - mouseLocation.get().getX();
			double deltaY = mouseEvent.getSceneY() - mouseLocation.get().getY();

			// Move the node by the drag distance
			node.setCenterX(node.getCenterX() + deltaX);
			node.setCenterY(node.getCenterY() + deltaY);

			// Update the current mouse position
			mouseLocation.set(new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
		});

		node.setOnMouseReleased(mouseEvent ->
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
	}

	// TODO:
	private void removeNode(Circle node)
	{
		graphPane.getChildren().remove(node);
		nodes.remove(node);
	}

	private void addEdge()
	{
		Line edge = new Line();
		edge.setStrokeWidth(3);
		edge.setStroke(unselectedEdgeColour);

		setupEdgeListeners(edge);

		Circle node1 = selectedNodes.get(0);
		Circle node2 = selectedNodes.get(1);

		edge.startXProperty().bind(node1.centerXProperty());
		edge.startYProperty().bind(node1.centerYProperty());
		edge.endXProperty().bind(node2.centerXProperty());
		edge.endYProperty().bind(node2.centerYProperty());

		Text label = new Text("0");
		label.setFont(Font.font(24));

		// Bind the position of the label to the midpoint of the line
		label.xProperty().bind(edge.startXProperty().add(edge.endXProperty()).divide(2));
		label.yProperty().bind(edge.startYProperty().add(edge.endYProperty()).divide(2));

		graphPane.getChildren().add(label);
		graphPane.getChildren().add(edge);

		edges.add(edge);

		label.toFront();
		node1.toFront();
		node2.toFront();
		displayNodeLabels();
	}

	private void setupEdgeListeners(Line edge)
	{
		edge.setOnMouseClicked(event ->
		{
			if (event.getClickCount() == 2)
			{
				// The circle has been double-clicked
				selectEdge(edge);
			}
		});
	}

	// TODO:
	private void removeEdge(Line edge)
	{
		graphPane.getChildren().remove(edge);
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
		edge.setStroke(selectedEdgeColour);
		selectedEdge = edge;
	}

	// Deselects edge by changing colour and emptying variable
	private void clearSelectedEdge()
	{
		selectedEdge.setStroke(unselectedEdgeColour);
		selectedEdge = null;
	}

	private void displayNodeLabels()
	{
		for (Text label: nodeLabels)
		{
			label.toFront();
		}
	}

	private void displayErrorMessage(String header, String message)
	{
		errorDialog.setTitle("Error");
		errorDialog.setHeaderText(header);
		errorDialog.setContentText(message);
		errorDialog.showAndWait();
	}
}
