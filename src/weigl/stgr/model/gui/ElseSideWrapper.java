package weigl.stgr.model.gui;

import weigl.std.ToString;
import weigl.stgr.model.StgrIfElse;

public class ElseSideWrapper extends StgrCommandWrapper {

    public ElseSideWrapper(StgrIfElse ifelse) {
	super(ifelse);
    }

    @Override
    public NodeWrapper getChild(int i) {
	return StgrModelJTreeAdapter
		.wrap(((StgrIfElse) block).getElse().get(i));
    }

    @Override
    public boolean isLeaf() {
	return false;
    }

    @Override
    public String toString() {
	return "else";
    }
}
