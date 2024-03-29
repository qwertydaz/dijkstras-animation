package project.controller;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import project.database.ComparisonChartData;
import project.database.Database;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Controller
{
	private final Database db = new Database();
	private final ComparisonChartData cd = new ComparisonChartData();

	public Controller() throws NoSuchAlgorithmException
	{
		// Empty Constructor
	}

	public List<Circle> getNodes()
	{
		return db.getNodes();
	}

	public boolean isReady()
	{
		return db.isReady();
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

	public Map<Circle, Text> getNodesAndLabels()
	{
		return db.getNodesAndLabels();
	}

	public Map<Line, Text> getEdgesAndLabels()
	{
		return db.getEdgesAndLabels();
	}

	public boolean isActive(Circle node)
	{
		return db.isActive(node);
	}

	public boolean isActive(Line edge)
	{
		return db.isActive(edge);
	}

	public String getNodeName(int nodeId)
	{
		return db.getNodeName(nodeId);
	}

	public Circle getNodeShape(int nodeId)
	{
		return db.getNodeShape(nodeId);
	}

	public void connect() throws SQLException
	{
		db.connect();
	}

	public void disconnect() throws SQLException
	{
		db.disconnect();
	}

	public void generateGraphData(int numberOfNodes)
	{
		cd.generateGraph(numberOfNodes);
	}

	public int getComparisons(int numberOfNodes)
	{
		return cd.getComparisons(numberOfNodes);
	}
}
