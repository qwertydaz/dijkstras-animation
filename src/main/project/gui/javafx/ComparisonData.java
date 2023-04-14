package project.gui.javafx;

import project.model.dijkstra.Dijkstra;
import project.model.dijkstra.Edge;
import project.model.dijkstra.Node;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Random;

public class ComparisonData
{
	private final Dijkstra dijkstra;
	private final LinkedList<Node> nodes;
	private final LinkedList<Edge> edges;

	private final Random rand = SecureRandom.getInstanceStrong();

	public ComparisonData() throws NoSuchAlgorithmException
	{
		nodes = new LinkedList<>();
		edges = new LinkedList<>();

		dijkstra = new Dijkstra(nodes, edges);
	}

	private int[] calculateGraphSizes(int totalNodes, int numberOfGraphs)
	{
		int[] graphSizes = new int[numberOfGraphs];

		int nodesPerGraph = totalNodes / numberOfGraphs;
		int remainder = totalNodes % numberOfGraphs;

		for (int i = 0; i < numberOfGraphs; i++)
		{
			graphSizes[i] = nodesPerGraph;

			if (remainder > 0)
			{
				graphSizes[i]++;
				remainder--;
			}
		}

		return graphSizes;
	}

	private void generateGraph(int numberOfNodes)
	{
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

	public int getComparisons(int numberOfNodes)
	{
		int comparisons = 0;

		for (int i = 1; i <= numberOfNodes; i++)
		{
			//dijkstra.run();
			comparisons += dijkstra.getComparisons();
		}

		return comparisons;
	}
}
