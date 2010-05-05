package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

import weigl.stgr.model.gui.JTreeWrapper;
import weigl.stgr.model.gui.StgrCaseWrapper;

/**
 * @author Alexander Weigl <alexweigl@gmail.com>
 */
@JTreeWrapper(StgrCaseWrapper.class)
public class StgrCase extends StgrCommand {
    protected Point m_lineBegin;
    protected StgrCommandBlock m_block = new StgrCommandBlock();

    public StgrCase(String cmd) {
	super(cmd);
    }

    @Override
    public Dimension calculateSize(FontMetrics f) {
	m_size = m_commands.calculateSize(f);
	return getSize();
    }

    @Override
    public void paintOn(Graphics2D g, int x, int y, int w, int h) {
	m_commands.paintOn(g, x, y, w, h);
	
	Graphics2D f = (Graphics2D) g.create();
	options.configureDraw(f);
	f.drawRect(x, y, w, m_commands.getSize().height);
    }

    @Override
    public void toCode(StringBuilder str, int indent) {
	Utils.addIndent(str, indent);
	str.append("case ").append(getLabel()).append('\n');
	m_block.toCode(str, indent + 1);
	Utils.addIndent(str, indent);
	str.append("end\n");
    }
}