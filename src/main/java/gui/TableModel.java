package src.main.java.gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public abstract class TableModel<V> extends AbstractTableModel
{
	private List<V> db;

	private final String[] columnNames = {};

	@Override
	public String getColumnName(int column)
	{
		return columnNames[column];
	}

	public void setData(List<V> db)
	{
		this.db = db;
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

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return null;
	}
}
