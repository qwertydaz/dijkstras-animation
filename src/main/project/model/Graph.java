package project.model;

import project.model.dijkstra.Edge;
import project.model.dijkstra.Node;

import java.util.List;

public abstract class Graph
{
	List<Node> nodes;
	List<Edge> edges;

	protected Graph(List<Node> nodes, List<Edge> edges)
	{
		this.nodes = nodes;
		this.edges = edges;
	}

	public Edge findEdge(Node node1, Node node2)
	{
		for (Edge edge : edges)
		{
			if (edge.getNodes().contains(node1) && edge.getNodes().contains(node2))
			{
				return edge;
			}
		}

		return null;
	}
}
