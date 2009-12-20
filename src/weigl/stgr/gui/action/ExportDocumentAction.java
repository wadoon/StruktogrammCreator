package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.KeyStroke;

import weigl.stgr.gui.StgrAdvGui;


public class ExportDocumentAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = 5715441439622728749L;

  public static Set<String> SUFFIXES = null;

  static {
    TreeSet<String> suffix = new TreeSet<String>();
    for (String suf : ImageIO.getWriterFileSuffixes())
      suffix.add(suf.toLowerCase());
    SUFFIXES = Collections.unmodifiableSet(suffix);
  }

  public ExportDocumentAction(StgrAdvGui application) {
    super(application);

    putValue(MNEMONIC_KEY, KeyEvent.VK_X);
    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_X,
        InputEvent.CTRL_DOWN_MASK));
    putValue(NAME, "Export");
    putValue(LONG_DESCRIPTION,
        "Exportiert das Struktogramm als Bild im ausgwählten Typ.");
    putValue(SMALL_ICON, StgrAdvGui.getIcon("document-save_small.png"));
  }

  public void actionPerformed(ActionEvent e) {
    m_application.exportDocument();
  }

  public static String[] getSuffixes() {
    String[] str = new String[SUFFIXES.size()];
    int i = 0;
    Iterator<String> it = SUFFIXES.iterator();
    while (it.hasNext())
      str[i++] = it.next();
    return str;
  }
}