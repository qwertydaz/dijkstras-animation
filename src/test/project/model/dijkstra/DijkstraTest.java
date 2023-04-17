package project.model.dijkstra;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DijkstraTest
{
	private Dijkstra d;
	private Node oneEdgeNode;
	private Node threeEdgeNode;
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
		oneEdgeNode = new Node("A");
		Node nodeB = new Node("B");
		threeEdgeNode = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		normalNodes = new LinkedList<>(Arrays.asList(threeEdgeNode, nodeB, oneEdgeNode, nodeE, nodeD));

		normalEdges = new LinkedList<>(Arrays.asList(
				new Edge(oneEdgeNode, nodeE, 2),
				new Edge(nodeE, threeEdgeNode, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(threeEdgeNode, nodeD, 3),
				new Edge(nodeB, threeEdgeNode, 4)
		));
	}

	@AfterEach
	void tearDown()
	{
		d = null;
		oneEdgeNode = null;
		threeEdgeNode = null;
		normalNodes = null;
		normalEdges = null;

		Node.resetId();
		Edge.resetId();
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
	void testRunNodeWithOneEdge()
	{
		Map<String[], String[]> expected = Map.of(
				new String[]{"Tv"}, new String[]{"A", "B", "C", "D", "E"},
				new String[]{"1"}, new String[]{"0", "-1", "-1", "-1", "2"},
				new String[]{"1", "5"}, new String[]{"0", "-1", "5", "9", "2"},
				new String[]{"1", "3", "5"}, new String[]{"0", "9", "5", "8", "2"},
				new String[]{"1", "3", "4", "5"}, new String[]{"0", "9", "5", "8", "2"},
				new String[]{"1", "2", "3", "4", "5"}, new String[]{"0", "9", "5", "8", "2"}
		);

		d = new Dijkstra(normalNodes, normalEdges);

		Map<String[], String[]> actual = d.run(oneEdgeNode);

		assertTrue(deepEquals(expected, actual),
				"run: Did not return the expected output");
	}

	@Test
	void testRunNodeWithThreeEdge()
	{
		Map<String[], String[]> expected = Map.of(
				new String[]{"Tv"}, new String[]{"A", "B", "C", "D", "E"},
				new String[]{"3"}, new String[]{"-1", "4", "0", "3", "3"},
				new String[]{"3", "4"}, new String[]{"-1", "4", "0", "3", "3"},
				new String[]{"3", "4", "5"}, new String[]{"5", "4", "0", "3", "3"},
				new String[]{"2", "3", "4", "5"}, new String[]{"5", "4", "0", "3", "3"},
				new String[]{"1", "2", "3", "4", "5"}, new String[]{"5", "4", "0", "3", "3"}
		);

		d = new Dijkstra(normalNodes, normalEdges);

		Map<String[], String[]> actual = d.run(threeEdgeNode);

		assertTrue(deepEquals(expected, actual),
				"run: Did not return the expected output");
	}

	@Test
	void testRunInvalidNode()
	{
		d = new Dijkstra(normalNodes, normalEdges);

		Node invalidNode = new Node("F");

		assertThrows(IllegalArgumentException.class, () -> d.run(invalidNode),
				"run: Did not throw the expected exception");
	}

	@Test
	void testRunNull()
	{
		d = new Dijkstra(normalNodes, normalEdges);

		assertThrows(IllegalArgumentException.class, () -> d.run(null),
				"run: Did not throw the expected exception");
	}

	@Test
	void testGetComparisonsCompleteGraph()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		List<Node> nodes = new LinkedList<>(Arrays.asList(nodeA, nodeB, nodeC, nodeD, nodeE));

		List<Edge> edges = new LinkedList<>(Arrays.asList(
				new Edge(nodeA, nodeB, 2),
				new Edge(nodeA, nodeC, 3),
				new Edge(nodeA, nodeD, 4),
				new Edge(nodeA, nodeE, 2),
				new Edge(nodeB, nodeC, 5),
				new Edge(nodeB, nodeD, 6),
				new Edge(nodeB, nodeE, 7),
				new Edge(nodeC, nodeD, 2),
				new Edge(nodeC, nodeE, 4),
				new Edge(nodeD, nodeE, 2)
		));

		Map<String[], String[]> expected = Map.of(
				new String[]{"Tv"}, new String[]{"A", "B", "C", "D", "E"},
				new String[]{"6"}, new String[]{"0", "2", "3", "4", "2"},
				new String[]{"6", "7"}, new String[]{"0", "2", "3", "4", "2"},
				new String[]{"6", "7", "10"}, new String[]{"0", "2", "3", "4", "2"},
				new String[]{"6", "7", "8", "10"}, new String[]{"0", "2", "3", "4", "2"},
				new String[]{"6", "7", "8", "9", "10"}, new String[]{"0", "2", "3", "4", "2"}
		);

		d = new Dijkstra(nodes, edges);

		Map<String[], String[]> actual = d.run(nodeA);

		assertTrue(deepEquals(expected, actual),
				"getComparisons: Did not return the expected output");
	}

	@Test
	void testGetComparisonsLinearGraph()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		List<Node> nodes = new LinkedList<>(Arrays.asList(nodeA, nodeB, nodeC, nodeD, nodeE));

		List<Edge> edges = new LinkedList<>(Arrays.asList(
				new Edge(nodeA, nodeB, 2),
				new Edge(nodeB, nodeC, 3),
				new Edge(nodeC, nodeD, 4),
				new Edge(nodeD, nodeE, 5)
		));

		Map<String[], String[]> expected = Map.of(
				new String[]{"Tv"}, new String[]{"A", "B", "C", "D", "E"},
				new String[]{"6"}, new String[]{"0", "2", "-1", "-1", "-1"},
				new String[]{"6", "7"}, new String[]{"0", "2", "5", "-1", "-1"},
				new String[]{"6", "7", "8"}, new String[]{"0", "2", "5", "9", "-1"},
				new String[]{"6", "7", "8", "9"}, new String[]{"0", "2", "5", "9", "14"},
				new String[]{"6", "7", "8", "9", "10"}, new String[]{"0", "2", "5", "9", "14"}
		);

		d = new Dijkstra(nodes, edges);

		Map<String[], String[]> actual = d.run(nodeA);

		assertTrue(deepEquals(expected, actual),
				"getComparisons: Did not return the expected output");
	}
}
