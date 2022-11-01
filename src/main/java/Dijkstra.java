package src.main.java;

import java.util.Arrays;
import java.util.Set;

import static src.main.java.DijkstraUtil.edgeExists;
import static src.main.java.DijkstraUtil.retrieveWeight;
import static src.main.java.DijkstraUtil.toSetArray;

public class Dijkstra
{
	public static int[] dijkstraAlgo(String node, String[] vertices, Set<String>[] edges, Set<String>[][] weights)
	{
		int[] lValues = new int[vertices.length];
		String[] treeVertices = new String[vertices.length];

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
		}

		// next passes of L values
		while (!Arrays.equals(treeVertices, vertices))
		{


			Arrays.sort(treeVertices);
		}

		return lValues;
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

		// shortest paths from node
		int[] lValues = dijkstraAlgo(node, vertices, edges, weights);

		// test output
		System.out.println(Arrays.toString(lValues));
	}
}
