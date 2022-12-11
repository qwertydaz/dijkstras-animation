package src.main.java.model.dijkstra;

import java.util.Objects;

public class Node implements Comparable<Node>
{
	private static int count = 1;
	private int id;

	private String name;
	private int x;
	private int y;

	public Node(int x, int y, String name)
	{
		this(name);
		this.x = x;
		this.y = y;
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
