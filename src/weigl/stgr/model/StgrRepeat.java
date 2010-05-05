package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import weigl.stgr.model.gui.JTreeWrapper;
import weigl.stgr.model.gui.StgrWhileWrapper;

/**
 * @author alex
 */
@JTreeWrapper(StgrWhileWrapper.class)
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
	sb.append("do {").append(m_commands.toString()).append("\n}while(")
		.append(super.toString()).append(")\n");
	return sb.toString();
    }

    @Override
    public Dimension calculateSize(FontMetrics f) {
	Dimension cmds_size = m_commands.calculateSize(f);

	m_size.height = MARGIN_VERTICAL + getLabelHeight(f) + cmds_size.height;
	m_size.width = Math.max(MARGIN_RIGHT + cmds_size.width,
		getLabelWidth(f));
	return m_size;
    }

    @Override
    public void paintOn(Graphics2D g, int x, int y, int w, int h) {
	Graphics2D f =(Graphics2D) g.create();
	
	options.configureDraw(f);
	f.drawRect(x, y, w, getSize().height);

	m_commands.paintOn(g, x + MARGIN_RIGHT, y, w - MARGIN_RIGHT, h);

	int posY = y + m_commands.getDimension().height + MARGIN_VERTICAL / 2
		+ m_label_height;
	int posX = x + MARGIN_RIGHT;
	options.configureFont(f);
	f.drawString(m_label, posX, posY);
    }

    @Override
    public void toCode(StringBuilder str, int indent) {
	Utils.addIndent(str, indent);
	str.append("repeat ").append(getLabel()).append('\n');
	m_commands.toCode(str, indent + 1);

	Utils.addIndent(str, indent);
	str.append("end\n");
    }
}
