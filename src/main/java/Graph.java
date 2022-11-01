package src.main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static src.main.java.GraphUtil.edgeExists;
import static src.main.java.GraphUtil.retrieveWeight;
import static src.main.java.GraphUtil.toSetArray;

public class Graph
{
	String[] vertices;
	Set<String>[] edges;
	Set<String>[][] weights;

	public Graph(String[] vertices, Set<String>[] edges, Set<String>[][] weights)
	{
		this.vertices = vertices;
		this.edges = edges;
		this.weights = weights;
	}

	// Dijkstra's Algorithm
	public int[] findShortestPaths(String node)
	{
		int[] lValues = new int[vertices.length];
		ArrayList<String> treeVertices = new ArrayList<>();

		// first pass of L values
		for (int i = 0; i < vertices.length; i++)
		{
			if (vertices[i].equals(node))
			{
				lValues[i] = 0;
			}
			else if (edgeExists(new String[]{node, vertices[i]}, edges))
			{
				lValues[i] = retrieveWeight(new String[]{node, vertices[i]}, weights);
			}
			else
			{
				// this is to be representative of infinity
				lValues[i] = -1;
			}
		}

		treeVertices.add(node);

		// next passes of L values
		while (treeVertices.size() != vertices.length)
		{
			// TODO: finish translating pseudocode
		}

		return lValues;
	}

	private ArrayList<String> addVertexToTv(ArrayList<String> treeVertices, String[] vertices, int[] lValues)
	{
		// TODO: cycle through the vertices by smallest L value
	}

	private boolean isVertexInTv(int index, ArrayList<String> treeVertices)
	{
		String vertex = vertices[index];

		return treeVertices.contains(vertex);
	}

	private int indexOfSmallestNumber(int[] intArray, ArrayList<String> treeVertices)
	{
		int index = -1;
		int smallestNum = Integer.MAX_VALUE;

		for (int i = 0; i < intArray.length; i++)
		{
			if (intArray[i] > 0 && intArray[i] < smallestNum)
			{
				smallestNum = intArray[i];
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
		String[] vertices = new String[]{"A", "B", "C", "D", "E"};
		Set<String>[] edges = toSetArray(new String[][]{{"A", "E"}, {"E", "C"}, {"E", "D"}, {"C", "D"}, {"B", "C"}});
		Set<String>[][] weights = toSetArray(new String[][][]{
				{{"A", "E"}, {"2"}},
				{{"E", "C"}, {"3"}},
				{{"E", "D"}, {"7"}},
				{{"C", "D"}, {"3"}},
				{{"B", "C"}, {"4"}}
		});

		Graph graph = new Graph(vertices, edges, weights);

		// shortest paths from node
		int[] lValues = graph.findShortestPaths(node);

		// test output
		System.out.println(Arrays.toString(lValues));
	}
}
