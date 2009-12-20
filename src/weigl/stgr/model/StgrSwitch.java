package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;

public class StgrSwitch extends StgrCommand {
	public StgrSwitch(String lbl) {
		super(lbl);
	}

	private static final int HEIGHT = 50;
	
	public Dimension getSize(FontMetrics metrics) {
		Dimension s = new Dimension( (TEXT_SPACE + getLabelWidth(metrics))*2 , HEIGHT );
		
		Dimension d = new Dimension();
		Iterator<ICommand> iter = m_commands.iterator();
		while (iter.hasNext()) {
			StgrCase c = (StgrCase) iter.next();
			Dimension e = c.getSize(metrics);

			d.width += e.width;
			d.height += e.height > d.height ? e.height : d.height;
		}

		if(s.width >=  d.width)
			d.width = s.width;
		
		d.height+=s.height;
		m_size = d;
		return d;
	}

	public void paintOn(Graphics2D g, int x, int y, int width, int height) {
		Rectangle rect = new Rectangle(new Point(x, y), new Dimension(width,
				HEIGHT));

		// pain a big rectangle for the head
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		// paint a diagional in the rectangle
		g.drawLine(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height);
		// paint the label in the center of the rectangle
		g.drawString(getLabel(), (int) rect.x + rect.width - getLabelWidth() - TEXT_SPACE, (int) rect
				.getCenterY());

		// steigung der diagionale
		double m = ((rect.y - rect.getCenterY()) / (rect.x - rect.getCenterX()));

		Rectangle block = new Rectangle(new Point(x, y + rect.height));
		Iterator<ICommand> iter = m_commands.iterator();
		while (iter.hasNext()) {
			StgrCase c = (StgrCase) iter.next();
			int w = c.getSize().width;

			c.paintOn(g, block.x, block.y, w, height);
			g.drawString(c.getLabel(), block.x + 5, block.y -5);
			block.x += w;
			if (iter.hasNext()) {
				g.drawLine(block.x, (int) Math.abs(w * m) + y, block.x,
						block.y);
			}
			
		}
	}
}
