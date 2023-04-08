package project.gui.javafx;

import javafx.scene.control.Alert;

public class Util
{
	private Util()
	{
		// Empty constructor
	}

	private static final Alert errorDialog = new Alert(Alert.AlertType.ERROR);

	public static void displayErrorMessage(String header, String message)
	{
		errorDialog.setTitle("Error");
		errorDialog.setHeaderText(header);
		errorDialog.setContentText(message);
		errorDialog.showAndWait();
	}
}
