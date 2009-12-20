package weigl.stgr;


import javax.swing.JApplet;

import weigl.stgr.gui.StgrAdvGui;

public class ProgramStartApplet extends JApplet {
	private static final long serialVersionUID = -8006837330761987683L;

	public void init() {
		StgrAdvGui view = new StgrAdvGui();
		setRootPane(view.getRootPane());
	}
}