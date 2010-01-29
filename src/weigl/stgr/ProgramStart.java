package weigl.stgr;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import weigl.stgr.gui.StgrAdvGui;

public class ProgramStart {
	public static void main(String[] args) {
		final JFrame frame = new StgrAdvGui();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setVisible(true);
			}
		});
	}
}
