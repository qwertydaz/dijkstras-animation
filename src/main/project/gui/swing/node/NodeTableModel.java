package project.gui.swing.node;

import project.model.dijkstra.Node;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class NodeTableModel extends AbstractTableModel
{
	private List<Node> db;
	private final String[] columnNames = {"ID", "Name"};

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

	public void setData(List<Node> db)
	{
		this.db = db;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Node node = db.get(rowIndex);

		return switch (columnIndex)
				{
					case 0 -> node.getId();
					case 1 -> node.getName();
					default -> null;
				};
	}
}
