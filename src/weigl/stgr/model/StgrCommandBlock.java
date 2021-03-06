package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import weigl.stgr.controller.Options;
import weigl.stgr.model.gui.JTreeWrapper;
import weigl.stgr.model.gui.StgrCommandBlockWrapper;

/**
 * @author alex
 */
@JTreeWrapper(StgrCommandBlockWrapper.class)
public class StgrCommandBlock extends ArrayList<ICommand> implements ICommand {
    private static final long serialVersionUID = -6266401951122818506L;

    private Dimension size = new Dimension();

    public String toString() {
	StringBuilder sb = new StringBuilder();
	Iterator<ICommand> cmd = iterator();

	while (cmd.hasNext()) {
	    sb.append(cmd.next()).append("\n");
	}

	return sb.toString();
    }

    public Dimension calculateSize(FontMetrics m) {
	int sum = 0, max = 0;

	Dimension d;
	Iterator<ICommand> cmd = iterator();
	while (cmd.hasNext()) {
	    d = cmd.next().calculateSize(m);
	    sum += d.height;
	    if (d.width > max)
		max = d.width;
	}
	size.width = max;
	size.height = sum;
	return size;
    }

    public void paintOn(Graphics2D g, int x, int y, int width, int height) {
	for (ICommand c : this) {
	    c.paintOn(g, x, y, width, height);
	    y += c.getSize().height;
	}
    }

    public Dimension getDimension() {
	return size;
    }

    public String getLabel() {
	return null;
    }

    public Dimension getSize() {
	return size;
    }

    public void remove(ICommand command) {
	remove(command);
    }

    public void setLabel(String label) {

    }

    public void append(ICommand e) {
	add(e);
    }

    public int childCount() {
	return size();
    }

    @Override
    public void setOptions(Options o) {
	//not supported
    }

    @Override
    public void toCode(StringBuilder str,int indent) {
	Iterator<ICommand> cmd = iterator();
	while (cmd.hasNext())
	{
	    Utils.addIndent(str, indent);
	    cmd.next().toCode(str, indent+1);
	}
    }
}
