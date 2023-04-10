package project.model.dijkstra;

import project.model.Graph;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dijkstra extends Graph
{
	private final NodeMap unvisitedNodes;
	private final NodeMap visitedNodes;
	private final Map<String[], String[]> steps = new LinkedHashMap<>();
	private int numOfNodes;

	public Dijkstra(List<Node> nodes, List<Edge> edges)
	{
		super(nodes, edges);
		this.unvisitedNodes = new NodeMap(nodes);
		this.visitedNodes = new NodeMap();
		this.numOfNodes = nodes.size();
	}

	public NodeMap getUnvisitedNodes()
	{
		return unvisitedNodes;
	}

	public void updateNodes(List<Node> nodes)
	{
		this.unvisitedNodes.update(nodes);
		this.visitedNodes.clear();
		this.numOfNodes = nodes.size();
	}

	// Dijkstra's Algorithm
	public Map<String[], String[]> run(Node startingNode)
	{
		if (!steps.isEmpty())
		{
			steps.clear();
		}

		// adds the node names, used as column headers, as the first String[] in steps
		instantiateSteps();

		// runs an initial pass on the nodes to set the L values
		findInitialLValues(startingNode);

		while (visitedNodes.size() < unvisitedNodes.size())
		{
			// adds the current L values, in unvisitedNodes, to steps as a String[]
			updateSteps();

			// finds the node with the lowest L value
			Node nextNode = findNodeWithSmallestLValue();

			// updates unvisitedNodes with the new L values and flags the node as visited
			findSubsequentLValues(nextNode);
		}

		updateSteps();

		// returns the final L values as a List<String[]>
		return steps;
	}

	private void instantiateSteps()
	{
		int index = 0;

		String[] nodeNames = new String[numOfNodes];

		for (Node node : unvisitedNodes.getNodes())
		{
			nodeNames[index] = node.getName();
			index++;
		}

		steps.put(new String[]{"Tv"}, nodeNames);
	}

	private void updateSteps()
	{
		int index = 0;

		String[] nodeIds = new String[visitedNodes.size()];
		String[] lValues = new String[numOfNodes];

		for (Node node : visitedNodes.getNodes())
		{
			nodeIds[index] = node.getName();
			index++;
		}

		index = 0;

		for (Node node : unvisitedNodes.getNodes())
		{
			lValues[index] = unvisitedNodes.getLValueByNodeId(node.getId()).toString();
			index++;
		}

		steps.put(nodeIds, lValues);
	}

	private void findInitialLValues(Node startingNode)
	{
		// first pass of L values
		for (Node node : unvisitedNodes.getNodes())
		{
			Edge edge = findEdge(startingNode, node);

			if (node.equals(startingNode))
			{
				unvisitedNodes.setLValueByNodeId(node.getId(), 0);
			}
			else if (edge != null)
			{
				unvisitedNodes.setLValueByNodeId(node.getId(), edge.getWeight());
			}
			else
			{
				// this is to be representative of infinity
				unvisitedNodes.setLValueByNodeId(node.getId(), -1);
			}
		}

		flagNodeAsVisited(startingNode);
	}

	private void findSubsequentLValues(Node nextNode)
	{
		for (Node node : unvisitedNodes.getNodes())
		{
			Edge edge = findEdge(nextNode, node);

			if (edge != null)
			{
				int oldLValue = unvisitedNodes.getLValueByNodeId(node.getId());
				int newLValue = unvisitedNodes.getLValueByNodeId(nextNode.getId()) + edge.getWeight();

				// if the edge exists and the weight of its path is smaller
				if (oldLValue > newLValue || oldLValue == -1)
				{
					// L value is updated to be the smaller path
					unvisitedNodes.setLValueByNodeId(node.getId(), newLValue);
				}
			}
		}

		flagNodeAsVisited(nextNode);
	}

	private void flagNodeAsVisited(Node node)
	{
		int lValue = unvisitedNodes.getLValueByNodeId(node.getId());
		visitedNodes.setLValueByNodeId(node.getId(), lValue);

		visitedNodes.addNode(node);
	}

	private Node findNodeWithSmallestLValue()
	{
		int smallestLValue = Integer.MAX_VALUE;

		Node node = null;
		int lValue;

		for (Node currentNode : unvisitedNodes.getNodes())
		{
			if (visitedNodes.contains(currentNode))
			{
				continue; // skip nodes that have already been visited
			}

			lValue = unvisitedNodes.getLValueByNodeId(currentNode.getId());

			if (lValue > 0 && lValue < smallestLValue)
			{
				smallestLValue = lValue;
				node = currentNode;
			}
		}

		// just for debugging, node should never be null
		if (node == null)
		{
			throw new RuntimeException("No node with smallest L value found");
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

		Map<String[], String[]> steps = dijkstraAlgo.run(nodeA);

		for (Map.Entry<String[], String[]> entry : steps.entrySet())
		{
			System.out.println(Arrays.toString(entry.getKey()) + " : " + Arrays.toString(entry.getValue()));
		}
	}
}
