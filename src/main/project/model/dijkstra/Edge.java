package project.model.dijkstra;

import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Edge
{
	private static int count = 1;
	private int id;

	private final Node node1;
	private final Node node2;
	private int weight;
	private final Set<Node> nodes;

	private Text label;
	private Line shape;

	public Edge(Node node1, Node node2)
	{
		this.node1 = node1;
		this.node2 = node2;
		this.nodes = new HashSet<>(Arrays.asList(node1, node2));

		this.id = count;
		count++;
	}

	public Edge(Node node1, Node node2, int weight)
	{
		this(node1, node2);
		this.weight = weight;
	}

	public Edge(int id, Node node1, Node node2, int weight)
	{
		this(node1, node2, weight);

		this.id = id;
	}

	public Edge(int id, Node node1, Node node2, Text label, Line shape)
	{
		this.node1 = node1;
		this.node2 = node2;
		this.nodes = new HashSet<>(Arrays.asList(node1, node2));

		this.weight = Integer.parseInt(label.getText());

		this.label = label;
		this.shape = shape;
		this.id = id;
		count++;
	}

	public Edge(Node node1, Node node2, Text label, Line shape)
	{
		this(node1, node2);
		this.weight = Integer.parseInt(label.getText());
		this.label = label;
		this.shape = shape;
	}

	public int getId()
	{
		return id;
	}

	public Node getNode1()
	{
		return node1;
	}

	public Node getNode2()
	{
		return node2;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(String weight)
	{
		this.weight = Integer.parseInt(weight);
		label.setText(weight);
	}

	public Set<Node> getNodes()
	{
		return nodes;
	}

	public Text getLabel()
	{
		return label;
	}

	public Line getShape()
	{
		return shape;
	}

	public String toString()
	{
		return "{" + id + ": " + node1.toString() + ", " + node2.toString() + ", " + weight + "}";
	}
}
