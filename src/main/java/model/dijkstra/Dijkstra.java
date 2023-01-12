package src.main.java.model.dijkstra;

import src.main.java.model.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dijkstra extends Graph
{
	private final NodeMap unvisitedNodes;
	private final NodeMap visitedNodes;
	private final ArrayList<String[]> steps = new ArrayList<>();
	private int stepSize;

	public Dijkstra(List<Node> nodes, List<Edge> edges)
	{
		super(nodes, edges);
		this.unvisitedNodes = new NodeMap(nodes);
		this.visitedNodes = new NodeMap();
		this.stepSize = nodes.size();
	}

	public void updateNodes(List<Node> nodes)
	{
		this.unvisitedNodes.update(nodes);
		this.visitedNodes.clear();
		this.stepSize = nodes.size();
	}

	// Dijkstra's Algorithm
	public List<String[]> run(Node startingNode)
	{
		if (!steps.isEmpty())
		{
			steps.clear();
		}

		// adds the node names, used as column headers, as the first String[] in steps
		instantiateSteps();

		// runs an initial pass on the nodes to set the L values
		findInitialLValues(startingNode);

		while (visitedNodes.size() != unvisitedNodes.size())
		{
			// adds the current L values, in unvisitedNodes, to steps as a String[]
			updateSteps();

			// finds the node with the lowest L value
			Node nextNode = findNodeWithSmallestLValue();

			// updates unvisitedNodes with the new L values and flags the node as visited
			findSubsequentLValues(nextNode);
		}

		// returns the final L values as a List<String[]>
		return steps;
	}

	private void instantiateSteps()
	{
		String[] step = new String[stepSize];
		int index = 0;

		for (Map.Entry<Node, Integer> entry : unvisitedNodes.entrySet())
		{
			step[index] = entry.getKey().getName();
			index++;
		}

		steps.add(step);
	}

	private void updateSteps()
	{
		String[] step = new String[stepSize];
		int index = 0;

		for (Map.Entry<Node, Integer> entry : unvisitedNodes.entrySet())
		{
			step[index] = entry.getValue().toString();
			index++;
		}

		steps.add(step);
	}

	private void findInitialLValues(Node startingNode)
	{
		// first pass of L values
		for (Node node : unvisitedNodes.keySet())
		{
			Edge edge = findEdge(startingNode, node);

			if (node.equals(startingNode))
			{
				unvisitedNodes.replace(node, 0);
			}
			else if (edge != null)
			{
				unvisitedNodes.replace(node, edge.getWeight());
			}
			else
			{
				// this is to be representative of infinity
				unvisitedNodes.replace(node, -1);
			}
		}

		flagNodeAsVisited(startingNode);
	}

	private void findSubsequentLValues(Node nextNode)
	{
		for (Map.Entry<Node, Integer> nodeAndLValue : unvisitedNodes.entrySet())
		{
			Edge edge = findEdge(nextNode, nodeAndLValue.getKey());

			if (edge != null)
			{
				int newLValue = unvisitedNodes.get(nextNode) + edge.getWeight();

				// if the edge exists and the weight of its path is smaller
				if (nodeAndLValue.getValue() > newLValue || nodeAndLValue.getValue() == -1)
				{
					// L value is updated to be the smaller path
					unvisitedNodes.replace(nodeAndLValue.getKey(), newLValue);
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

		LinkedList<Node> testNodes = new LinkedList<>(Arrays.asList(nodeC, nodeB, nodeA, nodeE, nodeD));

		LinkedList<Edge> testEdges = new LinkedList<>(Arrays.asList(
				new Edge(nodeA, nodeE, 2),
				new Edge(nodeE, nodeC, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(nodeC, nodeD, 3),
				new Edge(nodeB, nodeC, 4)
		));

		Dijkstra dijkstraAlgo = new Dijkstra(testNodes, testEdges);

		ArrayList<String[]> steps = (ArrayList<String[]>) dijkstraAlgo.run(nodeA);

		for (String[] step : steps)
		{
			System.out.println(Arrays.toString(step));
		}
	}
}
