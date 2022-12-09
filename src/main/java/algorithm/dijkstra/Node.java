package src.main.java.algorithm.dijkstra;

import java.util.Objects;

public class Node implements Comparable<Node>
{
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
	}

	public Node()
	{
		// Empty Constructor
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
}
