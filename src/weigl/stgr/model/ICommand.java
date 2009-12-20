package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;


/**
 * @author  alex
 */
public interface ICommand 
{
	public static final int TEXT_SPACE = 5;
	
	public void setLabel(String label);
	public String getLabel();
	public Dimension getSize();
	public Dimension getSize(FontMetrics metrics);
	public void paintOn(Graphics2D g, int x, int y, int w, int h);
	
	public void append(ICommand command);
	public void remove(ICommand command);
  public int childCount();
}
