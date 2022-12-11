package src.main.java.model;

import src.main.java.model.dijkstra.Edge;
import src.main.java.model.dijkstra.Node;
import src.main.java.exception.WeightNotFoundException;

public abstract class Graph
{
	Node[] nodes;
	Edge[] edges;

	protected Graph(Node[] nodes, Edge[] edges)
	{
		this.nodes = nodes;
		this.edges = edges;
	}

	public boolean edgeExists(Edge potentialEdge)
	{
		for (Edge edge : edges)
		{
			if (potentialEdge.isEqual(edge))
			{
				return true;
			}
		}
		return false;
	}

	public int findWeight(Edge targetEdge) throws WeightNotFoundException
	{
		for (Edge edge : edges)
		{
			if (targetEdge.isEqual(edge))
			{
				// returns the weight as an integer
				return edge.getWeight();
			}
		}

		throw new WeightNotFoundException();
	}

	public Node[] getNodes()
	{
		return nodes;
	}

	public Edge[] getEdges()
	{
		return edges;
	}
}
