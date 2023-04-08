package project.model.dijkstra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DijkstraTest
{
	private Dijkstra d;
	private LinkedList<Node> normalNodes;
	private LinkedList<Edge> normalEdges;

	@BeforeEach
	void setUp()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		normalNodes = new LinkedList<>(Arrays.asList(nodeC, nodeB, nodeA, nodeE, nodeD));

		normalEdges = new LinkedList<>(Arrays.asList(
				new Edge(nodeA, nodeE, 2),
				new Edge(nodeE, nodeC, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(nodeC, nodeD, 3),
				new Edge(nodeB, nodeC, 4)
		));

		d = new Dijkstra(normalNodes, normalEdges);
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

		d.updateNodes(nodes);

		assertEquals(nodes, new LinkedList<>(d.getUnvisitedNodes().getNodes()),
				"updateNodes: Did not return the expected output");
	}
}
