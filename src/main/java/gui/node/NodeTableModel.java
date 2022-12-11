package src.main.java.gui.node;

import src.main.java.gui.TableModel;
import src.main.java.model.dijkstra.Node;

import java.util.List;

public class NodeTableModel extends TableModel<Node>
{
	private List<Node> db;
	private final String[] columnNames = {"ID", "Name"};

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
