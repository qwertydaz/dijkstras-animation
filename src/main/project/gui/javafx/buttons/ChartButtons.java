package project.gui.javafx.buttons;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.gui.javafx.ComparisonChart;

public class ChartButtons
{
	private final Buttons buttons;
	private final ComparisonChart chart;

	private VBox totalNodesBox;
	private VBox stepSizeBox;

	private Button createChartButton;

	private final Font font = new Font(15);

	public ChartButtons(Buttons buttons, ComparisonChart comparisonChart)
	{
		this.buttons = buttons;
		this.chart = comparisonChart;

		setup();
		setupButtonActions();
	}

	public void setup()
	{
		// totalNodes
		Label totalNodesLabel = new Label("Total Nodes\n(10 - 1000)");
		totalNodesLabel.setFont(font);

		// min: 10, max: 1000, initial: 100, step: 10
		SpinnerValueFactory.IntegerSpinnerValueFactory spinnerValueFactory =
				new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 1000, 100, 10);
		Spinner<Integer> totalNodesSpinner = new Spinner<>(spinnerValueFactory);

		totalNodesBox = new VBox(totalNodesLabel, totalNodesSpinner);
		VBox.setVgrow(totalNodesLabel, Priority.ALWAYS);
		VBox.setVgrow(totalNodesSpinner, Priority.ALWAYS);

		// stepSize
		Label stepSizeLabel = new Label("Step Size\n(1 - 100)");
		stepSizeLabel.setFont(font);

		ComboBox<Integer> stepSizesComboBox = new ComboBox<>();
		stepSizesComboBox.getItems().addAll(1, 2, 5, 10, 20, 50, 100);
		stepSizesComboBox.setValue(10);

		stepSizeBox = new VBox(stepSizeLabel, stepSizesComboBox);
		VBox.setVgrow(totalNodesLabel, Priority.ALWAYS);
		VBox.setVgrow(totalNodesSpinner, Priority.ALWAYS);

		// createChart
		createChartButton = new Button("Create Chart");
	}

	public void setupButtonActions()
	{
		createChartButton.setOnAction(actionEvent -> createChart());
	}

	// TODO: Fix this
	private void createChart()
	{
		//chart.create(Integer.parseInt(totalNodesTextField.getText()), stepSizesComboBox.getValue());
	}

	public VBox getTotalNodesBox()
	{
		return totalNodesBox;
	}

	public VBox getStepSizeBox()
	{
		return stepSizeBox;
	}

	public Button getCreateChartButton()
	{
		return createChartButton;
	}
}
