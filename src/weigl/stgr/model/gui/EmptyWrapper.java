package weigl.stgr.model.gui;

public class EmptyWrapper implements NodeWrapper {

    @Override
    public int childCount() {
	return 0;
    }

    @Override
    public NodeWrapper getChild(int i) {
	return null;
    }

    @Override
    public boolean isLeaf() {
	return true;
    }

    @Override
    public int indexOf(Object child) {
	return 0;
    }
    
    @Override
    public String toString() {
	return "error";
    }
}
