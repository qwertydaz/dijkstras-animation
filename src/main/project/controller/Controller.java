package project.controller;

import project.exception.NodeNotFoundException;
import project.gui.swing.edge.EdgeFormEvent;
import project.gui.swing.node.NodeFormEvent;
import project.model.Database;
import project.model.dijkstra.Edge;
import project.model.dijkstra.Node;

import java.sql.SQLException;
import java.util.List;

public class Controller
{
	Database db = new Database();

	public List<Node> getNodes()
	{
		return db.getNodes();
	}

	public void addNode(NodeFormEvent event)
	{
		String name = event.getName();

		Node node = new Node(name);

		db.addNode(node);
	}

	public void removeNode(int index)
	{
		db.removeNode(index);
	}

	public void saveNodes() throws SQLException
	{
		db.saveNodes();
	}

	public void loadNodes() throws SQLException
	{
		db.loadNodes();
	}

	public List<Edge> getEdges()
	{
		return db.getEdges();
	}

	public void addEdge(EdgeFormEvent event)
	{
		try
		{
			Node node1 = findNode(event.getNode1Name());
			Node node2 = findNode(event.getNode2Name());
			int weight = event.getWeight();

			Edge edge = new Edge(node1, node2, weight);

			db.addEdge(edge);
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void removeEdge(int index)
	{
		db.removeEdge(index);
	}

	public void saveEdges() throws SQLException
	{
		db.saveEdges();
	}

	public void loadEdges() throws SQLException
	{
		db.loadEdges();
	}

	private Node findNode(String searchName) throws NodeNotFoundException
	{
		return db.findNode(searchName);
	}

	public boolean isNodePresent(String searchName)
	{
		return db.isNodePresent(searchName);
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