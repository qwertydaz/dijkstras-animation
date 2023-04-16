package project.view.util;

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
		return displayDialog(Alert.AlertType.CONFIRMATION, "Confirmation", header, message);
	}

	public static boolean displayWarningDialog(String header, String message)
	{
		return displayDialog(Alert.AlertType.WARNING, "Warning", header, message);
	}

	private static boolean displayDialog(Alert.AlertType alertType, String title, String header, String message)
	{
		Alert dialog = new Alert(alertType);

		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(message);

		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");

		dialog.getButtonTypes().setAll(buttonYes, buttonNo);

		Optional<ButtonType> result = dialog.showAndWait();
		return result.orElse(buttonNo) == buttonYes;
	}

	public static int[] calculateGraphSizes(int totalNodes, int numberOfGraphs)
	{
		int[] graphSizes = new int[numberOfGraphs];
		int stepSize = totalNodes / numberOfGraphs;

		for (int i = 0; i < numberOfGraphs; i++)
		{
			graphSizes[i] = (i + 1) * stepSize;
		}

		return graphSizes;
	}
}
