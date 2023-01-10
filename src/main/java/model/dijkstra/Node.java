package src.main.java.model.dijkstra;

import javafx.scene.shape.Circle;

import java.util.Objects;

public class Node implements Comparable<Node>
{
	private static int count = 1;
	private int id;

	private String name;

	private int x;
	private int y;
	private Circle shape;

	public Node(String name, int x, int y, Circle shape)
	{
		this(name);
		this.x = x;
		this.y = y;
		this.shape = shape;
	}

	public Node(String name)
	{
		this.name = name;

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

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Circle getShape()
	{
		return shape;
	}

	@Override
	public int compareTo(Node other)
	{
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
