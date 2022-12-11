package src.main.java.gui.edge;

import src.main.java.gui.TableModel;
import src.main.java.model.dijkstra.Edge;

import java.util.List;

public class EdgeTableModel extends TableModel<Edge>
{
	private List<Edge> db;
	private final String[] columnNames = {"ID", "Node1", "Node2", "Weight"};

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		Edge edge = db.get(rowIndex);

		return switch (columnIndex)
				{
					case 0 -> edge.getId();
					case 1 -> edge.getNode1();
					case 2 -> edge.getNode2();
					case 3 -> edge.getWeight();
					default -> null;
				};
	}
}
