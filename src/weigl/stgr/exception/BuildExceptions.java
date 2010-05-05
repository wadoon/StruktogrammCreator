package weigl.stgr.exception;

import java.util.ArrayList;


public class BuildExceptions extends Exception {
    private static final long serialVersionUID = 7682211277253826339L;
    private ArrayList<BuildException> exceptions = new ArrayList<BuildException>();

    public ArrayList<BuildException> getExceptions() {
	return exceptions;
    }

    public void setBe(ArrayList<BuildException> be) {
	this.exceptions = be;
    }

    public boolean fire() {
	return exceptions.size() != 0;
    }

    public void add(int lineNum, String string) {
	exceptions.add(new BuildException(lineNum, string));
    }
}
