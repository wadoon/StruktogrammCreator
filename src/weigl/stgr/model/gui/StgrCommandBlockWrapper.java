/**
 * 
 */
package weigl.stgr.model.gui;

import weigl.stgr.model.StgrCommandBlock;

public class StgrCommandBlockWrapper implements NodeWrapper {
    private StgrCommandBlock block;

    public StgrCommandBlockWrapper(StgrCommandBlock block) {
        this.block = block;
    }

    @Override
    public int childCount() {
        return block.childCount();
    }

    @Override
    public NodeWrapper getChild(int i) {
        return StgrModelJTreeAdapter.wrap(block.get(i));
    }

    @Override
    public boolean isLeaf() {
        return childCount() == 0;
    }

    @Override
    public int indexOf(Object child) {
	return block.indexOf(child);
    }


    @Override
    public String toString() {
        return "block "+block.getLabel();
    }
    
}