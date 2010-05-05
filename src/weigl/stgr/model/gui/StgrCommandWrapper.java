/**
 * 
 */
package weigl.stgr.model.gui;

import weigl.stgr.model.StgrCommand;

public class StgrCommandWrapper implements NodeWrapper {
    protected StgrCommand block;
    
    public StgrCommandWrapper(StgrCommand block) {
        this.block = block;
    }
    
    @Override
    public int childCount() {
        return block.childCount();
    }
    
    @Override
    public NodeWrapper getChild(int i) {
        return StgrModelJTreeAdapter.wrap(block.getCommands().get(i));
    }
    
    @Override
    public boolean isLeaf() {
        return childCount() == 0;
    }

    @Override
    public int indexOf(Object child) {
	return block.getCommands().indexOf(child);
    }
    

    @Override
    public String toString() {
        return block.getLabel();
    }
    
}