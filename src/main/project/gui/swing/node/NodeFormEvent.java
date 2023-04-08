package project.gui.swing.node;

import java.util.EventObject;

public class NodeFormEvent extends EventObject
{
	private final String name;

	public NodeFormEvent(Object source, String name)
	{
		super(source);

		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
