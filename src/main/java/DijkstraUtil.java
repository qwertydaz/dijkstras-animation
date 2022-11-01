package src.main.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DijkstraUtil
{
	public static Set<String>[] toSetArray(String[][] nestedArray)
	{
		Set<String>[] setArray = new Set[nestedArray.length];

		for (int i = 0; i < nestedArray.length; i++)
		{
			setArray[i] = new HashSet<>(Arrays.asList(nestedArray[i]));
		}

		return setArray;
	}

	public static Set<String>[][] toSetArray(String[][][] nestedArray)
	{
		Set<String>[][] setArray = new Set[nestedArray.length][];

		for (int i = 0; i < nestedArray.length; i++)
		{
			setArray[i] = toSetArray(nestedArray[i]);
		}

		return setArray;
	}

	public static boolean edgeExists(String[] potentialEdge, Set<String>[] edges)
	{
		Set<String> potentialEdgeSet = new HashSet<>(Arrays.asList(potentialEdge));

		for (Set<String> edge : edges)
		{
			if (edge.equals(potentialEdgeSet))
			{
				return true;
			}
		}

		return false;
	}

	public static int retrieveWeight(String[] targetEdge, Set<String>[][] weights)
	{
		Set<String> targetEdgeSet = new HashSet<>(Arrays.asList(targetEdge));

		for (Set<String>[] edgeWeightPair : weights)
		{
			for (Set<String> edge : edgeWeightPair)
			{
				if (edge.equals(targetEdgeSet))
				{
					// returns the weight as an integer
					return Integer.parseInt(edgeWeightPair[1].iterator().next());
				}
			}
		}

		// returns -1 if targetEdge is not contained in the set of weights
		return -1;
	}
}
