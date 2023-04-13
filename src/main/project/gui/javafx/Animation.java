package project.gui.javafx;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Animation
{
	private final Controller controller;
	private final Graph graph;
	private final Table table;

	private final List<Map<Circle, Line>> toggledEdgesHistory = new ArrayList<>();

	private LinkedHashMap<String[], String[]> results;

	private Set<String> previousNodes = new HashSet<>();
	private final List<Integer> lastHighlightedNodeIds = new ArrayList<>();

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

			break;
		}

		graph.setAnimationStatus(true);
		totalFrames = results.size() - 1;

		forward();
	}

	// highlights the next node and adjacent edges
	public void forward()
	{
		// frame data
		if (frame == totalFrames)
		{
			return;
		}
		frame++;

		// get adjacent nodes and edges
		Map.Entry<String[], String[]> dataRow = getDataRow();
		assert dataRow != null;
		String nextNodeId = findNextNodeId(dataRow);
		Circle nextNode = controller.getNodeShape(Integer.parseInt(nextNodeId));
		Map<Circle, Line> adjacentNodesAndEdges = controller.getAdjacentNodesAndEdges(nextNode);

		// highlight current node and edges
		Map<Circle, Line> toggledEdges = graph.highlight(nextNode, adjacentNodesAndEdges);
		toggledEdges.put(nextNode, null); // Store the next node with a null edge value
		toggledEdgesHistory.add(toggledEdges);

		// update table
		table.addRow(formatDataRow(dataRow));
	}

	// unhighlights the current node and adjacent edges
	public void backward()
	{
		// frame data
		if (frame == 1)
		{
			return;
		}
		frame--;

		// unhighlight current node and edges
		Map<Circle, Line> lastToggledEdges = toggledEdgesHistory.remove(toggledEdgesHistory.size() - 1);
		graph.unhighlight(lastToggledEdges);

		// update table
		table.removeLastRow();
	}

	// stops animation
	public void stop()
	{
		results = null;
		frame= 0;

		table.clearAll();

		graph.setAnimationStatus(false);
		graph.unhighlightAll(toggledEdgesHistory);
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
		String[] nodeNames = getNodeNames(dataRow.getKey());

		String[] formattedDataRow = new String[dataRow.getValue().length + 1];
		formattedDataRow[0] = Arrays.toString(nodeNames);
		System.arraycopy(dataRow.getValue(), 0, formattedDataRow, 1, dataRow.getValue().length);

		for (int i = 0; i < formattedDataRow.length; i++)
		{
			if (formattedDataRow[i].equals("-1"))
			{
				formattedDataRow[i] = "∞";
			}
		}

		return formattedDataRow;
	}

	private String[] getNodeNames(String[] nodeIds)
	{
		String[] nodeNames = new String[nodeIds.length];

		for (int i = 0; i < nodeIds.length; i++)
		{
			nodeNames[i] = controller.getNodeName(Integer.parseInt(nodeIds[i]));
		}

		return nodeNames;
	}

	private String findNextNodeId(Map.Entry<String[], String[]> dataRow)
	{
		// Checks if next node id data has been cached
		if (frame <= lastHighlightedNodeIds.size())
		{
			return String.valueOf(lastHighlightedNodeIds.get(frame - 1));
		}

		Set<String> currentNodes = new HashSet<>(Arrays.asList(dataRow.getKey()));

		// Find the set of nodes that were added since the last frame
		Set<String> newNodes = new HashSet<>(currentNodes);
		newNodes.removeAll(previousNodes);

		// Return the ID of the first node in the set of new nodes
		String nextNodeId = newNodes.iterator().next();

		// Update the set of previous nodes for the next frame
		previousNodes = currentNodes;

		// Store the ID of the next node for the next frame
		lastHighlightedNodeIds.add(Integer.parseInt(nextNodeId));

		return nextNodeId;
	}
}