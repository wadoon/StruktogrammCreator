package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import weigl.stgr.controller.CommandType;
import weigl.stgr.model.gui.JTreeWrapper;
import weigl.stgr.model.gui.StgrIfElseWrapper;

/**
 * @author Alexander Weigl <alexweigl@gmail.com>
 */
@JTreeWrapper(StgrIfElseWrapper.class)
public class StgrIfElse extends StgrCommand {
    private StgrCommandBlock m_else = new StgrCommandBlock();
    private StgrCommandBlock m_if = super.m_commands;

    private int text_width = 0;

    public static final int HEIGHT = 30;

    public StgrIfElse(String cmd) {
	super(cmd);
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("if( ").append(super.toString()).append(" )\n{\n").append(
		m_if.toString()).append("}\nelse\n{").append(m_else.toString())
		.append("}\n");
	return sb.toString();
    }

    public int getHeight(FontMetrics m) {
	int h_if = m_if.calculateSize(m).height;
	int h_else = m_else.calculateSize(m).height;
	return Math.max(h_if, h_else) + getLabelHeight(m) + HEIGHT;
    }

    @Override
    public Dimension calculateSize(FontMetrics f) {
	text_width = f.stringWidth(this.m_label);
	m_size.height = getHeight(f);
	m_size.width = m_if.calculateSize(f).width + m_else.calculateSize(f).width;

	return m_size;
    }

    @Override
    public void paintOn(Graphics2D g, int x, int y, int w, int h) {
	Graphics2D f = (Graphics2D) g.create();
	
	int y1 = y + HEIGHT + m_label_height;

	options.configureFont(f);
	f.drawString("T", x + 5, y1 - 5);
	f.drawString("F", x + w - 10, y1 - 5);
	
	f.drawString(m_label, x + (m_if.getDimension().width - text_width / 2),
		y + 15);
	
	options.configureDraw(f);
	f.drawRect(x, y, w, HEIGHT + m_label_height);

	f.drawRect(x, y, w, getSize().height);

	f.drawLine(x, y, x + m_if.getDimension().width, y1);
	f.drawLine(x + w, y, x + m_if.getDimension().width, y1);

	drawBloc(g, x, y1, m_if.getDimension().width, h, m_if);
	drawBloc(g, x + m_if.getDimension().width, y1,
		m_else.getDimension().width, h, m_else);
    }

    public void drawBloc(Graphics2D g, int x, int y, int w, int h,
	    StgrCommandBlock bloc) {
	if (bloc.size() > 0) {
	    bloc.paintOn(g, x, y, w, h);
	} else {
	    g.drawRect(x, y, w, h);
	    g.drawLine(x, y, x + w, y + h);
	}
    }

    public void setCurrentBloc(CommandType t) {
	if (t == CommandType.ELSE) {
	    m_commands = m_else;
	} else {
	    m_commands = m_if;
	}
    }

    public StgrCommandBlock getElse() {
	return m_else;
    }

    public StgrCommandBlock getIf() {
	return m_if;
    }

    @Override
    public void toCode(StringBuilder str,int indent) 
    {
	Utils.addIndent(str, indent);
	str.append("if ").append(getLabel()).append("\n");

	m_if.toCode(str,indent+1);	

	Utils.addIndent(str, indent+1);
	str.append("else\n")	;
	
	m_else.toCode(str,indent+1);
	
	Utils.addIndent(str, indent);
	str.append("end\n");
    }
}
