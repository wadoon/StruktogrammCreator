package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import weigl.stgr.gui.StgrAdvGui;

public class OpenDocumentAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = -9116135824721168221L;

  public OpenDocumentAction(StgrAdvGui application) {
    super(application);

    putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_O,
        InputEvent.CTRL_DOWN_MASK));
    putValue(NAME, "Datei öffnen ...");
    putValue(LONG_DESCRIPTION, "Öffnet ein neues Document.");
    putValue(SMALL_ICON, StgrAdvGui.getIcon("document-open_small.png"));
    // putValue(LARGE_ICON_KEY, StgrAdvGui.getIcon("document-open.png"));
  }

  public void actionPerformed(ActionEvent e) {
    m_application.openDocument();
  }
}
