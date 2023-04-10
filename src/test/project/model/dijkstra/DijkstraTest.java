package project.model.dijkstra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DijkstraTest
{
	private Dijkstra d;
	private Node startingNode;
	private LinkedList<Node> normalNodes;
	private LinkedList<Edge> normalEdges;

	private boolean deepEquals(Map<String[], String[]> expectedSteps, Map<String[], String[]> actualSteps)
	{
		if (expectedSteps == actualSteps)
		{
			return true;
		}

		if (expectedSteps.size() != actualSteps.size())
		{
			return false;
		}

		for (Map.Entry<String[], String[]> expectedEntry : expectedSteps.entrySet())
		{
			boolean found = false;

			for (Map.Entry<String[], String[]> actualEntry : actualSteps.entrySet())
			{
				if (Arrays.deepEquals(expectedEntry.getKey(), actualEntry.getKey()) &&
						Arrays.deepEquals(expectedEntry.getValue(), actualEntry.getValue()))
				{
					found = true;
					break;
				}
			}

			if (!found)
			{
				return false;
			}
		}

		return true;
	}

	@BeforeEach
	void setUp()
	{
		startingNode = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		normalNodes = new LinkedList<>(Arrays.asList(nodeC, nodeB, startingNode, nodeE, nodeD));

		normalEdges = new LinkedList<>(Arrays.asList(
				new Edge(startingNode, nodeE, 2),
				new Edge(nodeE, nodeC, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(nodeC, nodeD, 3),
				new Edge(nodeB, nodeC, 4)
		));
	}

	@Test
	void testUpdateNodesSameData()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		LinkedList<Node> nodes = new LinkedList<>(Arrays.asList(nodeC, nodeB, nodeA, nodeE, nodeD));
		Collections.sort(nodes);

		d = new Dijkstra(normalNodes, normalEdges);
		d.updateNodes(nodes);

		assertEquals(nodes, new LinkedList<>(d.getUnvisitedNodes().getNodes()),
				"updateNodes: Did not return the expected output");
	}

	@Test
	void testUpdateNodesDifferentData()
	{
		Node nodeF = new Node("F");
		Node nodeG = new Node("G");
		Node nodeH = new Node("H");
		Node nodeI = new Node("I");
		Node nodeJ = new Node("J");

		LinkedList<Node> nodes = new LinkedList<>(Arrays.asList(nodeH, nodeG, nodeF, nodeJ, nodeI));
		Collections.sort(nodes);

		d = new Dijkstra(normalNodes, normalEdges);
		d.updateNodes(nodes);

		assertEquals(nodes, new LinkedList<>(d.getUnvisitedNodes().getNodes()),
				"updateNodes: Did not return the expected output");
	}

	@Test
	void testRunNormalInput()
	{
		Map<String[], String[]> expected = Map.of(
				new String[]{"Tv"}, new String[]{"A", "B", "C", "D", "E"},
				new String[]{"A"}, new String[]{"0", "-1", "-1", "-1", "2"},
				new String[]{"A", "E"}, new String[]{"0", "-1", "5", "9", "2"},
				new String[]{"A", "C", "E"}, new String[]{"0", "9", "5", "8", "2"},
				new String[]{"A", "C", "D", "E"}, new String[]{"0", "9", "5", "8", "2"},
				new String[]{"A", "B", "C", "D", "E"}, new String[]{"0", "9", "5", "8", "2"}
		);

		d = new Dijkstra(normalNodes, normalEdges);

		assertTrue(deepEquals(expected, d.run(startingNode)),
				"run: Did not return the expected output");
	}
}
