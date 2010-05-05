package weigl.stgr.model.gui;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class JTreeRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = -6665881670956116454L;

    @Override
    public Component getTreeCellRendererComponent(JTree arg0, Object arg1,
	    boolean arg2, boolean arg3, boolean arg4, int arg5, boolean arg6) {
	Component c = super.getTreeCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	//TODO
	return c;
    }

}
