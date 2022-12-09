package src.main.java.algorithm.dijkstra;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Edge
{
	private final Node node1;
	private final Node node2;
	private int weight;
	private final Set<Node> nodes;

	public Edge(Node node1, Node node2)
	{
		this.node1 = node1;
		this.node2 = node2;
		this.nodes = new HashSet<>(Arrays.asList(node1, node2));
	}

	public Edge(Node node1, Node node2, int weight)
	{
		this(node1, node2);
		this.weight = weight;
	}

	public Node getNode1()
	{
		return node1;
	}

	public Node getNode2()
	{
		return node2;
	}

	public int getWeight()
	{
		return weight;
	}

	public Set<Node> getNodes()
	{
		return nodes;
	}

	// checks if one edge has the same nodes as another edge
	public boolean isEqual(Edge edge)
	{
		return nodes.containsAll(edge.getNodes());
	}
}
