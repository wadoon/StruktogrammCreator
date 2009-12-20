package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * @author  alex
 */
public class StgrRepeat extends StgrCommand {
	public static final int MARGIN_RIGHT = 10;

	public static final int MARGIN_VERTICAL = 6;

	public StgrRepeat(String cmd) {
		super(cmd);
	}

	public void setCommands(StgrCommandBlock cmd) {
		this.m_commands = cmd;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("do {").append(m_commands.toString()).append("\n}while(").append(
				super.toString()).append(")\n");
		return sb.toString();
	}

	@Override
	public Dimension getSize(FontMetrics f) {
		Dimension cmds_size = m_commands.getSize(f);

		m_size.height = MARGIN_VERTICAL  + getLabelHeight(f) + cmds_size.height;
		m_size.width = Math
				.max(MARGIN_RIGHT + cmds_size.width, getLabelWidth(f));
		return m_size;
	}

	@Override
	public void paintOn(Graphics2D g, int x, int y, int w, int h) {
		g.drawRect(x, y, w, getSize().height);

//		g.drawRect(x+MARGIN_RIGHT, y, w, h - y - MARGIN_BOTTOM );
		
		m_commands.paintOn(g, x + MARGIN_RIGHT, y, w - MARGIN_RIGHT, h);
		
		int posY = y +m_commands.getDimension().height + MARGIN_VERTICAL/2 + m_label_height;
		int posX = x + MARGIN_RIGHT;
		
		
		g.drawString(m_label, posX, posY);
//		g.drawString(m_label, x + MARGIN_RIGHT, y +cmds.getDimension().width + MARGIN_BOTTOM - 8);
	}
}
