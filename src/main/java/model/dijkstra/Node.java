package src.main.java.model.dijkstra;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node>
{
	private static int count = 1;
	private int id;
	private String name;

	private Text label;
	private Circle shape;
	private double xCoord;
	private double yCoord;

	private List<Integer> lValues;

	public Node(int id, double xCoord, double yCoord, Text label, Circle shape)
	{
		this(label, shape);

		this.id = id;
		this.xCoord = xCoord;
		this.yCoord = yCoord;

		this.lValues = new ArrayList<>();
	}

	public Node(Text label, Circle shape)
	{
		this(label.getText());
		this.label = label;
		this.shape = shape;
	}

	public Node(String name)
	{
		this.name = name;
		this.lValues = new ArrayList<>();

		this.id = count;
		count++;
	}

	public Node()
	{
		// Empty Constructor
	}

	public Node(int id, String name)
	{
		this(name);

		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
		label.setText(name);
	}

	public Text getLabel()
	{
		return label;
	}

	public Circle getShape()
	{
		return shape;
	}

	public double getXCoord()
	{
		return xCoord;
	}

	public double getYCoord()
	{
		return yCoord;
	}

	public List<Integer> getLValues()
	{
		return lValues;
	}

	public void addLValue(int lValue)
	{
		lValues.add(lValue);
	}

	@Override
	public int compareTo(Node other)
	{
		if (this.name == null || other.name == null)
		{
			throw new NullPointerException("Name cannot be null");
		}

		return this.name.compareTo(other.name);
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null || getClass() != other.getClass())
		{
			return false;
		}

		Node node = (Node) other;

		return Objects.equals(name, node.name);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(name);
	}

	public String toString()
	{
		return "{" + id + ": " + name + "}";
	}
}
