package project.gui.javafx.data;

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

public class ComparisonChartData
{
	private final Dijkstra dijkstra;
	private final LinkedList<Node> nodes;
	private final LinkedList<Edge> edges;

	private int[] graphSizes;

	private final Random rand = SecureRandom.getInstanceStrong();
	private static final Logger logger = Logger.getLogger(ComparisonChartData.class.getName());

	public ComparisonChartData() throws NoSuchAlgorithmException
	{
		nodes = new LinkedList<>();
		edges = new LinkedList<>();

		dijkstra = new Dijkstra(nodes, edges);
	}

	// Takes a total number of nodes and calculates an even distribution of nodes between a number of graphs
	private void calculateGraphSizes(int totalNodes, int numberOfGraphs)
	{
		graphSizes = new int[numberOfGraphs];
		int stepSize = totalNodes / numberOfGraphs;

		for (int i = 0; i < numberOfGraphs; i++)
		{
			graphSizes[i] = (i + 1) * stepSize;
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
		dijkstra.updateNodes(nodes);
		dijkstra.run(nodes.get(rand.nextInt(numberOfNodes)));
		return dijkstra.getComparisons();
	}

	// Repeatedly runs Dijkstra's algorithm on different sized graphs
	public Map<Integer, Integer> calculateResults(int totalNumberOfNodes, int numberOfSteps)
	{
		Map<Integer, Integer> results = new LinkedHashMap<>();
		results.put(0,0);
		calculateGraphSizes(totalNumberOfNodes, numberOfSteps);

		for (int graphSize : graphSizes)
		{
			long startTime = System.nanoTime();

			generateGraph(graphSize);
			results.put(graphSize, getComparisons(graphSize));

			long timeTaken = (System.nanoTime() - startTime) / 1000000;
			String message = "Graph size: " + graphSize + ", Time: " + timeTaken + "ms";
			logger.info(message);
		}

		return results;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException
	{
		ComparisonChartData cd = new ComparisonChartData();

		Map<Integer, Integer> results = cd.calculateResults(100, 10);

		StringBuilder sb = new StringBuilder();
		sb.append("Number of nodes / Number of comparisons\n\n");

		for (Map.Entry<Integer, Integer> entry : results.entrySet())
		{
			sb.append(entry.getKey())
					.append(" / ")
					.append(entry.getValue())
					.append("\n");
		}

		String output = sb.toString();
		logger.info(output);
	}
}
