package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import weigl.stgr.controller.Options;
import weigl.stgr.model.gui.JTreeWrapper;
import weigl.stgr.model.gui.StgrCommandWrapper;

/**
 * @author alex
 */
@JTreeWrapper(StgrCommandWrapper.class)
public class StgrCommand implements ICommand {

    private static final int SPACE = 10;

    protected StgrCommandBlock m_commands = new StgrCommandBlock();

    protected String m_label = "test";

    protected Dimension m_size = new Dimension();;

    protected int m_label_height = 0;

    protected int m_label_width = 0;

    protected Options options;

    public StgrCommand(String cmd) {
	super();
	this.m_label = cmd;
    }

    public String getLabel() {
	return m_label;
    }

    public void setLabel(String cmd) {
	this.m_label = cmd;
    }

    public String toString() {
	return m_label;
    }

    public final int getLabelWidth(FontMetrics m) {
	getCalcLabelSize(m);
	return m_label_width;
    }

    public final int getLabelHeight(FontMetrics m) {
	getCalcLabelSize(m);
	return m_label_height;
    }

    /**
     * @param m
     */
    private final void getCalcLabelSize(FontMetrics m) {
	m_label_height = m.getHeight();
	m_label_width = m.stringWidth(m_label);
    }

    public void paintOn(Graphics2D g, int x, int y, int w, int h) {
	g = (Graphics2D) g.create();
	options.configureDraw(g);
	g.drawRect(x, y, w, getSize().height);
	options.configureFont(g);
	g.drawString(this.m_label, x + SPACE/2, y + getSize().height - SPACE/2);
    }

    public Dimension calculateSize(FontMetrics f) 
    {
	m_size.width  = SPACE + getLabelWidth(f);
	m_size.height = SPACE + getLabelHeight(f);
	return m_size;
    }

    public final Dimension getSize() {
	return m_size;
    }

    public final int getHorizentalCenter(int position_y, int box_height,
	    int text_height) {
	return (int) position_y;// box_height/2 - text_height/2;
    }

    public final void append(ICommand command) {
	m_commands.append(command);
    }

    public final void remove(ICommand command) {
	m_commands.remove(command);
    }

    public final StgrCommandBlock getCommands() {
	return m_commands;
    }

    public final int getLabelHeight() {
	return m_label_height;
    }

    public final int getLabelWidth() {
	return m_label_width;
    }

    public int childCount() {
	return m_commands.size();
    }

    @Override
    public void setOptions(Options o) {
	if (o == null)
	    options = new Options();
	else
	    this.options = o;
    }

    @Override
    public void toCode(StringBuilder str, int indent) {
	Utils.addIndent(str, indent);
	str.append("do ").append(this);
    }
}
