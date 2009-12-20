package weigl.stgr.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import weigl.stgr.exception.NoSuitableBuilderFoundException;

public abstract class BuilderUtil {
	private static List<Builder> m_builders = new ArrayList<Builder>(5);
	
	/**
	 * 
	 * @param builder
	 */
	public static void registerBuilder(Builder builder)
	{
		for (Builder b : m_builders)
			if(b.equals(builder)) return;
		
		System.out.println("BuilderUtil.registerBuilder()");
		m_builders.add(builder);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Iterator<Builder> getList()
	{
		return m_builders.iterator();
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static Builder getSuitableBuilder(File file) throws NoSuitableBuilderFoundException
	{
		Iterator<Builder> iter = m_builders.iterator();
		while (iter.hasNext()) {
			Builder b = iter.next();
			if(b.isSuitableFor(file))
				return b;
		}
		throw new NoSuitableBuilderFoundException("No Builder was found who supported this filetype");
	}
}
