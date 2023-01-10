package src.main.java.gui.swing.edge;

import src.main.java.model.dijkstra.Edge;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EdgeTablePanel extends JPanel
{
	private final JTable table;
	private final EdgeTableModel tableModel;
	private final JPopupMenu popup;
	private EdgeTableListener edgeTableListener;

	public EdgeTablePanel()
	{
		tableModel = new EdgeTableModel();
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

			if (edgeTableListener != null)
			{
				edgeTableListener.rowDeleted(row);
				tableModel.fireTableRowsDeleted(row, row);
			}
		});

		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void setData(List<Edge> db)
	{
		tableModel.setData(db);
	}

	public void refresh()
	{
		tableModel.fireTableDataChanged();
	}

	public void setEdgeTableListener(EdgeTableListener listener)
	{
		this.edgeTableListener = listener;
	}
}
