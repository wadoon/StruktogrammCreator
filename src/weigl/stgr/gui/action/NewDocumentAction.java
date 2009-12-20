package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import weigl.stgr.gui.StgrAdvGui;

public class NewDocumentAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = -8773424215752172201L;

  public NewDocumentAction(StgrAdvGui application) {
    super(application);

    putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_N,
        InputEvent.CTRL_DOWN_MASK));
    putValue(NAME, "Neue Datei");
    putValue(LONG_DESCRIPTION, "Erstellt eine neue Datei.");
    putValue(SMALL_ICON, StgrAdvGui.getIcon("document-new_small.png"));
    // putValue(LARGE_ICON_KEY, StgrAdvGui.getIcon("document-new.png"));

  }

  public void actionPerformed(ActionEvent e) {
    m_application.newDocument();
  }
}