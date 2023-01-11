package src.main.java.gui.javafx;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Controller
{
	private final Database db = new Database();

	public List<Circle> getNodes()
	{
		return db.getNodes();
	}

	public List<Line> getEdges()
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

	public Text findLabel(Circle node)
	{
		return db.findLabel(node);
	}

	public Text findLabel(Line edge)
	{
		return db.findLabel(edge);
	}

	public void setStartNode(Circle node)
	{
		db.setStartNode(node);
	}

	public List<ArrayList<String>> runDijkstra()
	{
		return db.runDijkstra();
	}
}
