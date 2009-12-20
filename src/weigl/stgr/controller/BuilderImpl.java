package weigl.stgr.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import weigl.stgr.exception.BuildException;
import weigl.stgr.model.StgrModel;

public abstract class BuilderImpl implements Builder {
	public boolean isSuitableFor(File file)
	{
		return false;
	}

	public StgrModel parse(final File file) throws BuildException, IOException {
		return parse(FileUtils.readFileToString(file,"utf-8"));
	}
}
