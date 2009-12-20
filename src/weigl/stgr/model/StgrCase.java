package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;


/**
 * @author  alex
 */
public class StgrCase extends StgrCommand {
	protected Point m_lineBegin;
	protected StgrCommandBlock m_block = new StgrCommandBlock();

	public StgrCase(String cmd) {
		super(cmd);
	}

	@Override
	public Dimension getSize(FontMetrics f) {
		m_size =  m_commands.getSize(f);
		return getSize();
	}

	@Override
	public void paintOn(Graphics2D g, int x, int y, int w, int h)
	{
		m_commands.paintOn(g, x, y, w, h);
		g.drawRect(x, y, w, m_commands.getSize().height);
	}
}