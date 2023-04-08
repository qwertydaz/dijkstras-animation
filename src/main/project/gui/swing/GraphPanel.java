package project.gui.swing;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GraphPanel extends JPanel
{
	public GraphPanel()
	{
		Border innerBorder = BorderFactory.createTitledBorder("Graph");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
	}
}
