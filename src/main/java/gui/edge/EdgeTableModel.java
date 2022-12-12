package src.main.java.gui.edge;

import src.main.java.model.dijkstra.Edge;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EdgeTableModel extends AbstractTableModel
{
	private List<Edge> db;
	private final String[] columnNames = {"ID", "Node1", "Node2", "Weight"};

	@Override
	public String getColumnName(int column)
	{
		return columnNames[column];
	}

	@Override
	public int getRowCount()
	{
		return db.size();
	}

	@Override
	public int getColumnCount()
	{
		return columnNames.length;
	}

	public void setData(List<Edge> db)
	{
		this.db = db;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Edge edge = db.get(rowIndex);

		return switch (columnIndex)
				{
					case 0 -> edge.getId();
					case 1 -> edge.getNode1().getName();
					case 2 -> edge.getNode2().getName();
					case 3 -> edge.getWeight();
					default -> null;
				};
	}
}
