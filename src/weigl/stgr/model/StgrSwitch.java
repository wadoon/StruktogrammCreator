package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;

import weigl.stgr.model.gui.JTreeWrapper;
import weigl.stgr.model.gui.StgrSwitchWrapper;

@JTreeWrapper(StgrSwitchWrapper.class)
public class StgrSwitch extends StgrCommand {
    	private static final int HEIGHT = 30;
	public StgrSwitch(String lbl) {
		super(lbl);
	}

	
	public Dimension calculateSize(FontMetrics metrics) {
		Dimension s = new Dimension( (TEXT_SPACE + getLabelWidth(metrics))*2 , HEIGHT );
		
		Dimension d = new Dimension();
		Iterator<ICommand> iter = m_commands.iterator();
		while (iter.hasNext()) {
			ICommand c = iter.next();
			Dimension e = c.calculateSize(metrics);

			d.width  += e.width;
			d.height += e.height > d.height ? e.height : d.height;
		}

		if(s.width >  d.width)
			d.width = s.width;
		
		d.height+=s.height;
		m_size = d;
		return d;
	}

	public void paintOn(Graphics2D g, int x, int y, int width, int height) {
		Rectangle rect = new Rectangle(new Point(x, y), new Dimension(width,
				HEIGHT));
		
		Graphics2D d =(Graphics2D) g.create();
		Graphics2D f =(Graphics2D) g.create();
		
		options.configureDraw(d);
		options.configureFont(f);
		
		// paint a big rectangle for the head
		d.drawRect(rect.x, rect.y, rect.width, rect.height);
		// paint a diagional in the rectangle
		d.drawLine(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height);
		// paint the label in the center of the rectangle
		f.drawString(getLabel(), (int) rect.x + rect.width - getLabelWidth() - TEXT_SPACE, (int) rect
				.getCenterY());

		// steigung der diagionale
		double m = ((rect.y - rect.getCenterY()) / (rect.x - rect.getCenterX()));

		Rectangle block = new Rectangle(new Point(x, y + rect.height));
		Iterator<ICommand> iter = m_commands.iterator();
		while (iter.hasNext()) {
			ICommand c = iter.next();
			int w = c.getSize().width;

			c.paintOn(g, block.x, block.y, w, height);
			f.drawString(c.getLabel(), block.x + 5, block.y -5);
			block.x += w;
			if (iter.hasNext()) {
				d.drawLine(block.x, (int) Math.abs(w * m) + y, block.x,
						block.y);
			}
			
		}
	}
	
	@Override
	public void toCode(StringBuilder str,int indent) {
	    	Utils.addIndent(str, indent);
	    	str.append("switch ").append(getLabel()).append('\n');
	    	
	    	m_commands.toCode(str,indent+1);
	    	
	    	Utils.addIndent(str, indent);
	    	str.append("end\n");
	}
}
