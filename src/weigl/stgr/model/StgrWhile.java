package weigl.stgr.model;

import java.awt.Graphics2D;

import weigl.stgr.model.gui.JTreeWrapper;
import weigl.stgr.model.gui.StgrWhileWrapper;

@JTreeWrapper(StgrWhileWrapper.class)
public class StgrWhile extends StgrRepeat {

    public StgrWhile(String cmd) {
	super(cmd);
    }

    @Override
    public void paintOn(Graphics2D g, int x, int y, int w, int h) {
	Graphics2D f = (Graphics2D) g.create();

	options.configureDraw(f);
	f.drawRect(x, y, w, getSize().height);

	int posY = y + MARGIN_VERTICAL / 2 + m_label_height;
	int posX = x + MARGIN_RIGHT;
	
	options.configureFont(f);
	f.drawString(m_label, posX, posY);

	m_commands.paintOn(g, x + MARGIN_RIGHT, y + MARGIN_VERTICAL
		+ m_label_height, w - MARGIN_RIGHT, h);
    }

    @Override
    public void toCode(StringBuilder str, int indent) {
	Utils.addIndent(str, indent);
	str.append("while ").append(getLabel()).append('\n');

	m_commands.toCode(str, indent + 1);

	Utils.addIndent(str, indent);
	str.append("end\n");
    }
}
