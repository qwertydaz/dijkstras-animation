package src.main.java.gui.javafx;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import src.main.java.model.dijkstra.Edge;
import src.main.java.model.dijkstra.Node;

import java.util.List;

public class Controller
{
	Database db = new Database();

	public List<Node> getNodes()
	{
		return db.getNodes();
	}

	public List<Edge> getEdges()
	{
		return db.getEdges();
	}

	public void saveNode(Text label, Circle node)
	{
		db.saveNode(label, node);
	}

	public void saveEdge(Circle node1, Circle node2, Text label, Line edge)
	{
		db.saveEdge(node1, node2, label, edge);
	}

	public void deleteNode(Circle node)
	{
		db.deleteNode(node);
	}

	public void deleteEdge(Line edge)
	{
		db.deleteEdge(edge);
	}
}
