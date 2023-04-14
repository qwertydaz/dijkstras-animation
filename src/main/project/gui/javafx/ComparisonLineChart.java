package project.gui.javafx;

public class ComparisonLineChart
{
	private Controller controller;

	public ComparisonLineChart()
	{

	}

	public ComparisonLineChart(Controller controller)
	{
		this.controller = controller;
	}

	private int getComparisons(int numberOfNodes)
	{
		return controller.getComparisons(numberOfNodes);
	}

	private void display()
	{

	}
}
