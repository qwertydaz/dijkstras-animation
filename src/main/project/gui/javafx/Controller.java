package project.gui.javafx;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import project.model.dijkstra.Node;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

	public Node getStartNode()
	{
		return db.getStartNode();
	}

	public Map<Circle, Line> getAdjacentNodesAndEdges(Circle node)
	{
		return db.getAdjacentNodesAndEdges(node);
	}

	public void addNode(Text label, Circle node)
	{
		db.addNode(label, node);
	}

	public void addEdge(Circle node1, Circle node2, Text label, Line edge)
	{
		db.addEdge(node1, node2, label, edge);
	}

	public void removeNode(Circle node)
	{
		db.removeNode(node);
	}

	public List<Line> getAttachedEdges(Circle node)
	{
		return db.getAttachedEdges(node);
	}

	public void removeEdge(Line edge)
	{
		db.removeEdge(edge);
	}

	public boolean edgeExists(Circle node1, Circle node2)
	{
		return db.edgeExists(node1, node2);
	}

	public void updateLabel(Text label, String newText)
	{
		db.updateLabel(label, newText);
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

	public void setActive(Circle node)
	{
		db.setActive(node);
	}

	public void setActive(Line edge)
	{
		db.setActive(edge);
	}

	public void setInactive(Circle node)
	{
		db.setInactive(node);
	}

	public void setInactive(Line edge)
	{
		db.setInactive(edge);
	}

	public Map<String[], String[]> runDijkstra()
	{
		return db.runDijkstra();
	}

	public void clear()
	{
		db.clear();
	}

	public void saveEdges() throws SQLException
	{
		db.saveEdges();
	}

	public void loadEdges() throws SQLException
	{
		db.loadEdges();
	}

	public void saveNodes() throws SQLException
	{
		db.saveNodes();
	}

	public void loadNodes() throws SQLException
	{
		db.loadNodes();
	}

	public double[] getCoords(Circle node)
	{
		return db.getCoords(node);
	}

	public Map<Circle, Text> getNodesAndLabels()
	{
		return db.getNodesAndLabels();
	}

	public Map<Line, Text> getEdgesAndLabels()
	{
		return db.getEdgesAndLabels();
	}

	public int getNodeId(Circle node)
	{
		return db.getNodeId(node);
	}

	public boolean isNodeActive(Circle node)
	{
		return db.isNodeActive(node);
	}

	public boolean isEdgeActive(Line edge)
	{
		return db.isEdgeActive(edge);
	}

	public void connect() throws SQLException
	{
		db.connect();
	}

	public void disconnect()
	{
		db.disconnect();
	}
}
