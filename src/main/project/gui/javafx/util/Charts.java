package project.gui.javafx.util;

import javafx.scene.chart.XYChart;

import java.util.Map;
import java.util.function.IntUnaryOperator;

public class Charts
{
	// Generic method to use any gradient function
	public XYChart.Series<Number, Number> calculateGradient(IntUnaryOperator gradientFunction,
	                                                        String equationName, int xLimit, int numOfSteps)
	{
		// Create the data series
		final XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.getData().add(new XYChart.Data<>(0, 0));
		series.setName(equationName);

		// Calculate the step size
		int[] stepSizes = Util.calculateGraphSizes(xLimit, numOfSteps);

		// Add data to the series
		for (int stepSize : stepSizes)
		{
			double gradient = gradientFunction.applyAsInt(stepSize);
			series.getData().add(new XYChart.Data<>(stepSize, gradient));
		}

		return series;
	}

	// Calculate with max edge gradient: y = x(x-1) / 2
	public XYChart.Series<Number, Number> calculateMaxEdgeGradient(int xLimit, int stepSize)
	{
		return calculateGradient(i -> (i * (i - 1)) / 2, "y= x(x-1) / 2", xLimit, stepSize);
	}

	// Calculate with polynomial gradient: y = x^2
	public XYChart.Series<Number, Number> calculatePolynomialGradient(int xLimit, int stepSize)
	{
		return calculateGradient(i -> (i * i), "y= x^2", xLimit, stepSize);
	}

	// Calculate using data from Dijkstra.java
	public XYChart.Series<Number, Number> calculateUserInputGradient(Map<Integer, Integer> results)
	{
		// Create the data series
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Your Results");

		// Add data to the series
		for (Map.Entry<Integer, Integer> entry : results.entrySet())
		{
			series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		}

		return series;
	}

	// Add more line gradients here...
}
