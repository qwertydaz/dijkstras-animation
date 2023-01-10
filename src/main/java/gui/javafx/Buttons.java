package src.main.java.gui.javafx;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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

		buttonsPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));
	}

	public Pane getButtonsPane()
	{
		return buttonsPane;
	}
}
