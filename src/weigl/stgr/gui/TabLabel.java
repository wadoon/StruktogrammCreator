package weigl.stgr.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabLabel extends JPanel {
	private static final long serialVersionUID = -177186766723563544L;
	private JButton m_btnClose = new JButton();
	private JLabel m_label = new JLabel();

	public TabLabel(String label, JTabbedPane pane, JComponent tab) {
		setText(label);
		setLayout(new BorderLayout(10,0));
		m_btnClose.setAction(new CloseTabAction(pane, tab));

		//Style
		m_btnClose.setBorder(BorderFactory.createEmptyBorder());
		setBorder(BorderFactory.createEmptyBorder(3,1,1,1));
		
		m_btnClose.setOpaque(false);
		m_label.setOpaque(false);
		setOpaque(false);
		
		add(m_label);
		add(m_btnClose, BorderLayout.EAST);
	}

	public void setText(String label) {
		m_label.setText(label);
	}

	public String getText() {
		return m_label.getText();
	}

	public class CloseTabAction extends AbstractAction {
		private static final long serialVersionUID = 4451967145226913614L;
		private JTabbedPane m_tabbedPane;
		private JComponent m_component;

		public CloseTabAction(JTabbedPane tabbedPane, JComponent component) {
			super();
			m_tabbedPane = tabbedPane;
			m_component = component;
			putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
					"icons/process-stop_small.png")));
		}

		
		public void actionPerformed(ActionEvent e) {
			m_tabbedPane.remove(m_component);
		}
	}
}
