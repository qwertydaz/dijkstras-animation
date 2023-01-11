package src.main.java.gui.javafx;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
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

public class Graph
{
	private Pane graphPane;

	private ContextMenu contextMenu;
	private SeparatorMenuItem separator;
	private MenuItem addNodeMenuItem;
	private MenuItem removeNodeMenuItem;
	private MenuItem addEdgeMenuItem;
	private MenuItem removeEdgeMenuItem;
	private MenuItem selectStartNodeMenuItem;

	private final TextField inputBox;
	private final Stage popupStage;

	private final List<Text> nodeLabels;

	private final List<Circle> selectedNodes;
	private Line selectedEdge;
	private Circle selectedStartNode;

	private static final Color selectedNodeColour = Color.RED;
	private static final Color unselectedNodeColour = Color.LIGHTBLUE;
	private static final Color selectedStartNodeColour = Color.GREEN;
	private static final Color selectedEdgeColour = Color.RED;
	private static final Color unselectedEdgeColour = Color.GRAY;

	private Object source;
	private final Controller controller = new Controller();

	public Graph()
	{
		nodeLabels = new ArrayList<>();
		selectedNodes = new ArrayList<>();
		inputBox = new TextField();
		popupStage = new Stage();
		popupStage.setScene(new Scene(inputBox, 300, 50));

		setupContextMenu();
		setupGraphPane();
	}

	// Right-click menu
	private void setupContextMenu()
	{
		contextMenu = new ContextMenu();

		separator = new SeparatorMenuItem();

		// Menu option 1
		addNodeMenuItem = new MenuItem("Add Node");

		// Menu option 2
		removeNodeMenuItem = new MenuItem("Remove Node");

		// Menu option 3
		addEdgeMenuItem = new MenuItem("Add Edge");

		// Menu option 4
		removeEdgeMenuItem = new MenuItem("Remove Edge");

		// Menu option 5
		selectStartNodeMenuItem = new MenuItem("Select Start Node");
	}

	private void setupMenuItemActions(Object source)
	{
		// Menu option 1
		addNodeMenuItem.setOnAction(actionEvent ->
		{
			if (controller.getNodes().size() < 10)
			{
				addNode();
			}
			else
			{
				Util.displayErrorMessage("MAX LIMIT Reached",
						"You can only have up to 10 nodes.");
			}
		});

		// Menu option 2
		removeNodeMenuItem.setOnAction(actionEvent -> removeNode((Circle) source));

		// Menu option 3
		addEdgeMenuItem.setOnAction(actionEvent -> addEdge());

		// Menu option 4
		removeEdgeMenuItem.setOnAction(actionEvent -> removeEdge((Line) source));

		// Menu option 5
		selectStartNodeMenuItem.setOnAction(actionEvent -> selectStartNode((Circle) source));
	}
	
	private void setupLeftClick()
	{
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
	}

	private void setupRightClick()
	{
		graphPane.setOnContextMenuRequested(event ->
		{
			// Determine what was right-clicked
			source = event.getPickResult().getIntersectedNode();
			setupMenuItemActions(source);
			contextMenu.getItems().clear();

			if (source instanceof Circle)
			{
				if (selectedStartNode != source)
				{
					contextMenu.getItems().add(selectStartNodeMenuItem);
				}

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
				contextMenu.getItems().add(addNodeMenuItem);
			}

			contextMenu.show(graphPane, event.getScreenX(), event.getScreenY());
		});
	}

	// Handles events in the graph pane
	private void setupGraphPane()
	{
		graphPane = new Pane();

		graphPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		// Left click
		setupLeftClick();

		// Right click
		setupRightClick();
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
		controller.saveNode(label, node);
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
			double x = node.getCenterX() + mouseEvent.getSceneX() - mouseLocation.get().getX();
			double y = node.getCenterY() + mouseEvent.getSceneY() - mouseLocation.get().getY();

			// Check if the circle is within the bounds of the pane
			if (x - node.getRadius() > 0 && x + node.getRadius() < graphPane.getWidth() &&
					y - node.getRadius() > 0 && y + node.getRadius() < graphPane.getHeight())
			{
				// Move the node by the drag distance
				node.setCenterX(x);
				node.setCenterY(y);

				// Update the current mouse position
				mouseLocation.set(new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY()));
			}
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

	private void removeNode(Circle node)
	{
		Text label = controller.findLabel(node);

		if (label != null)
		{
			graphPane.getChildren().remove(label);
		}
		else
		{
			System.err.println("Label cannot be deleted; Invalid label");
		}

		graphPane.getChildren().remove(node);
		controller.deleteNode(node);
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

		controller.saveEdge(node1, node2, label, edge);

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

	private void removeEdge(Line edge)
	{
		Text label = controller.findLabel(edge);

		if (label != null)
		{
			graphPane.getChildren().remove(label);
		}
		else
		{
			System.err.println("Label cannot be deleted; Invalid label");
		}

		graphPane.getChildren().remove(edge);
		controller.deleteEdge(edge);
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

	// Selects a starting node by changing colour and adding to variable
	private void selectStartNode(Circle node)
	{
		if (selectedStartNode == null)
		{
			controller.setStartNode(node);
			node.setStroke(selectedStartNodeColour);
			node.setStrokeWidth(3);
			selectedStartNode = node;
		}
		else
		{
			clearSelectedStartNode();
		}
	}

	// Deselects starting node by changing colour and emptying variable
	private void clearSelectedStartNode()
	{
		controller.setStartNode(null);
		selectedStartNode.setStroke(null);
		selectedStartNode = null;
	}

	private void displayNodeLabels()
	{
		for (Text label: nodeLabels)
		{
			label.toFront();
		}
	}

	public Pane getGraphPane()
	{
		return graphPane;
	}

	public void clearGraph()
	{
		graphPane.getChildren().clear();
		controller.clear();
	}
}
