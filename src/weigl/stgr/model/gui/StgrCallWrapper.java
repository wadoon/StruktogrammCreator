/**
 * 
 */
package weigl.stgr.model.gui;

import weigl.stgr.model.StgrCall;

public class StgrCallWrapper extends StgrCommandWrapper {
    public StgrCallWrapper(StgrCall  block) {
        super(block);
    }
    
    @Override
    public String toString() {
        return "call "+block.getLabel();
    }
}