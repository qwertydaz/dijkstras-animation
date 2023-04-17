package project.model.dijkstra;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NodeTest
{
	@AfterEach
	void tearDown()
	{
		Node.resetId();
		Edge.resetId();
	}

	@Test
	void testSetNameChar()
	{
		Text nodeLabel = new Text("A");
		Circle nodeShape = new Circle();
		Node node = new Node(nodeLabel, nodeShape);
		node.setName("B");
		assertEquals("B", node.getName(), "setName: name should be B");
	}

	@Test
	void testSetNameLongString()
	{
		String longName = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";

		Text nodeLabel = new Text("A");
		Circle nodeShape = new Circle();
		Node node = new Node(nodeLabel, nodeShape);
		node.setName(longName);
		assertEquals(longName, node.getName(), "setName: name should be " + longName);
	}

	@Test
	void testSetNameEmptyString()
	{
		Text nodeLabel = new Text("A");
		Circle nodeShape = new Circle();
		Node node = new Node(nodeLabel, nodeShape);
		node.setName("");
		assertEquals("", node.getName(), "setName: name should be empty string");
	}

	@Test
	void testSetNameNull()
	{
		Text nodeLabel = new Text("A");
		Circle nodeShape = new Circle();
		Node node = new Node(nodeLabel, nodeShape);
		node.setName(null);
		assertEquals("", node.getName(), "setName: name should be empty string");
	}

	@Test
	void testCompareToNameFirstAlphabetically()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");

		assertEquals(-1, nodeA.compareTo(nodeB), "compareTo: nodeA should be less than nodeB");
	}

	@Test
	void testCompareToNameLastAlphabetically()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");

		assertEquals(1, nodeB.compareTo(nodeA), "compareTo: nodeB should be greater than nodeA");
	}

	@Test
	void testCompareToNull()
	{
		Node nodeA = new Node("A");

		assertThrows(NullPointerException.class, () -> nodeA.compareTo(null), "compareTo: nodeA should throw NullPointerException");
	}

	@Test
	void testEqualsSameObject()
	{
		Node nodeA = new Node("A");

		assertEquals(nodeA, nodeA, "equals: nodeA should be equal to itself");
	}

	@Test
	void testEqualsDifferentObjectSameName()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("A");

		assertEquals(nodeA, nodeB, "equals: nodeA should be equal to nodeB");
	}

	@Test
	void testEqualsDifferentObjectDifferentName()
	{
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");

		assertNotEquals(nodeA, nodeB, "equals: nodeA should not be equal to nodeB");
	}

	@Test
	void testEqualsNull()
	{
		Node nodeA = new Node("A");

		assertNotEquals(null, nodeA, "equals: nodeA should not be equal to null");
	}

	@Test
	void testEqualsDifferentClass()
	{
		Node nodeA = new Node("A");

		assertNotEquals("A", nodeA, "equals: nodeA should not be equal to string A");
	}

	@Test
	void testGetLabel()
	{
		Text nodeLabel = new Text("A");
		Circle nodeShape = new Circle();
		Node nodeA = new Node(nodeLabel, nodeShape);

		assertEquals("A", nodeA.getLabel().getText(), "getLabel: label should be A");
	}

	@Test
	void testGetShape()
	{
		Text nodeLabel = new Text("A");
		Circle nodeShape = new Circle();
		Node nodeA = new Node(nodeLabel, nodeShape);

		assertEquals(nodeShape, nodeA.getShape(), "getShape: shape should be nodeShape");
	}

	@Test
	void testSetActive()
	{
		Node nodeA = new Node("A");
		nodeA.setActive();

		assertTrue(nodeA.isActive(), "setActive: nodeA should be active");
	}

	@Test
	void testSetInactive()
	{
		Node nodeA = new Node("A");
		nodeA.setActive();
		nodeA.setInactive();

		assertFalse(nodeA.isActive(), "setInactive: nodeA should not be active");
	}

	@Test
	void testToString()
	{
		Node nodeA = new Node("A");

		assertEquals("{1: A}", nodeA.toString(), "toString: string should be {1: A}");
	}
}
