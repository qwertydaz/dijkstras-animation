package project.gui.javafx;

import java.util.LinkedList;
import java.util.Map;

public class Animation
{
	private final Controller controller;
	private final Graph graph;
	private final Table table;

	private Map<String[], String[]> results;
	private Map<String[], String[]> headers;
	private LinkedList<String> visitedNodes;
	private int currentNodeIndex = 0;

	public Animation(Controller controller, Graph graph, Table table)
	{
		this.controller = controller;
		this.graph = graph;
		this.table = table;
	}

	// starts animation
	public void start()
	{
		results = controller.runDijkstra();

		this.visitedNodes = new LinkedList<>();
		graph.setAnimationStatus(true);
		currentNodeIndex = graph.getStartNodeIndex();
		graph.highlightNodeAndAdjacentEdges();
	}

	// highlights the next node and adjacent edges
	public void forward()
	{
		// TODO:
		//  - change dijkstra.run() output to list node ids instead of node names
		//  - then use node id to get node shape and highlight it and its adjacent edges
		//  - mark that node id as visited and ensure its not used again
		//  - update table with current L values (add a new row)
		//  - ensure that nothing happens once all nodes have been visited
	}

	// unhighlights the current node and adjacent edges
	public void backward()
	{
		// TODO:
		//  - unhighlight the current node and adjacent edges
		//  - mark that node id as unvisited and ensure it can be used again
		//  - update table with current L values (remove the last row)
		//  - ensure that nothing happens once all nodes are unvisited
	}

	// stops animation
	public void stop()
	{
		// TODO:
		//  - unhighlight all nodes and edges
		//  - reset table to initial state

		graph.setAnimationStatus(false);
		results = null;
		headers = null;
		currentNodeIndex = 0;
		visitedNodes.clear();
	}
}
