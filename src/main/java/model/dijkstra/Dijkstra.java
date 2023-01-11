package src.main.java.model.dijkstra;

import src.main.java.exception.WeightNotFoundException;
import src.main.java.model.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dijkstra extends Graph
{
	private final NodeMap unvisitedNodes;
	private final NodeMap visitedNodes;
	private final ArrayList<ArrayList<String>> steps = new ArrayList<>();

	public Dijkstra(List<Node> nodes, List<Edge> edges)
	{
		super(nodes, edges);
		this.unvisitedNodes = new NodeMap(nodes);
		this.visitedNodes = new NodeMap();
	}

	public void updateNodes(List<Node> nodes)
	{
		this.unvisitedNodes.update(nodes);
		this.visitedNodes.clear();
	}

	// Dijkstra's Algorithm
	public List<ArrayList<String>> run(Node startingNode)
	{
		try
		{
			instantiateSteps();
			findInitialLValues(startingNode);

			while (visitedNodes.size() != unvisitedNodes.size())
			{
				updateSteps();
				Node nextNode = findNodeWithSmallestLValue();
				findSubsequentLValues(nextNode);
			}

			return steps;
		}
		catch (WeightNotFoundException e)
		{
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	private void instantiateSteps()
	{
		ArrayList<String> step = new ArrayList<>();

		for (Map.Entry<Node, Integer> entry : unvisitedNodes.entrySet())
		{
			step.add(entry.getKey().getName());
		}

		steps.add(step);
	}

	private void updateSteps()
	{
		ArrayList<String> step = new ArrayList<>();

		for (Map.Entry<Node, Integer> entry : unvisitedNodes.entrySet())
		{
			step.add(String.valueOf(entry.getValue()));
		}

		steps.add(step);
	}

	private void findInitialLValues(Node startingNode) throws WeightNotFoundException
	{
		// first pass of L values
		for (Node node : unvisitedNodes.keySet())
		{
			Edge potentialEdge = new Edge(startingNode, node);

			if (node.equals(startingNode))
			{
				unvisitedNodes.replace(node, 0);
			}
			else if (edgeExists(potentialEdge))
			{
				unvisitedNodes.replace(node, findWeight(potentialEdge));
			}
			else
			{
				// this is to be representative of infinity
				unvisitedNodes.replace(node, -1);
			}
		}

		flagNodeAsVisited(startingNode);
	}

	private void findSubsequentLValues(Node nextNode) throws WeightNotFoundException
	{
		for (Map.Entry<Node, Integer> nodeAndLValue : unvisitedNodes.entrySet())
		{
			Edge potentialEdge = new Edge(nextNode, nodeAndLValue.getKey());
			if (edgeExists(potentialEdge))
			{
				int potentialNewLValue = unvisitedNodes.get(nextNode) + findWeight(potentialEdge);

				// if the edge exists and the weight of its path is smaller
				if (nodeAndLValue.getValue() > potentialNewLValue || nodeAndLValue.getValue() == -1)
				{
					// L value is updated to be the smaller path
					unvisitedNodes.replace(nodeAndLValue.getKey(), potentialNewLValue);
				}
			}
		}

		flagNodeAsVisited(nextNode);
	}

	private void flagNodeAsVisited(Node node)
	{
		int lValue = unvisitedNodes.get(node);
		visitedNodes.put(node, lValue);
	}

	private Node findNodeWithSmallestLValue()
	{
		int smallestLValue = Integer.MAX_VALUE;

		Node node = new Node();
		int lValue;

		for (Map.Entry<Node, Integer> nodeAndLValue : unvisitedNodes.entrySet())
		{
			Node currentNode = nodeAndLValue.getKey();
			if (visitedNodes.containsKey(currentNode))
			{
				continue; // skip nodes that have already been visited
			}

			if (nodeAndLValue.getValue() != null)
			{
				lValue = nodeAndLValue.getValue();

				if (lValue > 0 && lValue < smallestLValue)
				{
					smallestLValue = lValue;
					node = currentNode;
				}
			}
		}
		return node;
	}

	public static void main(String[] args)
	{
		// test input
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		LinkedList<Node> nodes = new LinkedList<>(Arrays.asList(nodeA, nodeB, nodeC, nodeD, nodeE));

		LinkedList<Edge> edges = new LinkedList<>(Arrays.asList(
				new Edge(nodeA, nodeE, 2),
				new Edge(nodeE, nodeC, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(nodeC, nodeD, 3),
				new Edge(nodeB, nodeC, 4)
		));

		Dijkstra dijkstraAlgo = new Dijkstra(nodes, edges);

		ArrayList<ArrayList<String>> steps = (ArrayList<ArrayList<String>>) dijkstraAlgo.run(nodeA);

		for (ArrayList<String> step : steps)
		{
			System.out.println(step);
		}
	}
}
