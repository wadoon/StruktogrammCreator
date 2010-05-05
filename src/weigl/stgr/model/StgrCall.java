package weigl.stgr.model;

import java.awt.Dimension;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import weigl.stgr.model.gui.JTreeWrapper;
import weigl.stgr.model.gui.StgrCallWrapper;

@JTreeWrapper(StgrCallWrapper.class)
public class StgrCall extends StgrCommand {
    public StgrCall(String cmd) {
	super(cmd);
    }

    @Override
    public String toString() {
	return "call: " + super.toString();
    }

    @Override
    public Dimension calculateSize(FontMetrics f) {
	super.calculateSize(f).width = 30 + getLabelWidth(f);
	super.calculateSize(f).height = 10 + getLabelHeight(f);
	return getSize();
    }

    @Override
    public void paintOn(Graphics2D g, int x, int y, int w, int h) {
	Graphics2D f = (Graphics2D) g.create();
	
	options.configureDraw(f);
	f.drawRect(x, y, w, getSize().height);
	f.drawRect(x + 10, y, w - 20, getSize().height);

	options.configureFont(f);
	f.drawString(this.getLabel(), x + 15, y + getSize().height - 5);
    }

    @Override
    public void toCode(StringBuilder str, int indent) {
	Utils.addIndent(str, indent);
	str.append("call ").append(getLabel()).append('\n');
    }
}
