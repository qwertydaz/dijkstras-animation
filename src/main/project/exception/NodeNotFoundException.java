package project.exception;

public class NodeNotFoundException extends Exception
{
	public NodeNotFoundException(String message)
	{
		super(message);
	}

	public NodeNotFoundException()
	{
		super("Node Not Found");
	}
}
