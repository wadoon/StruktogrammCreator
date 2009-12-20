package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class StgrCall extends StgrCommand 
{
  public StgrCall(String cmd)
  {
    super(cmd);
  }

  @Override
  public String toString()
  {
    return "call: " + super.toString();
  }

  @Override
  public Dimension getSize(FontMetrics f)
  {
    super.getSize(f).width =  30 + getLabelWidth(f);
    super.getSize(f).height = 10 + getLabelHeight(f);
    return getSize();
  }

  @Override
  public void paintOn(Graphics2D g, int x, int y, int w, int h)
  {
    g.drawRect(x, y, w, getSize().height);
    g.drawString(this.getLabel(), x+15, y+getSize().height-5);
    g.drawRect(x+10, y, w-20, getSize().height);
  }
}

