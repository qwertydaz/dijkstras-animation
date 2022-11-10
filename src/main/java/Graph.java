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

	// TODO: finish translating pseudocode
	// Dijkstra's Algorithm
	public Map<String, Integer> findShortestPaths(String startingVertex)
	{
		// derive the first set of L values and move the starting vertex to treeVertices
		findInitialLValues(startingVertex);

		// next passes of L values
		while (treeVertices.size() != vertexCount)
		{
			String nextVertex = findVertexWithSmallestLValue();

			updateLValues(nextVertex);
		}

		return treeVertices;
	}

	private void findInitialLValues(String startingVertex)
	{
		// first pass of L values
		for (String vertex : remainingVertices.keySet())
		{
			if (vertex.equals(startingVertex))
			{
				remainingVertices.replace(vertex, 0);
			}
			else if (edgeExists(new String[]{startingVertex, vertex}, edges))
			{
				int weight = retrieveWeight(new String[]{startingVertex, vertex}, weights);
				remainingVertices.replace(vertex, weight);
			}
			else
			{
				// this is to be representative of infinity
				remainingVertices.replace(vertex, -1);
			}
		}

		addVertexToTreeVertices(startingVertex);
	}

	private void updateLValues(String nextVertex)
	{
		for (Map.Entry<String, Integer> verticesAndLValues : remainingVertices.entrySet())
		{
			String[] potentialEdge = new String[]{nextVertex, verticesAndLValues.getKey()};
			int potentialNewLValue = remainingVertices.get(nextVertex) + retrieveWeight(potentialEdge, weights);

			// if the edge exists and the weight of its path is smaller
			if (edgeExists(potentialEdge, edges) && verticesAndLValues.getValue() > potentialNewLValue)
			{
				// L value is updated to be the smaller path
				remainingVertices.replace(verticesAndLValues.getKey(), potentialNewLValue);
			}
		}

		addVertexToTreeVertices(nextVertex);
	}

	private void addVertexToTreeVertices(String vertex)
	{
		treeVertices.replace(vertex, remainingVertices.get(vertex));
		remainingVertices.remove(vertex);
	}

	private String findVertexWithSmallestLValue()
	{
		int smallestLValue = Integer.MAX_VALUE;

		String vertex = "";
		int lValue;

		//Map<String, Integer> targetVertexAndLValue = new HashMap<>();

		for (Map.Entry<String, Integer> verticesAndLValues : remainingVertices.entrySet())
		{
			if (verticesAndLValues.getValue() != null)
			{
				lValue = verticesAndLValues.getValue();

				if (lValue > 0 && lValue < smallestLValue)
				{
					smallestLValue = lValue;
					vertex = verticesAndLValues.getKey();
				}
			}
		}

		//targetVertexAndLValue.put(vertex, smallestLValue);

		return vertex;
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
		String startingVertex = "A";
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

		// shortest paths from startingVertex
		HashMap<String, Integer> shortestPaths = (HashMap<String, Integer>) graph.findShortestPaths(startingVertex);

		// TODO: output needs to reflect every step in the process, not just the end result
		// test output
		System.out.println(shortestPaths.toString());
	}
}
