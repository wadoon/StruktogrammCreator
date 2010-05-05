package weigl.stgr.model.gui;

import weigl.stgr.model.StgrIfElse;

public class IfSideWrapper extends StgrCommandWrapper{
    public IfSideWrapper(StgrIfElse ifelse) {
	super(ifelse);
    }

    @Override
    public int childCount() {
	return super.block.childCount();
    }

    @Override
    public NodeWrapper getChild(int i) {
	return StgrModelJTreeAdapter.wrap(block.getCommands().get(i));
    }

    @Override
    public boolean isLeaf() {
	return false;
    }

    @Override
    public String toString() {
	return "if " + super.block.getLabel();
    }
    
    
}
