package project.gui.javafx;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

public class ControlLineChart
{
	Pane controlLineChartPane;
	LineChart<Number, Number> lineChart;

	public ControlLineChart()
	{
		controlLineChartPane = new Pane();
		createGraph();
	}

	public Pane getPane()
	{
		return controlLineChartPane;
	}

	private void createGraph()
	{
		// Create the x-axis and y-axis
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();

		// Create the line chart
		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Chart displaying n(n-1)/2 - theoretical gradient for max number of edges");

		// Create the data series
		final XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("n(n-1) / 2");

		// Add data to the series
		for (int i = 0; i <= 10; i++)
		{
			double gradient = (i * (i - 1)) / 2.0;
			series.getData().add(new XYChart.Data<>(i, gradient));
		}

		// Add the data series to the line chart
		lineChart.getData().add(series);

		// Add the line chart to the pane
		controlLineChartPane.getChildren().add(lineChart);
	}
}
