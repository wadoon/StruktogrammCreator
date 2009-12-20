package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import weigl.stgr.gui.StgrAdvGui;



public class CloseDocumentAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = -8773424215752172201L;

  public CloseDocumentAction(StgrAdvGui application) {
    super(application);

    putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_W,
        InputEvent.CTRL_DOWN_MASK));
    putValue(NAME, "Datei schließen ...");
    putValue(LONG_DESCRIPTION, "Schließt akutelles Document.");
    putValue(SMALL_ICON, StgrAdvGui.getIcon("media-record_small.png"));
    // putValue(LARGE_ICON_KEY, StgrAdvGui.getIcon("media-record.png"));
  }

  public void actionPerformed(ActionEvent e) {
    m_application.closeDocument();
  }
}
