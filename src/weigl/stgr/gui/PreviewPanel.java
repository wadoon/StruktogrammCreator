package weigl.stgr.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXPanel;

import weigl.stgr.model.StgrModel;

public class PreviewPanel extends JXPanel implements MouseListener {
	private static final long serialVersionUID = 711345429083075100L;
	private NavigableImagePanel m_navigableImagePanel = new NavigableImagePanel();

	private JPopupMenu m_exportMenu;
	private StgrAdvGui m_callerFrame;

	public PreviewPanel(StgrAdvGui frame) {
		m_callerFrame = frame;
		m_exportMenu = createPopMenu();
		setLayout(new BorderLayout());
		setSize(500, 500);
		add(m_navigableImagePanel);

		m_navigableImagePanel.addMouseListener(this);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				m_navigableImagePanel.setImage(dilbertImage());
			}
		});
	}

	private JPopupMenu createPopMenu() {
		JPopupMenu menu = new JPopupMenu();
		menu.add(m_callerFrame.getExportDocument());
		menu.add(m_callerFrame.getExportDocumentAs());
		menu.addSeparator();
		return menu;
	}

	public BufferedImage dilbertImage() {
		try {
			final String i = "/comics/dilbert/archive/images/dilbert.+\\.(gif|jpg)&";
			StringBuilder webcontent = new StringBuilder();
			URL dilbert = new URL("http://dilbert.com");
			InputStream s = dilbert.openStream();
			byte[] b = new byte[4096];
			while (s.read(b) >= 0)
				webcontent.append(new String(b));
			Pattern p = Pattern.compile(i, Pattern.MULTILINE);
			Matcher m = p.matcher(webcontent);
			m.find();
			String url = webcontent.substring(m.start(), m.end());
			url = url.substring(0, url.length() - 1);
			dilbert = new URL(dilbert.getProtocol(), dilbert.getHost(), url);
			return ImageIO.read(dilbert);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setModel(StgrModel generateModel) {
		final Font font = new Font(Font.MONOSPACED, Font.PLAIN, 10);
		m_navigableImagePanel.setImage(generateModel
				.getImage(getFontMetrics(font)));
	}

	
	public void mouseClicked(MouseEvent e) {

	}

	
	public void mouseEntered(MouseEvent e) {

	}

	
	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger())
			m_exportMenu.show(e.getComponent(), e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger())
			m_exportMenu.show(e.getComponent(), e.getX(), e.getY());
	}

}

class DisappearAction extends AbstractAction {
	private static final long serialVersionUID = 5186800806406652017L;
	public JFrame m_container;

	public DisappearAction(JFrame container) {
		if (container == null)
			throw new NullPointerException("container is null");
		m_container = container;

		putValue(NAME, "Schließen");
		putValue(SMALL_ICON, StgrAdvGui.getIcon("process-stop_small.png"));
		putValue(LONG_DESCRIPTION, "Schließt das Fenster"
				+ (m_container.getName() == null ? m_container.getTitle()
						: m_container.getName()));
	}

	
	public void actionPerformed(ActionEvent e) {
		int operation = m_container.getDefaultCloseOperation();
		switch (operation) {
		case WindowConstants.EXIT_ON_CLOSE:
			System.exit(0);
			break;
		case WindowConstants.DISPOSE_ON_CLOSE:
			m_container.setVisible(false);
			m_container.dispose();
			break;
		case WindowConstants.HIDE_ON_CLOSE:
			m_container.setVisible(false);
			break;
		}
	}
}