package project.gui.javafx;

import project.model.dijkstra.Dijkstra;
import project.model.dijkstra.Edge;
import project.model.dijkstra.Node;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class ComparisonData
{
	private final Dijkstra dijkstra;
	private final LinkedList<Node> nodes;
	private final LinkedList<Edge> edges;

	private int[] graphSizes;

	private final Random rand = SecureRandom.getInstanceStrong();

	public ComparisonData() throws NoSuchAlgorithmException
	{
		nodes = new LinkedList<>();
		edges = new LinkedList<>();

		dijkstra = new Dijkstra(nodes, edges);
	}

	// Takes a total number of nodes and calculates an even distribution of nodes between a number of graphs
	private void calculateGraphSizes(int totalNodes, int numberOfGraphs)
	{
		graphSizes = new int[numberOfGraphs];

		for (int i = 1; i <= 5; i++)
		{
			graphSizes[i - 1] = totalNodes - i * numberOfGraphs;
		}
	}

	// Creates a graph with the maximum number of edges between a specified number of nodes
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

	// Runs Dijkstra's algorithm on a specified number of nodes and returns the total number of comparisons
	private int getComparisons(int numberOfNodes)
	{
		int comparisons = 0;

		for (int i = 1; i <= numberOfNodes; i++)
		{
			dijkstra.updateNodes(nodes);
			dijkstra.run(nodes.get(rand.nextInt(numberOfNodes - 1)));
			comparisons += dijkstra.getComparisons();
		}

		return comparisons;
	}

	// Repeatedly runs Dijkstra's algorithm on different sized graphs
	public Map<Integer, Integer> calculate(int totalNumberOfNodes, int numberOfSteps)
	{
		Map<Integer, Integer> results = new LinkedHashMap<>();
		calculateGraphSizes(totalNumberOfNodes, numberOfSteps);

		for (int graphSize : graphSizes)
		{
			generateGraph(graphSize);
			results.put(graphSize, getComparisons(graphSize));
		}

		return results;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException
	{
		ComparisonData cd = new ComparisonData();

		Map<Integer, Integer> results = cd.calculate(100, 10);

		StringBuilder sb = new StringBuilder();
		sb.append("Number of Nodes / Number of Comparisons\n\n");

		for (Map.Entry<Integer, Integer> entry : results.entrySet())
		{
			sb.append(entry.getKey())
					.append(" / ")
					.append(entry.getValue())
					.append("\n");
		}

		String output = sb.toString();
		Logger logger = Logger.getLogger(ComparisonData.class.getName());
		logger.info(output);
	}
}
