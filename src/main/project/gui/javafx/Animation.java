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

		for (Map.Entry<String[], String[]> entry : results.entrySet())
		{
			String[] columnNames = new String[entry.getKey().length + entry.getValue().length];
			System.arraycopy(entry.getKey(), 0, columnNames, 0, entry.getKey().length);
			System.arraycopy(entry.getValue(), 0, columnNames, entry.getKey().length, entry.getValue().length);
			table.setColumnNames(columnNames);

			results.remove(entry.getKey());
			break;
		}

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
		if (frame == totalFrames)
		{
			return;
		}

		frame++;
		Map.Entry<String[], String[]> dataRow = getDataRow();

		assert dataRow != null;
		String nextNodeId = findNextNodeId(dataRow);
		visitedNodes.add(nextNodeId);

		graph.highlightNodeAndAdjacentEdges(Integer.parseInt(nextNodeId));
		table.addRow(formatDataRow(dataRow));
	}

	// unhighlights the current node and adjacent edges
	public void backward()
	{
		if (frame == 0)
		{
			return;
		}

		frame--;
		visitedNodes.removeLast();
		String nodeId = visitedNodes.getLast();

		graph.highlightNodeAndAdjacentEdges(Integer.parseInt(nodeId));
		table.removeLastRow();
	}

	// stops animation
	public void stop()
	{
		results = null;
		currentNodeIndex = 0;
		frame= 0;
		visitedNodes.clear();

		table.clearAll();

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

	private String[] formatDataRow(Map.Entry<String[], String[]> dataRow)
	{
		String combinedString = String.join(" ", dataRow.getKey());
		String[] formattedDataRow = new String[dataRow.getValue().length + 1];
		formattedDataRow[0] = combinedString;
		System.arraycopy(dataRow.getValue(), 0, formattedDataRow, 1, dataRow.getValue().length);

		return formattedDataRow;
	}

	private String findNextNodeId(Map.Entry<String[], String[]> dataRow)
	{
		Set<String> newNodes = new HashSet<>(Arrays.asList(dataRow.getKey()));
		visitedNodes.forEach(newNodes::remove);

		return Collections.min(newNodes);
	}
}
