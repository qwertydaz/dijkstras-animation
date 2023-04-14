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

import java.util.Map;

public class ComparisonLineChart
{
	private final Controller controller;
	Pane comparisonLineChartPane;
	LineChart<Number, Number> lineChart;
	Map<Integer, Integer> results;

	public ComparisonLineChart(Controller controller)
	{
		this.controller = controller;

		setupPane();
	}

	private void setupPane()
	{

		comparisonLineChartPane = new Pane();

		comparisonLineChartPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		createGraph();
	}
	
	private void calculateResults(int numOfNodes, int numOfSteps)
	{
		results = controller.calculateResults(numOfNodes, numOfSteps);
	}

	// TODO:
	//  - Do not call this from the constructor
	//      - Add a button to calculate the results
	//  - Allow the user to select the number of nodes and steps
	//      - Add a text field for the number of nodes
	//      - Add a dropdown for the number of steps
	//      - Add a button to update the graph
	//  - Overlap the graphs (check if possible)
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
		lineChart.setTitle("Your Results");

		// Create the data series
		final XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Gradient");

		// Calculate the results
		int numOfNodes = 1000;
		int numOfSteps = 10;
		calculateResults(numOfNodes, numOfSteps);

		// Add data to the series
		for (Map.Entry<Integer, Integer> entry : results.entrySet())
		{
			series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		}

		// Add the data series to the line chart
		lineChart.getData().add(series);

		// Add the line chart to the pane
		comparisonLineChartPane.getChildren().add(lineChart);

		lineChart.prefWidthProperty().bind(comparisonLineChartPane.widthProperty());
		lineChart.prefHeightProperty().bind(comparisonLineChartPane.heightProperty());
	}

	public Pane getPane()
	{
		return comparisonLineChartPane;
	}
}
