package src.main.java.gui;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablePanel<V> extends JPanel
{
	private final JTable table;
	private final JPopupMenu popup;
	private final TableModel<V> tableModel;
	private TableListener tableListener;

	public TablePanel(TableModel<V> tableModel, TableListener tableListener)
	{
		this.tableModel = tableModel;
		this.tableListener = tableListener;

		table = new JTable(tableModel);
		popup = new JPopupMenu();

		JMenuItem removeItem = new JMenuItem("Delete row");
		popup.add(removeItem);

		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent event)
			{
				int row = table.rowAtPoint(event.getPoint());

				table.getSelectionModel().setSelectionInterval(row, row);

				if (event.getButton() == MouseEvent.BUTTON3)
				{
					popup.show(table, event.getX(), event.getY());
				}
			}
		});

		removeItem.addActionListener(event ->
		{
			int row = table.getSelectedRow();

			if (tableListener != null)
			{
				tableListener.rowDeleted(row);
				tableModel.fireTableRowsDeleted(row, row);
			}
		});

		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void setData(List<V> db)
	{
		tableModel.setData(db);
	}

	public void refresh()
	{
		tableModel.fireTableDataChanged();
	}

	public void setPersonTableListener(TableListener listener)
	{
		this.tableListener = listener;
	}
}
