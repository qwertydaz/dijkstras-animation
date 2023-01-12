package src.main.java.gui.swing;

import javax.swing.SwingUtilities;

public class SwingApp
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(MainFrame::new);
	}
}
