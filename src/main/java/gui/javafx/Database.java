package src.main.java.gui.javafx;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import src.main.java.exception.NodeNotFoundException;
import src.main.java.model.dijkstra.Dijkstra;
import src.main.java.model.dijkstra.Edge;
import src.main.java.model.dijkstra.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// TODO: setup mySQL connection (save/load)
public class Database
{
	private Connection conn = null;
	private int col;
	private final Dijkstra dijkstra;

	// Node data
	private final LinkedList<Node> nodes;
	private final LinkedList<Circle> nodeShapes;
	private Node startNode = null;
	private int nodeId;
	private String name;
	private double xCoord;
	private double yCoord;

	// Edge data
	private final LinkedList<Edge> edges;
	private final LinkedList<Line> edgeShapes;
	private int edgeId;
	private Node edgeNode1;
	private Node edgeNode2;
	private int weight;

	public Database()
	{
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		nodeShapes = new LinkedList<>();
		edgeShapes = new LinkedList<>();
		dijkstra = new Dijkstra(nodes, edges);
	}

	public List<Circle> getNodes()
	{
		return nodeShapes;
	}

	public List<Line> getEdges()
	{
		return edgeShapes;
	}

	public Node getStartNode()
	{
		return startNode;
	}

	public void setStartNode(Circle nodeShape)
	{
		startNode = findNode(nodeShape);
	}

	public void addNode(Text label, Circle node)
	{
		nodeShapes.add(node);
		nodes.add(new Node(label, node));
	}

	public void addEdge(Circle nodeShape1, Circle nodeShape2, Text label, Line edge)
	{
		Node node1 = findNode(nodeShape1);
		Node node2 = findNode(nodeShape2);

		if (node1 != null && node2 != null)
		{
			edges.add(new Edge(node1, node2, label, edge));
		}
		else
		{
			System.err.println("Edge cannot be added; Invalid nodes");
		}
	}

	public void removeNode(Circle nodeShape)
	{
		Node node = findNode(nodeShape);

		if (node != null)
		{
			nodes.remove(node);
			deleteConnectedEdges(node);
		}
		else
		{
			System.err.println("Node cannot be deleted; Invalid node");
		}
	}

	public void removeEdge(Line edgeShape)
	{
		Edge edge = findEdge(edgeShape);

		if (edge != null)
		{
			edges.remove(edge);
		}
		else
		{
			System.err.println("Edge cannot be deleted; Invalid edge");
		}
	}

	public Text findLabel(Circle nodeShape)
	{
		Node node = findNode(nodeShape);

		if (node == null)
		{
			return null;
		}

		return node.getLabel();
	}

	public Text findLabel(Line edgeShape)
	{
		Edge edge = findEdge(edgeShape);

		if (edge == null)
		{
			return null;
		}

		return edge.getLabel();
	}

	private Node findNode(Circle nodeShape)
	{
		for (Node node : nodes)
		{
			if (nodeShape == node.getShape())
			{
				return node;
			}
		}

		return null;
	}

	private Edge findEdge(Line edgeShape)
	{
		for (Edge edge : edges)
		{
			if (edgeShape == edge.getShape())
			{
				return edge;
			}
		}

		return null;
	}

	public boolean edgeExists(Circle nodeShape1, Circle nodeShape2)
	{
		Node node1 = findNode(nodeShape1);
		Node node2 = findNode(nodeShape2);

		for (Edge edge : edges)
		{
			if (edge.getNodes().contains(node1) && edge.getNodes().contains(node2))
			{
				return true;
			}
		}

		return false;
	}

	public void updateLabel(Text label, String newText)
	{
		for (Edge edge : edges)
		{
			if (label == edge.getLabel())
			{
				edge.setWeight(newText);
				return;
			}
		}

		for (Node node : nodes)
		{
			if (label == node.getLabel())
			{
				node.setName(newText);
				return;
			}
		}
	}

	private void deleteConnectedEdges(Node node)
	{
		edges.removeIf(edge -> node == edge.getNode1() || node == edge.getNode2());
	}

	public List<String[]> runDijkstra()
	{
		if (startNode != null)
		{
			dijkstra.updateNodes(nodes);
			return dijkstra.run(startNode);
		}

		return Collections.emptyList();
	}

	public void clear()
	{
		nodes.clear();
		edges.clear();
		nodeShapes.clear();
		edgeShapes.clear();
		startNode = null;
	}

	// Save/Load from MySQL Database

	private String[] getDatabaseDetails()
	{
		String csvFile = "./src/main/java/resources/database_details.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
		{
			String values = br.readLine();
			return values.split(",");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return new String[0];
	}

	public void connect() throws SQLException
	{
		if (conn != null) return;

		String[] databaseDetails = getDatabaseDetails();

		String url = databaseDetails[0];
		String user = databaseDetails[1];
		String password = databaseDetails[2];

		conn = DriverManager.getConnection(url, user, password);
	}

	public void disconnect()
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				System.err.println("Cannot close connection");
			}
		}
	}

	public void saveNodes() throws SQLException
	{
		String checkSql = "SELECT count(*) AS COUNT FROM nodes WHERE nodeId=?";
		String insertSql = "INSERT INTO nodes (nodeId, name, xCoord, yCoord) VALUES (?, ?, ?, ?)";
		String updateSql = "UPDATE nodes SET name=?, xCoord=?, yCoord=? WHERE nodeId=?";

		try (
				PreparedStatement checkStatement = conn.prepareStatement(checkSql);
				PreparedStatement insertStatement = conn.prepareStatement(insertSql);
				PreparedStatement updateStatement = conn.prepareStatement(updateSql))
		{
			for (Node node : nodes)
			{
				nodeId = node.getId();
				name = node.getName();
				xCoord = node.getShape().getCenterX();
				yCoord = node.getShape().getCenterY();

				checkStatement.setInt(1, nodeId);

				ResultSet checkResult = checkStatement.executeQuery();
				checkResult.next();

				// Checks if the node already exists in the database
				int count = checkResult.getInt(1);

				if (count == 0)
				{
					System.out.println("Inserting node with ID " + nodeId);

					col = 1;

					insertStatement.setInt(col++, nodeId);
					insertStatement.setString(col++, name);
					insertStatement.setDouble(col++, xCoord);
					insertStatement.setDouble(col, yCoord);

					insertStatement.executeUpdate();
				}
				else
				{
					System.out.println("Updating node with ID " + nodeId);

					col = 1;

					updateStatement.setInt(col++, nodeId);
					updateStatement.setString(col++, name);
					updateStatement.setDouble(col++, xCoord);
					updateStatement.setDouble(col, yCoord);

					updateStatement.executeUpdate();
				}
			}
		}
	}

	public void saveEdges() throws SQLException
	{
		String checkSql = "SELECT count(*) AS COUNT FROM edges WHERE edgeId=?";
		String insertSql = "INSERT INTO edges (edgeId, node1Name, node2Name, weight) VALUES (?, ?, ?, ?)";
		String updateSql = "UPDATE edges SET node1Name=?, node2Name=?, weight=? WHERE edgeId=?";

		try (
				PreparedStatement checkStatement = conn.prepareStatement(checkSql);
				PreparedStatement insertStatement = conn.prepareStatement(insertSql);
				PreparedStatement updateStatement = conn.prepareStatement(updateSql))
		{
			for (Edge edge : edges)
			{
				edgeId = edge.getId();
				edgeNode1 = edge.getNode1();
				edgeNode2 = edge.getNode2();
				weight = edge.getWeight();

				checkStatement.setInt(1, edgeId);

				ResultSet checkResult = checkStatement.executeQuery();
				checkResult.next();

				int count = checkResult.getInt(1);

				if (count == 0)
				{
					System.out.println("Inserting edge with ID " + edgeId);

					col = 1;

					insertStatement.setInt(col++, edgeId);
					insertStatement.setString(col++, edgeNode1.getName());
					insertStatement.setString(col++, edgeNode2.getName());
					insertStatement.setInt(col, weight);

					insertStatement.executeUpdate();
				}
				else
				{
					System.out.println("Updating edge with ID " + edgeId);

					col = 1;

					updateStatement.setInt(col++, edgeId);
					updateStatement.setString(col++, edgeNode1.getName());
					updateStatement.setString(col++, edgeNode2.getName());
					updateStatement.setInt(col, weight);

					updateStatement.executeUpdate();
				}
			}
		}
	}

	public void loadNodes() throws SQLException
	{
		nodes.clear();

		String sql = "SELECT nodeId, name, xCoord, yCoord FROM nodes ORDER BY nodeId";

		try (
				Statement selectStatement = conn.createStatement();
				ResultSet results = selectStatement.executeQuery(sql))
		{
			while (results.next())
			{
				getNodeResults(results);

				Node node = new Node(nodeId, name, xCoord, yCoord);
				nodes.add(node);

				System.out.println(node);
			}
		}
	}

	public void loadEdges() throws SQLException
	{
		edges.clear();

		String sql = "SELECT edgeId, node1Name, node2Name, weight FROM edges ORDER BY edgeId";

		try (
				Statement selectStatement = conn.createStatement();
				ResultSet results = selectStatement.executeQuery(sql))
		{
			while (results.next())
			{
				getEdgeResults(results);

				Edge edge = new Edge(edgeId, edgeNode1, edgeNode2, weight);
				edges.add(edge);

				System.out.println(edge);
			}
		}
	}

	private void getNodeResults(ResultSet results) throws SQLException
	{
		nodeId = results.getInt("nodeId");
		name = results.getString("name");
	}

	private void getEdgeResults(ResultSet results) throws SQLException
	{
		edgeId = results.getInt("edgeId");
		try
		{
			edgeNode1 = findNode(results.getString("node1Name"));
			edgeNode2 = findNode(results.getString("node2Name"));
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
		weight = results.getInt("weight");
	}

	private Node findNode(String searchName) throws NodeNotFoundException
	{
		for (Node node : nodes)
		{
			if (node.getName().equals(searchName))
			{
				return node;
			}
		}

		throw new NodeNotFoundException();
	}
}
