package project.gui.javafx;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import project.exception.EdgeNotFoundException;
import project.model.dijkstra.Dijkstra;
import project.model.dijkstra.Edge;
import project.model.dijkstra.Node;

import project.exception.NodeNotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

	public Node getStartNode()
	{
		return startNode;
	}

	public void setStartNode(Circle nodeShape)
	{
		try
		{
			startNode = findNode(nodeShape);
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void setActive(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			node.setActive();
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void setActive(Line edgeShape)
	{
		try
		{
			Edge edge = findEdge(edgeShape);
			edge.setActive();
		}
		catch (EdgeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void setInactive(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			node.setInactive();
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void setInactive(Line edgeShape)
	{
		try
		{
			Edge edge = findEdge(edgeShape);
			edge.setInactive();
		}
		catch (EdgeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public Map<Circle, Line> getAdjacentNodesAndEdges(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			Map<Circle, Line> adjacentNodesAndEdges = new HashMap<>();

			for (Edge edge : edges)
			{
				if (edge.getNode1().equals(node))
				{
					adjacentNodesAndEdges.put(edge.getNode2().getShape(), edge.getShape());
				}
				else if (edge.getNode2().equals(node))
				{
					adjacentNodesAndEdges.put(edge.getNode1().getShape(), edge.getShape());
				}
			}

			return adjacentNodesAndEdges;
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}

	public void addNode(Text label, Circle node)
	{
		nodeShapes.add(node);
		nodes.add(new Node(label, node));
	}

	public void addEdge(Circle nodeShape1, Circle nodeShape2, Text label, Line edge)
	{
		try
		{
			Node node1 = findNode(nodeShape1);
			Node node2 = findNode(nodeShape2);

			edges.add(new Edge(node1, node2, label, edge));

			adjacencyMap.computeIfAbsent(node1.getId(), k -> new HashSet<>()).add(node2.getId());
			adjacencyMap.computeIfAbsent(node2.getId(), k -> new HashSet<>()).add(node1.getId());
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void removeNode(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			nodes.remove(node);
			deleteConnectedEdges(node);
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void removeEdge(Line edgeShape)
	{
		try
		{
			Edge edge = findEdge(edgeShape);
			edges.remove(edge);
		}
		catch (EdgeNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public Text findLabel(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			return node.getLabel();
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Text findLabel(Line edgeShape)
	{
		try
		{
			Edge edge = findEdge(edgeShape);
			return edge.getLabel();
		}
		catch (EdgeNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private Node findNode(Circle nodeShape) throws NodeNotFoundException
	{
		for (Node node : nodes)
		{
			if (nodeShape == node.getShape())
			{
				return node;
			}
		}

		throw new NodeNotFoundException();
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

	public String getNodeName(int nodeId)
	{
		try
		{
			return findNode(nodeId).getName();
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Circle getNodeShape(int nodeId)
	{
		try
		{
			return findNode(nodeId).getShape();
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private Edge findEdge(Line edgeShape) throws EdgeNotFoundException
	{
		for (Edge edge : edges)
		{
			if (edgeShape == edge.getShape())
			{
				return edge;
			}
		}

		throw new EdgeNotFoundException();
	}

	public List<Line> getAttachedEdges(Circle nodeShape)
	{
		try
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
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public boolean edgeExists(Circle nodeShape1, Circle nodeShape2)
	{
		try
		{
			int node1Id = findNode(nodeShape1).getId();
			int node2Id = findNode(nodeShape2).getId();

			Set<Integer> adjacentNodes = adjacencyMap.get(node1Id);

			return adjacentNodes != null && adjacentNodes.contains(node2Id);
		}
		catch (NodeNotFoundException e)
		{
			return false;
		}
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

	public Map<String[], String[]> runDijkstra()
	{
		if (startNode != null)
		{
			dijkstra.updateNodes(nodes);

			return dijkstra.run(startNode);
		}

		return Collections.emptyMap();
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
		try (
				InputStream inputStream = getClass().getResourceAsStream("/database_details.csv");
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader br = new BufferedReader(inputStreamReader))
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

	public void disconnect() throws SQLException
	{
		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				throw new SQLException("Cannot close connection", e);
			}
		}
	}

	public void saveNodes() throws SQLException
	{
		String deleteEdgesSql = "DELETE FROM edges";
		String deleteNodesSql = "DELETE FROM nodes";
		String insertSql = "INSERT INTO nodes (nodeId, name, xCoord, yCoord) VALUES (?, ?, ?, ?)";

		try (
				PreparedStatement deleteEdgesStatement = conn.prepareStatement(deleteEdgesSql);
				PreparedStatement deleteNodesStatement = conn.prepareStatement(deleteNodesSql);
				PreparedStatement insertStatement = conn.prepareStatement(insertSql))
		{
			// Delete all existing edges in the database
			deleteEdgesStatement.executeUpdate();

			// Delete all existing nodes in the database
			deleteNodesStatement.executeUpdate();

			for (Node node : nodes)
			{
				nodeId = node.getId();
				name = node.getName();
				xCoord = node.getShape().getCenterX();
				yCoord = node.getShape().getCenterY();

				col = 1;

				insertStatement.setInt(col++, nodeId);
				insertStatement.setString(col++, name);
				insertStatement.setDouble(col++, xCoord);
				insertStatement.setDouble(col, yCoord);

				insertStatement.executeUpdate();
			}
		}
	}

	public void saveEdges() throws SQLException
	{
		String insertSql = "INSERT INTO edges (edgeId, node1Id, node2Id, weight) VALUES (?, ?, ?, ?)";

		try (
				PreparedStatement insertStatement = conn.prepareStatement(insertSql))
		{
			for (Edge edge : edges)
			{
				edgeId = edge.getId();
				edgeNode1 = edge.getNode1();
				edgeNode2 = edge.getNode2();
				weight = edge.getWeight();

				col = 1;

				insertStatement.setInt(col++, edgeId);
				insertStatement.setInt(col++, edgeNode1.getId());
				insertStatement.setInt(col++, edgeNode2.getId());
				insertStatement.setInt(col, weight);

				insertStatement.executeUpdate();
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
	}

	public double[] getCoords(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			return new double[] { node.getXCoord(), node.getYCoord() };
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return new double[0];
		}
	}

	private void getNodeResults(ResultSet results) throws SQLException
	{
		nodeId = results.getInt("nodeId");
		name = results.getString("name");
		xCoord = results.getDouble("xCoord");
		yCoord = results.getDouble("yCoord");
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

	public int getNodeId(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			return node.getId();
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	public boolean isActive(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			return node.isActive();
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean isActive(Line edgeShape)
	{
		try
		{
			Edge edge = findEdge(edgeShape);
			return edge.isActive();
		}
		catch (EdgeNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public String getDetails(Circle nodeShape)
	{
		try
		{
			Node node = findNode(nodeShape);
			return node.toString();
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public String getDetails(Line edgeShape)
	{
		try
		{
			Edge edge = findEdge(edgeShape);
			return edge.toString();
		}
		catch (EdgeNotFoundException e)
		{
			e.printStackTrace();
			return "";
		}
	}
}
