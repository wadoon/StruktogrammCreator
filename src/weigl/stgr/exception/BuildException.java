package weigl.stgr.exception;

public class BuildException extends Exception {
	private static final long serialVersionUID = -3856623725479792751L;
	private int line;
	public BuildException(int line , String message) {
	   
		super(message);
		
		this.line = line;
	}
	public int getLine() {
	    return line;
	}
	public void setLine(int line) {
	    this.line = line;
	}
	
	
}
