package project.database;

import project.model.dijkstra.Dijkstra;
import project.model.dijkstra.Edge;
import project.model.dijkstra.Node;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ComparisonChartData
{
	private final Dijkstra dijkstra;
	private final List<Node> nodes;
	private final List<Edge> edges;

	private final Random rand = SecureRandom.getInstanceStrong();

	public ComparisonChartData() throws NoSuchAlgorithmException
	{
		nodes = new LinkedList<>();
		edges = new LinkedList<>();

		dijkstra = new Dijkstra(nodes, edges);
	}

	// Creates a graph with the maximum number of edges between a specified number of nodes
	public void generateGraph(int numberOfNodes)
	{
		if (numberOfNodes < 2)
		{
			throw new IllegalArgumentException("generateGraph: Number of nodes must be greater than 1");
		}

		nodes.clear();
		edges.clear();

		for (int i = 0; i < numberOfNodes; i++)
		{
			nodes.add(new Node(i));
		}

		for (int i = 0; i < numberOfNodes; i++)
		{
			for (int j = i + 1; j < numberOfNodes; j++)
			{
				edges.add(new Edge(nodes.get(i), nodes.get(j), rand.nextInt(100) + 1));
			}
		}
	}

	// Runs Dijkstra's algorithm on a specified number of nodes and returns the total number of comparisons
	public int getComparisons(int numberOfNodes)
	{
		dijkstra.updateNodes(nodes);
		dijkstra.run(nodes.get(rand.nextInt(numberOfNodes)));
		return dijkstra.getComparisons();
	}

	public List<Node> getNodes()
	{
		return nodes;
	}
}
