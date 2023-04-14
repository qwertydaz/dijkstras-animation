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

import java.util.Map;

public class ComparisonChart
{
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
        LineChart<Number, Number> lineChart;

		// Create the x-axis and y-axis
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Number of Nodes");
		yAxis.setLabel("Number of Comparisons");

		// Create the line chart
		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Run Large Scale Test");

		// Add control data to the graph
		lineChart.getData().add(charts.calculateMaxEdgeGradient());

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
