package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;

import weigl.stgr.gui.StgrAdvGui;

public class CloseApplicationAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = -8773424215752172201L;

  public CloseApplicationAction(StgrAdvGui application) {
    super(application);

    // putValue(MNEMONIC_KEY, KeyEvent.VK_N);
    // putValue(ACCELERATOR_KEY,
    // KeyStroke.getAWTKeyStroke(KeyEvent.VK_W,InputEvent.CTRL_DOWN_MASK));
    putValue(NAME, "Programm schließen");
    putValue(LONG_DESCRIPTION, "Beendet die Applikation.");
    putValue(SMALL_ICON, StgrAdvGui.getIcon("process-stop_small.png"));
    // putValue(LARGE_ICON_KEY, StgrAdvGui.getIcon("process-stop.png"));
  }

  public void actionPerformed(ActionEvent e) {
    System.exit(0);
  }
}
