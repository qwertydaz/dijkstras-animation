package project.model;

import project.exception.NodeNotFoundException;
import project.model.dijkstra.Edge;
import project.model.dijkstra.Node;

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

public class Database
{
	private Connection conn = null;
	private int col;

	// Node data
	private final List<Node> nodes;
	private int nodeId;
	private String name;

	// Edge data
	private final List<Edge> edges;
	private int edgeId;
	private Node node1;
	private Node node2;
	private int weight;

	public Database()
	{
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
	}

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

//		try
//		{
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		}
//		catch (ClassNotFoundException e)
//		{
//			throw new ClassNotFoundException("Driver not found");
//		}

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
		String insertSql = "INSERT INTO nodes (nodeId, name) VALUES (?, ?)";
		String updateSql = "UPDATE nodes SET name=? WHERE nodeId=?";

		try (
				PreparedStatement checkStatement = conn.prepareStatement(checkSql);
				PreparedStatement insertStatement = conn.prepareStatement(insertSql);
				PreparedStatement updateStatement = conn.prepareStatement(updateSql))
		{
			for (Node node : nodes)
			{
				nodeId = node.getId();
				name = node.getName();

				checkStatement.setInt(1, nodeId);

				ResultSet checkResult = checkStatement.executeQuery();
				checkResult.next();

				int count = checkResult.getInt(1);

				if (count == 0)
				{
					System.out.println("Inserting node with ID " + nodeId);

					col = 1;

					insertStatement.setInt(col++, nodeId);
					insertStatement.setString(col++, name);

					insertStatement.executeUpdate();
				}
				else
				{
					System.out.println("Updating node with ID " + nodeId);

					col = 1;

					updateStatement.setInt(col++, nodeId);
					updateStatement.setString(col, name);

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
				node1 = edge.getNode1();
				node2 = edge.getNode2();
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
					insertStatement.setString(col++, node1.getName());
					insertStatement.setString(col++, node2.getName());
					insertStatement.setInt(col, weight);

					insertStatement.executeUpdate();
				}
				else
				{
					System.out.println("Updating edge with ID " + edgeId);

					col = 1;

					updateStatement.setInt(col++, edgeId);
					updateStatement.setString(col++, node1.getName());
					updateStatement.setString(col++, node2.getName());
					updateStatement.setInt(col, weight);

					updateStatement.executeUpdate();
				}
			}
		}
	}

	public void loadNodes() throws SQLException
	{
		nodes.clear();

		String sql = "SELECT nodeId, name FROM nodes ORDER BY nodeId";

		try (
				Statement selectStatement = conn.createStatement();
				ResultSet results = selectStatement.executeQuery(sql))
		{
			while (results.next())
			{
				getNodeResults(results);

				Node node = new Node(nodeId, name);
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

				Edge edge = new Edge(edgeId, node1, node2, weight);
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
			node1 = findNode(results.getString("node1Name"));
			node2 = findNode(results.getString("node2Name"));
		}
		catch (NodeNotFoundException e)
		{
			e.printStackTrace();
		}
		weight = results.getInt("weight");
	}

	public Node findNode(String searchName) throws NodeNotFoundException
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

	public boolean isNodePresent(String searchName)
	{
		for (Node node : nodes)
		{
			if (node.getName().equals(searchName))
			{
				return true;
			}
		}

		return false;
	}

	public void addNode(Node node)
	{
		nodes.add(node);
	}

	public void removeNode(int index)
	{
		nodes.remove(index);
	}

	public List<Node> getNodes()
	{
		return Collections.unmodifiableList(nodes);
	}

	public void addEdge(Edge edge)
	{
		edges.add(edge);
	}

	public void removeEdge(int index)
	{
		edges.remove(index);
	}

	public List<Edge> getEdges()
	{
		return Collections.unmodifiableList(edges);
	}
}
