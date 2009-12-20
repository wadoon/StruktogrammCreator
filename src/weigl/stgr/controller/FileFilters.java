package weigl.stgr.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;

public class FileFilters {
	public static FileFilter getXMLFileFilter() {
		return new OrFileFilter(new EndNameFileFilter(".xml"), DirectoryFileFilter.INSTANCE);
	}

	public static FileFilter getStgrFileFilter() {
		return new OrFileFilter(new EndNameFileFilter(".stgr"), DirectoryFileFilter.INSTANCE);
	}

	public static FileFilter getAllFileFilter() {
		List<FileFilter> list = new ArrayList<FileFilter>();
		list.add(getStgrFileFilter());
		list.add(getXMLFileFilter());
		list.add(DirectoryFileFilter.INSTANCE);
		return new OrFileFilter(list);
	}

	public static javax.swing.filechooser.FileFilter getJFAllFileFilter()
	{
		return new WrapperFileFilter("Alle unterstüzten Dateien", getAllFileFilter());
	}

	public static javax.swing.filechooser.FileFilter getJFStgrFileFilter()
	{
		return new WrapperFileFilter("Stgr-Dateien", getStgrFileFilter());
	}

	public static javax.swing.filechooser.FileFilter getJFXMLFileFilter()
	{
		return new WrapperFileFilter("XML-Dateien", getXMLFileFilter());
	}
}

class WrapperFileFilter extends javax.swing.filechooser.FileFilter {
	private FileFilter ff;
	private String desc;

	public WrapperFileFilter(String desc, FileFilter filter) {
		ff = filter;
		this.desc = desc;
	}

	@Override
	public boolean accept(File f) {
		return ff.accept(f);
	}

	@Override
	public String getDescription() {
		return desc;
	}

}

class EndNameFileFilter implements IOFileFilter
{
	private String suffix;

	public EndNameFileFilter(String suffix) {
		super();
		this.suffix = suffix;
	}

	public boolean accept(File pathname) {
		return pathname.getName().toLowerCase().endsWith(suffix);
	}

	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(suffix);
	}
}