package project.gui.javafx;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Animation
{
	private final Controller controller;
	private final Graph graph;
	private final Table table;

	private LinkedHashMap<String[], String[]> results;

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
		totalFrames = results.size();

		forward();
	}

	// TODO: fix the highlighting of nodes and edges
	// highlights the next node and adjacent edges
	public void forward()
	{
		// frame data
		if (frame == totalFrames - 1)
		{
			return;
		}
		frame++;

		// gather adjacent nodes and edges
		Map.Entry<String[], String[]> dataRow = getDataRow();
		assert dataRow != null;
		String nextNodeId = findNextNodeId(dataRow);
		Circle nextNode = controller.getNodeShape(Integer.parseInt(nextNodeId));
		Map<Circle, Line> adjacentNodesAndEdges = controller.getAdjacentNodesAndEdges(nextNode);

		// highlight adjacent nodes and edges
		graph.highlight(nextNode, adjacentNodesAndEdges);

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
		graph.unhighlightCurrent();

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
		graph.unhighlightAll();
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
		Set<String> newNodes = new HashSet<>(Arrays.asList(dataRow.getKey()));

		return Collections.min(newNodes);
	}
}
