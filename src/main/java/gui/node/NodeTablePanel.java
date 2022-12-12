package src.main.java.gui.node;

import src.main.java.model.dijkstra.Node;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class NodeTablePanel extends JPanel
{
	private final JTable table;
	private final NodeTableModel tableModel;
	private final JPopupMenu popup;
	private NodeTableListener nodeTableListener;

	public NodeTablePanel()
	{
		tableModel = new NodeTableModel();
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

			if (nodeTableListener != null)
			{
				nodeTableListener.rowDeleted(row);
				tableModel.fireTableRowsDeleted(row, row);
			}
		});

		setLayout(new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void setData(List<Node> db)
	{
		tableModel.setData(db);
	}

	public void refresh()
	{
		tableModel.fireTableDataChanged();
	}

	public void setNodeTableListener(NodeTableListener listener)
	{
		this.nodeTableListener = listener;
	}
}
