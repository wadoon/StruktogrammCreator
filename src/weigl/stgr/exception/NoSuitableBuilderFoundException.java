package weigl.stgr.exception;

public class NoSuitableBuilderFoundException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	public NoSuitableBuilderFoundException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuitableBuilderFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public NoSuitableBuilderFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoSuitableBuilderFoundException(Throwable cause) {
		super(cause);
	}
}
