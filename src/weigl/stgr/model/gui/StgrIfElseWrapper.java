package weigl.stgr.model.gui;

import weigl.stgr.model.StgrIfElse;

public class StgrIfElseWrapper implements NodeWrapper {
    private StgrIfElse ifelse;
    private NodeWrapper ifSide;
    private NodeWrapper elseSide;

    public StgrIfElseWrapper(StgrIfElse ifelse) {
        this.ifelse = ifelse;
        ifSide = new IfSideWrapper(ifelse);
        elseSide = new ElseSideWrapper(ifelse);
    }

    @Override
    public int childCount() {
        return 2;
    }

    @Override
    public NodeWrapper getChild(int i) {
        switch (i) {
        case 0:
    	return ifSide;
        default:
    	return elseSide;
        }
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int indexOf(Object child) {
	return child == ifSide ? 0:1;
    }
    

    @Override
    public String toString() {
        return "condition "+ifelse.getLabel();
    }
}

