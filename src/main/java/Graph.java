package src.main.java;

import java.util.Map;
import java.util.Set;

public abstract class Graph
{
	Set<String>[] edges;
	Set<String>[][] weights;
	Map<String, Integer> remainingVertices;
	Map<String, Integer> treeVertices;

	protected Graph(Set<String>[] edges, Set<String>[][] weights, Map<String, Integer> remainingVertices, Map<String, Integer> treeVertices)
	{
		this.edges = edges;
		this.weights = weights;
		this.remainingVertices = remainingVertices;
		this.treeVertices = treeVertices;
	}
}
