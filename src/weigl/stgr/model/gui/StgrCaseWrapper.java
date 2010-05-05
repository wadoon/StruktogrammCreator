/**
 * 
 */
package weigl.stgr.model.gui;

import weigl.stgr.model.StgrCase;

public class StgrCaseWrapper extends StgrCommandWrapper {

    public StgrCaseWrapper(StgrCase block) {
        super(block);
    }
    
    @Override
    public String toString() {
        return "case "+block.getLabel();
    }
    
}