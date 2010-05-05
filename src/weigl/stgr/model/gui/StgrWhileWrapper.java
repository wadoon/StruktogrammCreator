/**
 * 
 */
package weigl.stgr.model.gui;

import weigl.stgr.model.StgrRepeat;
import weigl.stgr.model.StgrWhile;

public class StgrWhileWrapper extends StgrCommandWrapper
{
    public StgrWhileWrapper(StgrWhile block) {
        super(block);
    }

    public StgrWhileWrapper(StgrRepeat block) {
	super(block);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
    

    @Override
    public String toString() {
        return "loop "+block.getLabel();
    }
    
}