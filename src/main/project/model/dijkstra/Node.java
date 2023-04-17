package project.model.dijkstra;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Objects;

public class Node implements Comparable<Node>
{
	private static int count = 1;
	private int id;
	private String name;

	private Text label;
	private Circle shape;
	private boolean isActive = false;

	public Node(int id, Text label, Circle shape)
	{
		this(label, shape);

		this.id = id;
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

		this.id = count;
		count++;
	}

	public Node(int name)
	{
		this.name = String.valueOf(name);

		this.id = count;
		count++;
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
		if (name == null)
		{
			name = "";
		}

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

	public void setActive()
	{
		isActive = true;
	}

	public void setInactive()
	{
		isActive = false;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public static void resetId()
	{
		count = 1;
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
