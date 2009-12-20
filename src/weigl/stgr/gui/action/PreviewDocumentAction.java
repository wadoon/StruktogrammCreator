package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import weigl.stgr.gui.StgrAdvGui;

public class PreviewDocumentAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = 5715441439622728749L;

  public PreviewDocumentAction(StgrAdvGui application) {
    super(application);

    putValue(MNEMONIC_KEY, KeyEvent.VK_P);
    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_P,
        InputEvent.CTRL_DOWN_MASK));
    putValue(NAME, "Preview");
    putValue(LONG_DESCRIPTION, "Zeigt eine Vorschau der Datei an.");
    // putValue(SMALL_ICON, StgrAdvGui.getIcon("document-new_small.png"));
    // putValue(LARGE_ICON_KEY, StgrAdvGui.getIcon("document-new.png"));

  }

  public void actionPerformed(ActionEvent e) {
    m_application.previewDocument();
  }
}
