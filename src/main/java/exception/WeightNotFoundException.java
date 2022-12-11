package src.main.java.exception;

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
