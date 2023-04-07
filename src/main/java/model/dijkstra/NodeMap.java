package src.main.java.model.dijkstra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class NodeMap
{
	private final List<Node> nodes;

	public NodeMap(List<Node> nodes)
	{
		this.nodes = new ArrayList<>(nodes);
	}

	public NodeMap()
	{
		this.nodes = new ArrayList<>();
	}

	public void update(List<Node> nodes)
	{
		this.nodes.clear();
		this.nodes.addAll(nodes);
	}

	public String getNodeNames()
	{
		StringBuilder sb = new StringBuilder();

		for (Node node : nodes)
		{
			sb.append(node.getName());
			sb.append(" ");
		}

		return sb.toString().trim();
	}

	public Node getNodeByName(String name)
	{
		for (Node node : nodes)
		{
			if (node.getName().equals(name))
			{
				return node;
			}
		}

		return null;
	}

	public boolean containsNode(Node node)
	{
		return nodes.contains(node);
	}

	public boolean addNode(Node node)
	{
		return nodes.add(node);
	}

	public boolean removeNode(Node node)
	{
		return nodes.remove(node);
	}

	public List<Node> getNodes()
	{
		return new ArrayList<>(nodes);
	}

	public int size()
	{
		return nodes.size();
	}

	public boolean isEmpty()
	{
		return nodes.isEmpty();
	}

	public void clear()
	{
		nodes.clear();
	}

	public boolean contains(Node node)
	{
		return nodes.contains(node);
	}
}
