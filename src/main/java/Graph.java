package src.main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static src.main.java.GraphUtil.edgeExists;
import static src.main.java.GraphUtil.retrieveWeight;
import static src.main.java.GraphUtil.toSetArray;

public class Graph
{
	Set<String>[] edges;
	Set<String>[][] weights;
	Map<String, Integer> remainingVertices;
	Map<String, Integer> treeVertices;
	int vertexCount;

	public Graph(Set<String>[] edges, Set<String>[][] weights, Map<String, Integer> remainingVertices, Map<String, Integer> treeVertices)
	{
		this.edges = edges;
		this.weights = weights;
		this.remainingVertices = remainingVertices;
		this.treeVertices = treeVertices;
		this.vertexCount = remainingVertices.size();
	}

	// Dijkstra's Algorithm
	public Map<String, Integer> findShortestPaths(String node)
	{
		// first pass of L values
		for (String vertex : remainingVertices.keySet())
		{
			if (vertex.equals(node))
			{
				remainingVertices.replace(vertex, 0);
			}
			else if (edgeExists(new String[]{node, vertex}, edges))
			{
				int weight = retrieveWeight(new String[]{node, vertex}, weights);
				remainingVertices.replace(vertex, weight);
			}
			else
			{
				// this is to be representative of infinity
				remainingVertices.replace(vertex, -1);
			}
		}

		addVertexToTreeVertices(node);

		// next passes of L values
		while (treeVertices.size() != vertexCount)
		{
			// TODO: finish translating pseudocode
		}

		return treeVertices;
	}

	private ArrayList<String> addVertexToTv(ArrayList<String> treeVertices, String[] vertices, int[] lValues)
	{
		// TODO: cycle through the vertices by smallest L value

		for (int i = 0; i < vertices.length; i++)
		{

		}

		return treeVertices;
	}

	private void addVertexToTreeVertices(String node)
	{
		treeVertices.replace(node, remainingVertices.get(node));
		remainingVertices.remove(node);
	}

	// check if a vertex's final weight has been added to treeVertices
	private boolean isNodeInTreeVertices(String node, Map<String, Integer> treeVertices)
	{
		return treeVertices.get(node) != null;
	}

	// TODO: needs reworked to work with new HashMaps
	private int indexOfSmallestNumber(int[] lValues, ArrayList<String> treeVertices)
	{
		int index = -1;
		int smallestNum = Integer.MAX_VALUE;

		for (int i = 0; i < lValues.length; i++)
		{
			if (lValues[i] > 0 && lValues[i] < smallestNum)
			{
				smallestNum = lValues[i];
				index = i;
			}
		}

		return index;
	}

	public static void main(String[] args)
	{
//		Set<Set<String>> edges = new HashSet<>(Arrays.asList(
//				new HashSet<>(Arrays.asList("A", "E")),
//				new HashSet<>(Arrays.asList("E", "C")),
//				new HashSet<>(Arrays.asList("E", "D")),
//				new HashSet<>(Arrays.asList("C", "D")),
//				new HashSet<>(Arrays.asList("B", "C"))
//		));

		// test input
		String node = "A";
		Set<String>[] edges = toSetArray(new String[][]{{"A", "E"}, {"E", "C"}, {"E", "D"}, {"C", "D"}, {"B", "C"}});
		Set<String>[][] weights = toSetArray(new String[][][]{
				{{"A", "E"}, {"2"}},
				{{"E", "C"}, {"3"}},
				{{"E", "D"}, {"7"}},
				{{"C", "D"}, {"3"}},
				{{"B", "C"}, {"4"}}
		});
		HashMap<String, Integer> remainingVertices = new HashMap<>();
		remainingVertices.put("A", null);
		remainingVertices.put("B", null);
		remainingVertices.put("C", null);
		remainingVertices.put("D", null);
		remainingVertices.put("E", null);
		HashMap<String, Integer> treeVertices = new HashMap<>(remainingVertices);

		Graph graph = new Graph(edges, weights, remainingVertices, treeVertices);

		// shortest paths from node
		HashMap<String, Integer> shortestPaths = (HashMap<String, Integer>) graph.findShortestPaths(node);

		// test output
		System.out.println(shortestPaths.toString());
	}
}
