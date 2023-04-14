package project.gui.javafx;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import project.gui.javafx.controller.Controller;
import project.gui.javafx.util.Charts;

public class ComparisonChart
{
	private LineChart<Number, Number> lineChart;
	private final Charts charts;
	private Pane comparisonLineChartPane;

	public ComparisonChart(Controller controller)
	{
		this.charts = new Charts(controller);

		setupPane();
	}

	private void setupPane()
	{

		comparisonLineChartPane = new Pane();

		comparisonLineChartPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		createGraph();
	}

	// TODO:
	//  - Add a loading wheel
	//  - Add a warning about the time it takes to calculate the results
	private void createGraph()
	{
		// Create the x-axis and y-axis
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Number of Nodes");
		yAxis.setLabel("Number of Comparisons");

		// Create the line chart
		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Run Large Scale Test");

		// Add control data to the graph

		// Add the line chart to the pane
		comparisonLineChartPane.getChildren().add(lineChart);

		lineChart.prefWidthProperty().bind(comparisonLineChartPane.widthProperty());
		lineChart.prefHeightProperty().bind(comparisonLineChartPane.heightProperty());
	}

	public void fillGraph(int totalNodes, int numOfSteps)
	{
		if (!lineChart.getData().isEmpty())
		{
			lineChart.getData().clear();
		}

		XYChart.Series<Number, Number> userInputGradient = charts.calculateUserInputGradient(totalNodes, numOfSteps);
		XYChart.Series<Number, Number> maxEdgeGradient = charts.calculateMaxEdgeGradient(totalNodes, numOfSteps);
		XYChart.Series<Number, Number> polynomialGradient = charts.calculatePolynomialGradient(totalNodes, numOfSteps);

		lineChart.getData().add(userInputGradient);
		lineChart.getData().add(maxEdgeGradient);
		lineChart.getData().add(polynomialGradient);
	}

	public Pane getPane()
	{
		return comparisonLineChartPane;
	}
}
