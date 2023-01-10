package src.main.java.gui.javafx;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import src.main.java.model.dijkstra.Dijkstra;
import src.main.java.model.dijkstra.Edge;
import src.main.java.model.dijkstra.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// TODO: setup mySQL connection (save/load)
public class Database
{
	private final LinkedList<Node> nodes;
	private final LinkedList<Edge> edges;
	private final Dijkstra dijkstra;

	public Database()
	{
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		dijkstra = new Dijkstra(nodes, edges);
	}

	public List<Node> getNodes()
	{
		return nodes;
	}

	public List<Edge> getEdges()
	{
		return edges;
	}

	public void saveNode(Text label, Circle node)
	{
		nodes.add(new Node(label, node));
	}

	public void saveEdge(Circle nodeShape1, Circle nodeShape2, Text label, Line edge)
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

	public void deleteNode(Circle nodeShape)
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

	public void deleteEdge(Line edgeShape)
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

	private void deleteConnectedEdges(Node node)
	{
		edges.removeIf(edge -> node == edge.getNode1() || node == edge.getNode2());
	}

	public List<ArrayList<String>> runDijkstra(Circle node)
	{
		Node startNode = findNode(node);

		if (startNode != null)
		{
			return dijkstra.findShortestPaths(startNode);
		}

		return Collections.emptyList();
	}
}
