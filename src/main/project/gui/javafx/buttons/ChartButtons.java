package project.gui.javafx.buttons;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import project.gui.javafx.ComparisonChart;

public class ChartButtons
{
	private final ComparisonChart comparisonChart;

	private Spinner<Integer> totalNodesSpinner;
	private VBox totalNodesBox;

	private ComboBox<Integer> stepSizeComboBox;
	private VBox stepSizeBox;

	private Button createChartButton;

	private final Font font = new Font(15);

	public ChartButtons(ComparisonChart comparisonChart)
	{
		this.comparisonChart = comparisonChart;

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
		totalNodesSpinner = new Spinner<>(spinnerValueFactory);
		totalNodesSpinner.setEditable(true);

		totalNodesBox = new VBox(totalNodesLabel, totalNodesSpinner);
		VBox.setVgrow(totalNodesLabel, Priority.ALWAYS);
		VBox.setVgrow(totalNodesSpinner, Priority.ALWAYS);

		// stepSize
		Label stepSizeLabel = new Label("Step Size\n(5 - 100)");
		stepSizeLabel.setFont(font);

		stepSizeComboBox = new ComboBox<>();
		stepSizeComboBox.getItems().addAll(5, 10, 20, 50, 100);
		stepSizeComboBox.setValue(10);

		stepSizeBox = new VBox(stepSizeLabel, stepSizeComboBox);
		VBox.setVgrow(totalNodesLabel, Priority.ALWAYS);
		VBox.setVgrow(totalNodesSpinner, Priority.ALWAYS);

		// createChart
		createChartButton = new Button("Create Chart");
	}

	public void setupButtonActions()
	{
		createChartButton.setOnAction(actionEvent -> createChart());
	}

	private void createChart()
	{
		int totalNodes = totalNodesSpinner.getValue();
		int stepSize = stepSizeComboBox.getValue();

		comparisonChart.clearChart();
		comparisonChart.startAlgorithm(totalNodes, stepSize);
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
