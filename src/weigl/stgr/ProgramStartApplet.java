package weigl.stgr;


import javax.swing.JApplet;

import weigl.stgr.gui.StgrIDE;

public class ProgramStartApplet extends JApplet {
	private static final long serialVersionUID = -8006837330761987683L;

	public void init() {
		StgrIDE view = new StgrIDE();
		setRootPane(view.getRootPane());
	}
}