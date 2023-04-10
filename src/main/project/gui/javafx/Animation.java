package project.gui.javafx;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Animation
{
	private final Controller controller;
	private final Graph graph;
	private final Table table;

	private LinkedHashMap<String[], String[]> results;
	private String[] headers;
	private LinkedList<String> visitedNodes;
	private int currentNodeIndex = 0;
	private int frame = 0;
	private int totalFrames;

	public Animation(Controller controller, Graph graph, Table table)
	{
		this.controller = controller;
		this.graph = graph;
		this.table = table;
	}

	// starts animation
	public void start()
	{
		results = (LinkedHashMap<String[], String[]>) controller.runDijkstra();
		headers = results.get(new String[]{"Tv"});
		results.remove(new String[]{"Tv"});
		totalFrames = results.size();

		graph.setAnimationStatus(true);
		graph.highlightNodeAndAdjacentEdges();

		this.visitedNodes = new LinkedList<>();
		currentNodeIndex = graph.getStartNodeIndex();
		visitedNodes.add(String.valueOf(currentNodeIndex));
	}

	// highlights the next node and adjacent edges
	public void forward()
	{
		// TODO:
		//  - update table with current L values (add a new row)

		if (frame == totalFrames)
		{
			return;
		}

		frame++;
		String nextNodeId = findNextNodeId(getDataRow());
		visitedNodes.add(nextNodeId);

		graph.highlightNodeAndAdjacentEdges(Integer.parseInt(nextNodeId));
	}

	// unhighlights the current node and adjacent edges
	public void backward()
	{
		// TODO:
		//  - update table with current L values (remove the last row)

		if (frame == 0)
		{
			return;
		}

		frame--;
		visitedNodes.removeLast();
		String nodeId = visitedNodes.getLast();

		graph.highlightNodeAndAdjacentEdges(Integer.parseInt(nodeId));
	}

	// stops animation
	public void stop()
	{
		// TODO:
		//  - reset table to initial state

		results = null;
		headers = null;
		currentNodeIndex = 0;
		frame= 0;
		visitedNodes.clear();

		graph.setAnimationStatus(false);
		graph.unhighlightAllNodesAndEdges();
	}

	private Map.Entry<String[], String[]> getDataRow()
	{
		int index = 0;

		for (Map.Entry<String[], String[]> entry : results.entrySet())
		{
			if (index == frame)
			{
				return entry;
			}

			index++;
		}

		return null;
	}

	private String findNextNodeId(Map.Entry<String[], String[]> dataRow)
	{
		Set<String> newNodes = new HashSet<>(Arrays.asList(dataRow.getKey()));
		visitedNodes.forEach(newNodes::remove);

		return Collections.min(newNodes);
	}
}
