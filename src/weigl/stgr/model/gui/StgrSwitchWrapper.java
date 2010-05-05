/**
 * 
 */
package weigl.stgr.model.gui;

import weigl.stgr.model.StgrSwitch;

public class StgrSwitchWrapper extends StgrCommandWrapper
{
    public StgrSwitchWrapper(StgrSwitch block) {
        super(block);
    }
    
    @Override
    public boolean isLeaf() {
        return false;
    }
    

    @Override
    public String toString() {
        return "switch "+block.getLabel();
    }
    
}