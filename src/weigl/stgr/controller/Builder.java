package weigl.stgr.controller;

import java.io.File;
import java.io.IOException;


import weigl.stgr.exception.BuildException;
import weigl.stgr.model.StgrModel;

public interface Builder 
{
	public StgrModel parse(File file) throws BuildException, IOException;
	public StgrModel parse(String content) throws BuildException;
	public boolean isSuitableFor(File file);
	
}
