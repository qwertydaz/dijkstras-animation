package src.main.java.model.dijkstra;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class NodeMap implements Map<Node, Integer>
{
	private final Map<Node, Integer> nodesAndLValues;

	public NodeMap(List<Node> nodes)
	{
		this.nodesAndLValues = new TreeMap<>();

		for (Node node : nodes)
		{
			nodesAndLValues.put(node, null);
		}
	}

	public NodeMap()
	{
		this.nodesAndLValues = new TreeMap<>();
	}

	public void update(List<Node> nodes)
	{
		nodesAndLValues.clear();

		for (Node node : nodes)
		{
			nodesAndLValues.put(node, null);
		}
	}

	@Override
	public Integer put(Node node, Integer integer)
	{
		return nodesAndLValues.put(node, integer);
	}

	@Override
	public void putAll(Map<? extends Node, ? extends Integer> nodeMap)
	{
		nodesAndLValues.putAll(nodeMap);
	}

	@Override
	public void clear()
	{
		nodesAndLValues.clear();
	}

	@Override
	public Set<Node> keySet()
	{
		return nodesAndLValues.keySet();
	}

	@Override
	public Collection<Integer> values()
	{
		return nodesAndLValues.values();
	}

	@Override
	public Set<Entry<Node, Integer>> entrySet()
	{
		return nodesAndLValues.entrySet();
	}

	@Override
	public Integer get(Object node)
	{
		return nodesAndLValues.get(node);
	}

	@Override
	public int size()
	{
		return nodesAndLValues.size();
	}

	@Override
	public boolean isEmpty()
	{
		return nodesAndLValues.isEmpty();
	}

	@Override
	public boolean containsKey(Object node)
	{
		return nodesAndLValues.containsKey(node);
	}

	@Override
	public boolean containsValue(Object lValue)
	{
		return nodesAndLValues.containsValue(lValue);
	}

	@Override
	public Integer remove(Object node)
	{
		return nodesAndLValues.remove(node);
	}
}
