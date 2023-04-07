package src.main.java.model.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeMap
{
	private final List<Node> nodes;
	private final Map<Integer, Integer> nodeIdToLValue;

	public NodeMap(List<Node> nodes)
	{
		this.nodes = new ArrayList<>(nodes);
		this.nodeIdToLValue = new HashMap<>();

		for (Node node : nodes)
		{
			this.nodeIdToLValue.put(node.getId(), -1);
		}
	}

	public NodeMap()
	{
		this.nodes = new ArrayList<>();
		this.nodeIdToLValue = new HashMap<>();
	}

	public void update(List<Node> nodes)
	{
		this.nodes.clear();
		this.nodes.addAll(nodes);
		this.nodeIdToLValue.clear();

		for (Node node : nodes)
		{
			this.nodeIdToLValue.put(node.getId(), -1);
		}
	}

	public String getNodeNames()
	{
		StringBuilder sb = new StringBuilder();

		for (Node node : nodes)
		{
			sb.append(node.getName());
			sb.append(" ");
		}

		return sb.toString();
	}

	public List<Node> getNodes()
	{
		return nodes;
	}

	public Integer getLValueByNodeId(int nodeId)
	{
		return nodeIdToLValue.get(nodeId);
	}

	public void setLValueByNodeId(int nodeId, int lValue)
	{
		nodeIdToLValue.put(nodeId, lValue);
	}

	public int size()
	{
		return this.nodes.size();
	}

	public boolean contains(Node node)
	{
		return getLValueByNodeId(node.getId()) != null;
	}

	public void clear()
	{
		this.nodes.clear();
		this.nodeIdToLValue.clear();
	}
}
