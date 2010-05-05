/**
 * 
 */
package weigl.stgr.model.gui;

import weigl.stgr.model.StgrRepeat;

class StgrRepeatWrapper extends StgrCommandWrapper
{
    public StgrRepeatWrapper(StgrRepeat block) {
        super(block);
    }
    
    @Override
    public boolean isLeaf() {
        return false;
    }
    

    @Override
    public String toString() {
        return "repeat "+block.getLabel();
    }
    
}