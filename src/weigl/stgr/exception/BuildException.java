package weigl.stgr.exception;

public class BuildException extends Exception {
	private static final long serialVersionUID = -3856623725479792751L;

	/**
	 * 
	 */
	public BuildException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BuildException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public BuildException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public BuildException(Throwable cause) {
		super(cause);
	}

}
