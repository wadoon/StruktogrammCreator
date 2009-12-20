package weigl.stgr.model;

import java.awt.Graphics2D;

public class StgrWhile extends StgrRepeat {

	public StgrWhile(String cmd) {
		super(cmd);
	}

	@Override
	public void paintOn(Graphics2D g, int x, int y, int w, int h) {
		g.drawRect(x, y, w, getSize().height);

		int posY = y + MARGIN_VERTICAL/2 + m_label_height;
		int posX = x + MARGIN_RIGHT;

		g.drawString(m_label, posX, posY);

		m_commands.paintOn(g, x + MARGIN_RIGHT, y + MARGIN_VERTICAL + m_label_height, w - MARGIN_RIGHT, h);
	}
}
