package project.gui.javafx;

import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import project.gui.javafx.controller.Controller;
import project.gui.javafx.util.Charts;
import project.gui.javafx.util.Util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.logging.Logger;

public class ComparisonChart
{
	private final Controller controller;
	private LineChart<Number, Number> lineChart;
	private final Charts charts;
	private Pane comparisonLineChartPane;
	private final ProgressBar progressBar;

	private int totalNodes;
	private int numberOfSteps;

	private static final Logger logger = Logger.getLogger(ComparisonChart.class.getName());

	public ComparisonChart(Controller controller)
	{
		this.controller = controller;
		this.charts = new Charts();
		this.progressBar = new ProgressBar(0);

		setupPane();
	}

	private void setupPane()
	{
		comparisonLineChartPane = new Pane();

		comparisonLineChartPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
				BorderWidths.DEFAULT)));

		progressBar.setPrefSize(100, 30);

		comparisonLineChartPane.getChildren().add(progressBar);

		createChart();
	}

	// TODO:
	//  - Add a warning about the time it takes to calculate the results
	private void createChart()
	{
		// Create the x-axis and y-axis
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Number of Nodes");
		yAxis.setLabel("Number of Comparisons");

		// Create the line chart
		lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Run Large Scale Test");

		// Add the line chart to the pane
		comparisonLineChartPane.getChildren().add(lineChart);

		lineChart.prefWidthProperty().bind(comparisonLineChartPane.widthProperty());
		lineChart.prefHeightProperty().bind(comparisonLineChartPane.heightProperty());
	}

	public void clearChart()
	{
		lineChart.getData().clear();
	}

	public void fillChart(int totalNodes, int numOfSteps, Map<Integer, Integer> results)
	{
		XYChart.Series<Number, Number> userInputGradient = charts.calculateUserInputGradient(results);
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

	public void startAlgorithm(int totalNumberOfNodes, int numberOfSteps)
	{
		this.totalNodes = totalNumberOfNodes;
		this.numberOfSteps = numberOfSteps;

		calculateResults(
				totalNumberOfNodes,
				numberOfSteps,
				this::handleProgressUpdate,
				this::handleResultsReady
		);
	}

	private void handleProgressUpdate(double progress)
	{
		progressBar.setProgress(progress);
	}

	private void handleResultsReady(Map<Integer, Integer> results)
	{
		fillChart(totalNodes, numberOfSteps, results);
	}

	// Repeatedly runs Dijkstra's algorithm on different sized graphs
	private void calculateResults(int totalNumberOfNodes, int numberOfSteps,
	                              DoubleConsumer onProgressUpdate,
	                              Consumer<Map<Integer, Integer>> onResultsReady)
	{
		int[] graphSizes = Util.calculateGraphSizes(totalNumberOfNodes, numberOfSteps);
		Map<Integer, Integer> results = new LinkedHashMap<>();
		results.put(0, 0);

		Thread thread = new Thread(() ->
		{
			for (int i = 0; i < graphSizes.length; i++)
			{
				long startTime = System.nanoTime();
				int graphSize = graphSizes[i];

				// Update the progress bar
				int finalI = i;
				Platform.runLater(() -> onProgressUpdate.accept((double) finalI / graphSizes.length));

				// Update the graph in ComparisonChartData.java
				controller.generateGraphData(graphSize);

				// Run Dijkstra's algorithm and collect the number of comparisons
				int comparisons = controller.getComparisons(graphSize);
				results.put(graphSize, comparisons);

				// Post log message to the console
				long timeTaken = (System.nanoTime() - startTime) / 1000000;
				String message = "Graph size: " + graphSize + ", Time: " + timeTaken + "ms";
				logger.info(message);
			}

			Platform.runLater(() ->
			{
				onProgressUpdate.accept(1.0);
				onResultsReady.accept(results);
			});
		});

		thread.start();
	}
}
