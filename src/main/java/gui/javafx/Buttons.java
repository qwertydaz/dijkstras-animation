package src.main.java.gui.javafx;

import javafx.scene.layout.Pane;

public class Buttons
{
	private Pane buttonsPane;

	public Buttons()
	{
		setupButtonsPane();
	}

	private void setupButtonsPane()
	{
		buttonsPane = new Pane();
	}

	public Pane getButtonsPane()
	{
		return buttonsPane;
	}
}
