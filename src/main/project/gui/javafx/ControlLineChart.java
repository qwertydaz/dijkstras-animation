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

public class ControlLineChart
{
	Pane controlLineChartPane;
	LineChart<Number, Number> lineChart;

	public ControlLineChart()
	{
		setupPane();
	}

	private void setupPane()
	{
		controlLineChartPane = new Pane();

		controlLineChartPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

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
		xAxis.setLabel("X");
		yAxis.setLabel("Y");

		// Create the line chart
		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Chart displaying: y= x(x-1) / 2");

		// Create the data series
		final XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("y= x(x-1) / 2");

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

		lineChart.prefWidthProperty().bind(controlLineChartPane.widthProperty());
		lineChart.prefHeightProperty().bind(controlLineChartPane.heightProperty());
	}
}
