package project.gui.javafx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Util
{
	private Util()
	{
		// Empty constructor
	}

	private static final Alert errorDialog = new Alert(Alert.AlertType.ERROR);
	private static final Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
	private static final Alert yesNoDialog = new Alert(Alert.AlertType.CONFIRMATION);

	public static void displayErrorMessage(String header, String message)
	{
		errorDialog.setTitle("Error");
		errorDialog.setHeaderText(header);
		errorDialog.setContentText(message);
		errorDialog.showAndWait();
	}

	public static void displayInfoMessage(String header, String message)
	{
		infoDialog.setTitle("Info");
		infoDialog.setHeaderText(header);
		infoDialog.setContentText(message);
		infoDialog.showAndWait();
	}

	public static boolean displayOptionDialog(String header, String message)
	{
		yesNoDialog.setTitle("Confirmation");
		yesNoDialog.setHeaderText(header);
		yesNoDialog.setContentText(message);

		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");

		yesNoDialog.getButtonTypes().setAll(buttonYes, buttonNo);

		Optional<ButtonType> result = yesNoDialog.showAndWait();
		return result.orElse(buttonNo) == buttonYes;
	}
}
