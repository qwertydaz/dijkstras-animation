package src.main.java.gui.javafx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO: issue when adding edge to nodes with same name and then changing them
// TODO: table breaks with duplicate node names
public class Database
{
	private Connection conn = null;
	private int col;
	private final Dijkstra dijkstra;
	private final Map<Integer, Set<Integer>> adjacencyMap;

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
		adjacencyMap = new HashMap<>();
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

	public int numNodes()
	{
		return nodes.size();
	}

	public int numEdges()
	{
		return edges.size();
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

			adjacencyMap.computeIfAbsent(node1.getId(), k -> new HashSet<>()).add(node2.getId());
			adjacencyMap.computeIfAbsent(node2.getId(), k -> new HashSet<>()).add(node1.getId());
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

	private Node findNode(int nodeId) throws NodeNotFoundException
	{
		for (Node node : nodes)
		{
			if (node.getId() == nodeId)
			{
				return node;
			}
		}

		throw new NodeNotFoundException();
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

	public List<Line> getAttachedEdges(Circle nodeShape)
	{
		List<Line> attachedEdges = new LinkedList<>();

		for (Edge edge : edges)
		{
			if (edge.getNodes().contains(findNode(nodeShape)))
			{
				attachedEdges.add(edge.getShape());
			}
		}

		return attachedEdges;
	}

	public boolean edgeExists(Circle nodeShape1, Circle nodeShape2)
	{
		int node1Id = findNode(nodeShape1).getId();
		int node2Id = findNode(nodeShape2).getId();

		Set<Integer> adjacentNodes = adjacencyMap.get(node1Id);

		return adjacentNodes != null && adjacentNodes.contains(node2Id);
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

			System.out.println(nodes);
			System.out.println(edges);

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

					updateStatement.setString(col++, name);
					updateStatement.setDouble(col++, xCoord);
					updateStatement.setDouble(col++, yCoord);
					updateStatement.setInt(col, nodeId);

					updateStatement.executeUpdate();
				}
			}
		}
	}

	public void saveEdges() throws SQLException
	{
		String checkSql = "SELECT count(*) AS COUNT FROM edges WHERE edgeId=?";
		String insertSql = "INSERT INTO edges (edgeId, node1Id, node2Id, weight) VALUES (?, ?, ?, ?)";
		String updateSql = "UPDATE edges SET node1Id=?, node2Id=?, weight=? WHERE edgeId=?";

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
					insertStatement.setInt(col++, edgeNode1.getId());
					insertStatement.setInt(col++, edgeNode2.getId());
					insertStatement.setInt(col, weight);

					insertStatement.executeUpdate();
				}
				else
				{
					System.out.println("Updating edge with ID " + edgeId);

					col = 1;

					updateStatement.setInt(col++, edgeNode1.getId());
					updateStatement.setInt(col++, edgeNode2.getId());
					updateStatement.setInt(col++, weight);
					updateStatement.setInt(col, edgeId);

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

				Circle nodeShape = new Circle(xCoord, yCoord, 50);
				nodeShape.setFill(Color.LIGHTBLUE);

				Text label = new Text(name);
				label.setFont(Font.font(24));

				label.xProperty().bind(nodeShape.centerXProperty().subtract(nodeShape.getRadius()/2));
				label.yProperty().bind(nodeShape.centerYProperty().add(nodeShape.getRadius()/8));

				Node node = new Node(nodeId, xCoord, yCoord, label, nodeShape);
				nodes.add(node);
			}
		}

		System.out.println(nodes);
	}

	public void loadEdges() throws SQLException
	{
		edges.clear();

		String sql = "SELECT edgeId, node1Id, node2Id, weight FROM edges ORDER BY edgeId";

		try (
				Statement selectStatement = conn.createStatement();
				ResultSet results = selectStatement.executeQuery(sql))
		{
			while (results.next())
			{
				getEdgeResults(results);

				Line edgeShape = new Line();
				edgeShape.setStrokeWidth(3);
				edgeShape.setStroke(Color.GRAY);

				edgeShape.startXProperty().bind(edgeNode1.getShape().centerXProperty());
				edgeShape.startYProperty().bind(edgeNode1.getShape().centerYProperty());
				edgeShape.endXProperty().bind(edgeNode2.getShape().centerXProperty());
				edgeShape.endYProperty().bind(edgeNode2.getShape().centerYProperty());

				Text label = new Text(String.valueOf(weight));
				label.setFont(Font.font(24));

				label.xProperty().bind(edgeShape.startXProperty().add(edgeShape.endXProperty()).divide(2));
				label.yProperty().bind(edgeShape.startYProperty().add(edgeShape.endYProperty()).divide(2));

				Edge edge = new Edge(edgeId, edgeNode1, edgeNode2, label, edgeShape);
				edges.add(edge);
			}
		}

		System.out.println(edges);
	}

	public double[] getCoords(Circle nodeShape)
	{
		Node node = findNode(nodeShape);
		assert node != null;
		return new double[] { node.getXCoord(), node.getYCoord() };
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
			edgeNode1 = findNode(results.getInt("node1Id"));
			edgeNode2 = findNode(results.getInt("node2Id"));
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
		weight = results.getInt("weight");
	}

	public Map<Circle, Text> getNodesAndLabels()
	{
		Map<Circle, Text> nodesAndLabels = new HashMap<>();

		for (Node node : nodes)
		{
			nodesAndLabels.put(node.getShape(), node.getLabel());
		}

		return nodesAndLabels;
	}

	public Map<Line, Text> getEdgesAndLabels()
	{
		Map<Line, Text> edgesAndLabels = new HashMap<>();

		for (Edge edge : edges)
		{
			edgesAndLabels.put(edge.getShape(), edge.getLabel());
		}

		return edgesAndLabels;
	}
}
