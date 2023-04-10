package project.exception;

public class EdgeNotFoundException extends Exception
{
	public EdgeNotFoundException(String message)
	{
		super(message);
	}

	public EdgeNotFoundException()
	{
		super("Edge Not Found");
	}
}
