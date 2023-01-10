package src.main.java.gui.swing.edge;

import java.util.EventObject;

public class EdgeFormEvent extends EventObject
{
	private final String node1Name;
	private final String node2Name;
	private final int weight;

	public EdgeFormEvent(Object source, String node1Name, String node2Name, int weight)
	{
		super(source);

		this.node1Name = node1Name;
		this.node2Name = node2Name;
		this.weight = weight;
	}

	public String getNode1Name()
	{
		return node1Name;
	}

	public String getNode2Name()
	{
		return node2Name;
	}

	public int getWeight()
	{
		return weight;
	}
}
