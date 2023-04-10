package project.gui.javafx;

import java.util.Map;

public class Animation
{
	private final Controller controller;
	private final Graph graph;
	private final Table table;

	private Map<String[], String[]> results;
	private Map<String[], String[]> headers;
	private int currentNodeIndex = 0;

	public Animation(Controller controller, Graph graph, Table table)
	{
		this.controller = controller;
		this.graph = graph;
		this.table = table;
	}

	public void start()
	{
		graph.setAnimationStatus(true);
		currentNodeIndex = graph.getStartNodeIndex();
		results = controller.runDijkstra();

		graph.highlightNodeAndAdjacentEdges();
	}

	public void forward()
	{

	}

	public void backward()
	{

	}

	public void stop()
	{
		graph.setAnimationStatus(false);
		results = null;
		headers = null;
		currentNodeIndex = 0;
	}
}
