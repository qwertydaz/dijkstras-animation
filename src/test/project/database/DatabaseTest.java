package project.database;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.model.dijkstra.Dijkstra;
import project.model.dijkstra.Edge;
import project.model.dijkstra.Node;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseTest
{
	private Database db;

	private Circle nodeShape1;
	private Circle nodeShape2;
	private Circle nodeShape3;
	private Circle nodeShape4;
	private Text nodeLabel1;
	private Text nodeLabel2;
	private Text nodeLabel3;
	private Text nodeLabel4;

	private Line edgeShape1;
	private Line edgeShape2;
	private Line edgeShape3;
	private Line edgeShape4;
	private Text edgeLabel1;
	private Text edgeLabel2;
	private Text edgeLabel3;
	private Text edgeLabel4;

	@BeforeEach
	void setUp()
	{
		db = new Database();

		nodeShape1 = new Circle();
		nodeShape2 = new Circle();
		nodeShape3 = new Circle();
		nodeShape4 = new Circle();
		nodeLabel1 = new Text("Node 1");
		nodeLabel2 = new Text("Node 2");
		nodeLabel3 = new Text("Node 3");
		nodeLabel4 = new Text("Node 4");

		edgeShape1 = new Line();
		edgeShape2 = new Line();
		edgeShape3 = new Line();
		edgeShape4 = new Line();
		edgeLabel1 = new Text("1");
		edgeLabel2 = new Text("2");
		edgeLabel3 = new Text("3");
		edgeLabel4 = new Text("4");

		db.addNode(nodeLabel1, nodeShape1);
		db.addNode(nodeLabel2, nodeShape2);
		db.addNode(nodeLabel3, nodeShape3);
		db.addNode(nodeLabel4, nodeShape4);

		db.addEdge(nodeShape1, nodeShape2, edgeLabel1, edgeShape1);
		db.addEdge(nodeShape1, nodeShape3, edgeLabel2, edgeShape2);
		db.addEdge(nodeShape2, nodeShape3, edgeLabel3, edgeShape3);
		db.addEdge(nodeShape3, nodeShape4, edgeLabel4, edgeShape4);
	}

	@AfterEach
	void tearDown()
	{
		Node.resetId();
		Edge.resetId();
		db.clear();

		db = null;

		nodeShape1 = null;
		nodeShape2 = null;
		nodeShape3 = null;
		nodeShape4 = null;
		nodeLabel1 = null;
		nodeLabel2 = null;
		nodeLabel3 = null;
		nodeLabel4 = null;

		edgeShape1 = null;
		edgeShape2 = null;
		edgeShape3 = null;
		edgeShape4 = null;
		edgeLabel1 = null;
		edgeLabel2 = null;
		edgeLabel3 = null;
		edgeLabel4 = null;
	}

	@Test
	void testGetAdjacentNodesAndEdgesValidInput()
	{
		Map<Circle, Line> expected = Map.of(nodeShape2, edgeShape1, nodeShape3, edgeShape2);
		Map<Circle, Line> actual = db.getAdjacentNodesAndEdges(nodeShape1);

		assertEquals(expected, actual, "getAdjacentNodesAndEdges: Valid input");
	}

	@Test
	void testGetAdjacentNodesAndEdgesInvalidInput()
	{
		Map<Circle, Line> expected = Map.of();
		Map<Circle, Line> actual = db.getAdjacentNodesAndEdges(new Circle());

		assertEquals(expected, actual, "getAdjacentNodesAndEdges: Invalid input");
	}

	@Test
	void testGetAdjacentNodesAndEdgesNullInput()
	{
		Map<Circle, Line> expected = Map.of();
		Map<Circle, Line> actual = db.getAdjacentNodesAndEdges(null);

		assertEquals(expected, actual, "getAdjacentNodesAndEdges: Null input");
	}

	@Test
	void testAddNodeValidInput()
	{
		Circle nodeShape = new Circle();
		Text nodeLabel = new Text("Node 5");

		db.addNode(nodeLabel, nodeShape);

		int actual = db.getNodes().size();

		assertEquals(5, actual, "addNode: Valid input");
	}

	@Test
	void testAddNodeInvalidInput()
	{
		db.addNode(null, null);

		int actual = db.getNodes().size();

		assertEquals(5, actual, "addNode: Invalid input");
	}

	@Test
	void testAddEdgeValidInput()
	{
		db.addEdge(nodeShape1, nodeShape4, new Text("5"), new Line());

		int actual = db.getEdges().size();

		assertEquals(5, actual, "addEdge: Valid input");
	}

	@Test
	void testAddEdgeInvalidInput()
	{
		db.addEdge(null, null, null, null);

		List<Line> expected = Arrays.asList(edgeShape1, edgeShape2, edgeShape3, edgeShape4);
		List<Line> actual = db.getEdges();

		assertEquals(expected, actual, "addEdge: Invalid input");
	}

	@Test
	void testRemoveNodeValidInput()
	{
		db.removeNode(nodeShape1);

		int actual = db.getNodes().size();

		assertEquals(4, actual, "removeNode: Valid input");
	}

	@Test
	void testRemoveNodeInvalidInput()
	{
		db.removeNode(new Circle());

		List<Circle> expected = Arrays.asList(nodeShape1, nodeShape2, nodeShape3, nodeShape4);
		List<Circle> actual = db.getNodes();

		assertEquals(expected, actual, "removeNode: Invalid input");
	}

	@Test
	void testRemoveEdgeValidInput()
	{
		db.removeEdge(edgeShape1);

		int actual = db.getEdges().size();

		assertEquals(4, actual, "removeEdge: Valid input");
	}

	@Test
	void testRemoveEdgeInvalidInput()
	{
		db.removeEdge(new Line());

		List<Line> expected = Arrays.asList(edgeShape1, edgeShape2, edgeShape3, edgeShape4);
		List<Line> actual = db.getEdges();

		assertEquals(expected, actual, "removeEdge: Invalid input");
	}

	@Test
	void testFindLabelValidCircle()
	{
		Text expected = nodeLabel1;
		Text actual = db.findLabel(nodeShape1);

		assertEquals(expected, actual, "findLabel: Valid input");
	}

	@Test
	void testFindLabelInvalidCircle()
	{
		Text actual = db.findLabel(new Circle());

		assertNull(actual, "findLabel: Invalid input");
	}

	@Test
	void testFindLabelValidLine()
	{
		Text expected = edgeLabel1;
		Text actual = db.findLabel(edgeShape1);

		assertEquals(expected, actual, "findLabel: Valid input");
	}

	@Test
	void testFindLabelInvalidLine()
	{
		Text actual = db.findLabel(new Line());

		assertNull(actual, "findLabel: Invalid input");
	}

	@Test
	void testEdgeExistsValidInput()
	{
		boolean expected = true;
		boolean actual = db.edgeExists(nodeShape1, nodeShape2);

		assertEquals(expected, actual, "edgeExists: Valid input");
	}

	@Test
	void testEdgeExistsInvalidInput()
	{
		boolean expected = false;
		boolean actual = db.edgeExists(nodeShape1, new Circle());

		assertEquals(expected, actual, "edgeExists: Invalid input");
	}

	@Test
	void testUpdatedLabelValidInput()
	{
		db.updateLabel(nodeLabel1, "changed");
		String actual = db.findLabel(nodeShape1).getText();

		assertEquals("changed", actual, "updateLabel: Valid input");
	}

	@Test
	void testUpdatedLabelInvalidLabel()
	{
		db.updateLabel(null, "changed");
		String actual = db.findLabel(nodeShape1).getText();

		assertEquals("Node 1", actual, "updateLabel: Invalid input");
	}

	@Test
	void testUpdatedLabelInvalidText()
	{
		db.updateLabel(nodeLabel1, null);
		String actual = db.findLabel(nodeShape1).getText();

		assertEquals("Node", actual, "updateLabel: Invalid input");
	}

	@Test
	void testRunDijkstraNodeWithOneEdge()
	{
		Node oneEdgeNode = new Node("A");
		Node nodeB = new Node("B");
		Node threeEdgeNode = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		List<Node> nodes = new LinkedList<>(Arrays.asList(threeEdgeNode, nodeB, oneEdgeNode, nodeE, nodeD));

		List<Edge> edges = new LinkedList<>(Arrays.asList(
				new Edge(oneEdgeNode, nodeE, 2),
				new Edge(nodeE, threeEdgeNode, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(threeEdgeNode, nodeD, 3),
				new Edge(nodeB, threeEdgeNode, 4)
		));

		Map<String[], String[]> expected = Map.of(
				new String[]{"Tv"}, new String[]{"A", "B", "C", "D", "E"},
				new String[]{"5"}, new String[]{"0", "-1", "-1", "-1", "2"},
				new String[]{"5", "9"}, new String[]{"0", "-1", "5", "9", "2"},
				new String[]{"5", "7", "9"}, new String[]{"0", "9", "5", "8", "2"},
				new String[]{"5", "7", "8", "9"}, new String[]{"0", "9", "5", "8", "2"},
				new String[]{"5", "6", "7", "8", "9"}, new String[]{"0", "9", "5", "8", "2"}
		);

		Dijkstra d = new Dijkstra(nodes, edges);

		Map<String[], String[]> actual = d.run(oneEdgeNode);

		assertTrue(deepEquals(expected, actual),
				"run: Did not return the expected output");
	}

	@Test
	void testRunDijkstraNodeWithThreeEdge()
	{
		Node oneEdgeNode = new Node("A");
		Node nodeB = new Node("B");
		Node threeEdgeNode = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		List<Node> nodes = new LinkedList<>(Arrays.asList(threeEdgeNode, nodeB, oneEdgeNode, nodeE, nodeD));

		List<Edge> edges = new LinkedList<>(Arrays.asList(
				new Edge(oneEdgeNode, nodeE, 2),
				new Edge(nodeE, threeEdgeNode, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(threeEdgeNode, nodeD, 3),
				new Edge(nodeB, threeEdgeNode, 4)
		));

		Map<String[], String[]> expected = Map.of(
				new String[]{"Tv"}, new String[]{"A", "B", "C", "D", "E"},
				new String[]{"7"}, new String[]{"-1", "4", "0", "3", "3"},
				new String[]{"7", "8"}, new String[]{"-1", "4", "0", "3", "3"},
				new String[]{"7", "8", "9"}, new String[]{"5", "4", "0", "3", "3"},
				new String[]{"6", "7", "8", "9"}, new String[]{"5", "4", "0", "3", "3"},
				new String[]{"5", "6", "7", "8", "9"}, new String[]{"5", "4", "0", "3", "3"}
		);

		Dijkstra d = new Dijkstra(nodes, edges);

		Map<String[], String[]> actual = d.run(threeEdgeNode);

		assertTrue(deepEquals(expected, actual),
				"run: Did not return the expected output");
	}

	@Test
	void testRunDijkstraInvalidNode()
	{
		Node oneEdgeNode = new Node("A");
		Node nodeB = new Node("B");
		Node threeEdgeNode = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		List<Node> nodes = new LinkedList<>(Arrays.asList(threeEdgeNode, nodeB, oneEdgeNode, nodeE, nodeD));

		List<Edge> edges = new LinkedList<>(Arrays.asList(
				new Edge(oneEdgeNode, nodeE, 2),
				new Edge(nodeE, threeEdgeNode, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(threeEdgeNode, nodeD, 3),
				new Edge(nodeB, threeEdgeNode, 4)
		));

		Dijkstra d = new Dijkstra(nodes, edges);

		Node invalidNode = new Node("F");

		assertThrows(IllegalArgumentException.class, () -> d.run(invalidNode),
				"run: Did not throw the expected exception");
	}

	@Test
	void testRunDijkstraNull()
	{
		Node oneEdgeNode = new Node("A");
		Node nodeB = new Node("B");
		Node threeEdgeNode = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");

		List<Node> nodes = new LinkedList<>(Arrays.asList(threeEdgeNode, nodeB, oneEdgeNode, nodeE, nodeD));

		List<Edge> edges = new LinkedList<>(Arrays.asList(
				new Edge(oneEdgeNode, nodeE, 2),
				new Edge(nodeE, threeEdgeNode, 3),
				new Edge(nodeE, nodeD, 7),
				new Edge(threeEdgeNode, nodeD, 3),
				new Edge(nodeB, threeEdgeNode, 4)
		));

		Dijkstra d = new Dijkstra(nodes, edges);

		assertThrows(IllegalArgumentException.class, () -> d.run(null),
				"run: Did not throw the expected exception");
	}

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
}
