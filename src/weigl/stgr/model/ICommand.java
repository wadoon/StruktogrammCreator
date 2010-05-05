package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import weigl.stgr.controller.Options;

/**
 * @author alex
 */
public interface ICommand {
    public static final int TEXT_SPACE = 5;

    public void setLabel(String label);

    public String getLabel();

    public Dimension getSize();

    public Dimension calculateSize(FontMetrics metrics);

    public void paintOn(Graphics2D g, int x, int y, int w, int h);

    void setOptions(Options o);
    void toCode(StringBuilder str, int indent_level);

    public void append(ICommand command);

    public void remove(ICommand command);

    public int childCount();
}
