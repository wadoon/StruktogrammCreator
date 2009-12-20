package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import weigl.stgr.gui.StgrAdvGui;

public class ExportAsDocumentAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = 5715441439622728749L;

  public ExportAsDocumentAction(StgrAdvGui application) {
    super(application);

    putValue(MNEMONIC_KEY, KeyEvent.VK_E);
    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_E,
        InputEvent.CTRL_DOWN_MASK));
    putValue(NAME, "Export as");
    putValue(
        LONG_DESCRIPTION,
        "Exportiert das Struktogramm als Bild im ausgwählten Typ, fragt woher Dateinamen.");
    putValue(SMALL_ICON, StgrAdvGui.getIcon("document-save-as_small.png"));
  }

  public void actionPerformed(ActionEvent e) {
    m_application.exportDocumentAs();
  }

}