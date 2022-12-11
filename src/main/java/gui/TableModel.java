package src.main.java.gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel<V> extends AbstractTableModel
{
	private List<V> db;

	public TableModel()
	{
		// Empty Constructor
	}

	public void setData(List<V> db)
	{
		this.db = db;
	}

	@Override
	public int getRowCount()
	{
		return 0;
	}

	@Override
	public int getColumnCount()
	{
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return null;
	}
}
