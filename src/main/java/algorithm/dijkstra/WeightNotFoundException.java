package src.main.java.algorithm.dijkstra;

public class WeightNotFoundException extends Exception
{
	public WeightNotFoundException(String message)
	{
		super(message);
	}

	public WeightNotFoundException()
	{
		super("Weight Not Found");
	}
}
