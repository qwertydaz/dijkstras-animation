package project.model.dijkstra;

import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EdgeTest
{
	@AfterEach
	void tearDown()
	{
		Node.resetId();
		Edge.resetId();
	}

	@Test
	void testSetWeightPositiveInteger()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);
		edge.setWeight("2");

		assertEquals(2, edge.getWeight(), "setWeight: Positive Integer");
	}

	@Test
	void testSetWeightLargePositiveInteger()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);
		edge.setWeight("2147483647");

		assertEquals(2147483647, edge.getWeight(), "setWeight: Large Positive Integer");
	}

	@Test
	void testSetWeightNegativeInteger()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);
		edge.setWeight("-2");

		assertEquals(1, edge.getWeight(), "setWeight: Negative Integer");
	}

	@Test
	void testSetWeightNonInteger()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);

		assertThrows(IllegalArgumentException.class, () -> edge.setWeight("A"), "setWeight: Non Integer");
	}

	@Test
	void testGetId()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);

		assertEquals(1, edge.getId(), "getId: Did not return the expected value");
	}

	@Test
	void testGetNode1()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);

		assertEquals(nodeA, edge.getNode1(), "getNode1: Did not return the expected value");
	}

	@Test
	void testGetNode2()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);

		assertEquals(nodeB, edge.getNode2(), "getNode2: Did not return the expected value");
	}

	@Test
	void testGetLabel()
	{
		Text label = new Text("5");
		Line shape = new Line();
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, label, shape);

		assertEquals(label, edge.getLabel(), "getLabel: Did not return the expected value");
	}

	@Test
	void testGetShape()
	{
		Text label = new Text("5");
		Line shape = new Line();
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, label, shape);

		assertEquals(shape, edge.getShape(), "getShape: Did not return the expected value");
	}

	@Test
	void testSetActive()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);
		edge.setActive();

		assertTrue(edge.isActive(), "setActive: Did not set the expected value");
	}

	@Test
	void testSetInactive()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);
		edge.setActive();
		edge.setInactive();

		assertFalse(edge.isActive(), "setInactive: Did not set the expected value");
	}

	@Test
	void testToString()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Edge edge = new Edge(nodeA, nodeB, 1);

		assertEquals("{1: {1: A}, {2: B}, 1}", edge.toString(), "toString: Did not return the expected value");
	}
}
