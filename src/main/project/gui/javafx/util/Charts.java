package project.gui.javafx.util;

import javafx.scene.chart.XYChart;
import project.gui.javafx.controller.Controller;

import java.util.Map;

public class Charts
{
	private final Controller controller;

	public Charts(Controller controller)
	{
		this.controller = controller;
	}

	public XYChart.Series<Number, Number> calculateMaxEdgeGradient()
	{
		// Create the data series
		final XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("y= x(x-1) / 2");

		// Add data to the series
		for (int i = 0; i <= 10; i++)
		{
			double gradient = (i * (i - 1)) / 2.0;
			series.getData().add(new XYChart.Data<>(i, gradient));
		}

		return series;
	}

	public XYChart.Series<Number, Number> calculateUserInputGradient(int totalNodes, int numOfSteps)
	{
		// Create the data series
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Your Results");

		// Calculate the results
		Map<Integer, Integer> results = controller.calculateResults(totalNodes, numOfSteps);

		// Add data to the series
		for (Map.Entry<Integer, Integer> entry : results.entrySet())
		{
			series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		}

		return series;
	}

	// Add more line gradients here...
}
