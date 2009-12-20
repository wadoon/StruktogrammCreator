package weigl.stgr.gui.action;

import javax.swing.AbstractAction;

import weigl.stgr.gui.StgrAdvGui;

abstract class StgrAdvGuiAction extends AbstractAction {
  protected StgrAdvGui m_application = null;

  public StgrAdvGuiAction(StgrAdvGui application) {
    m_application = application;
    init();
  }

  private void init() 
  {
    
  }

  public StgrAdvGui getApplication() {
    return m_application;
  }

  public void setApplication(StgrAdvGui application) {
    m_application = application;
  }
}