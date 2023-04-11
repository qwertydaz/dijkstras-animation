package project.gui.javafx;

import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


// TODO:
//  - fix highlighting of nodes and edges
//  - first frame in table isn't appearing
public class Animation
{
	private final Controller controller;
	private final Graph graph;
	private final Table table;

	private LinkedHashMap<String[], String[]> results;
	private LinkedList<String> visitedNodes;
	private int currentNodeId = 0;
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
		currentNodeId = graph.getStartNodeId();

		Circle currentNode = controller.getNodeShape(currentNodeId);
		graph.highlightNodeAndAdjacentEdges(currentNode);

		this.visitedNodes = new LinkedList<>();
		visitedNodes.add(String.valueOf(currentNodeId));
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

		Circle nextNode = controller.getNodeShape(Integer.parseInt(nextNodeId));
		graph.highlightNodeAndAdjacentEdges(nextNode);
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

		Circle currentNode = controller.getNodeShape(Integer.parseInt(nodeId));
		graph.highlightNodeAndAdjacentEdges(currentNode);

		table.removeLastRow();
	}

	// stops animation
	public void stop()
	{
		results = null;
		currentNodeId = 0;
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
		String[] nodeNames = getNodeNames(dataRow.getKey());

		String[] formattedDataRow = new String[dataRow.getValue().length + 1];
		formattedDataRow[0] = Arrays.toString(nodeNames);
		System.arraycopy(dataRow.getValue(), 0, formattedDataRow, 1, dataRow.getValue().length);

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
		visitedNodes.forEach(newNodes::remove);

		return Collections.min(newNodes);
	}
}
