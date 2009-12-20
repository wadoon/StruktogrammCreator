package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import weigl.stgr.gui.StgrAdvGui;

public class SaveDocumentAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = -9116135824721168221L;

  public SaveDocumentAction(StgrAdvGui application) {
    super(application);

    putValue(MNEMONIC_KEY, KeyEvent.VK_S);
    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_S,
        InputEvent.CTRL_DOWN_MASK));
    putValue(NAME, "Datei speichern");
    putValue(LONG_DESCRIPTION, "Speichert aktuelle Datei.");
    putValue(SMALL_ICON, StgrAdvGui.getIcon("document-save_small.png"));
    // putValue(LARGE_ICON_KEY, StgrAdvGui.getIcon("document-save.png"));
  }

  public void actionPerformed(ActionEvent e) {
    m_application.saveDocument();
  }
}