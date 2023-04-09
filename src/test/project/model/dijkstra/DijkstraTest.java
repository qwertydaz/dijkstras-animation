package project.model.dijkstra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DijkstraTest
{
	private Dijkstra d;
	private Node startingNode;
	private LinkedList<Node> normalNodes;
	private LinkedList<Edge> normalEdges;

	private String formatResult(List<String[]> list)
	{
		StringBuilder result = new StringBuilder();
		result.append('[');

		for (int i = 0; i < list.size(); i++)
		{
			String[] array = list.get(i);
			result.append('[');

			for (int j = 0; j < array.length; j++)
			{
				result.append('"').append(array[j]).append('"');

				if (j < array.length - 1)
				{
					result.append(", ");
				}
			}

			result.append(']');

			if (i < list.size() - 1)
			{
				result.append(", ");
			}
		}

		result.append(']');

		return result.toString();
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
		String expected = "[[\"Tv\", \"A\", \"B\", \"C\", \"D\", \"E\"], [\"[A]\", \"0\", \"-1\", \"-1\", \"-1\", \"2\"], [\"[A, E]\", \"0\", \"-1\", \"5\", \"9\", \"2\"], [\"[A, C, E]\", \"0\", \"9\", \"5\", \"8\", \"2\"], [\"[A, C, D, E]\", \"0\", \"9\", \"5\", \"8\", \"2\"], [\"[A, B, C, D, E]\", \"0\", \"9\", \"5\", \"8\", \"2\"]]";
		d = new Dijkstra(normalNodes, normalEdges);

		assertEquals(expected, formatResult(d.run(startingNode)),
				"run: Did not return the expected output");
	}
}
