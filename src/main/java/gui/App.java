package src.main.java.gui;

import javax.swing.SwingUtilities;

public class App
{
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(MainFrame::new);
	}
}
