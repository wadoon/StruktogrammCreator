package weigl.stgr.gui;

import weigl.stgr.model.StgrModel;
import weigl.stgr.controller.Builder;
import weigl.stgr.controller.BuilderUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.net.URL;

public class StgrSimpleFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -5660317241738548120L;

	private StgrModel m_model;

	private NavigableImagePanel m_draw = new NavigableImagePanel();

	private JToolBar m_toolbar = new JToolBar();

	private JButton m_btnOpen = new JButton();

	private JButton m_btnExport = new JButton();

	private JButton m_btnShowOption = new JButton();

	private Font m_currentFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);

	private BufferedImage m_current_pic = null;

	JFileChooser m_fileChooser = new JFileChooser(
			"D:/workspace/StruktoGrammCompiler/resources/examples/");

	private File m_lastFile;

	public StgrSimpleFrame() {
		init();
	}

	private void init() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("jStruktogramm");
		setLayout(new BorderLayout());

		m_btnOpen.setIcon(loadIcon("/weigl/stgr/gui/icons/open.png"));
		m_btnExport.setIcon(loadIcon("/weigl/stgr/gui/icons/export.png"));

		m_btnExport.addActionListener(this);
		m_btnOpen.addActionListener(this);
		m_btnShowOption.addActionListener(this);

		m_btnExport.setToolTipText("Exportiert das Struktogramm als PNG");
		m_btnOpen.setToolTipText("�ffnet eine Struktogramm-Date");

		m_toolbar.add(m_btnOpen);
		m_toolbar.addSeparator();
		m_toolbar.add(m_btnExport);
		m_toolbar.addSeparator();
		// m_toolbar.add(m_btnShowOption);

		this.add(m_toolbar, BorderLayout.NORTH);
		this.add(m_draw, BorderLayout.CENTER);
		setSize(600, 600);

		m_fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				String s = f.getName().toLowerCase();
				return (s.endsWith(".xml") || s.endsWith(".stgr"))
						|| f.isDirectory();
			}

			public String getDescription() {
				return "Alle passende Dateien (xml,stgr)";
			}
		});
	}

	private ImageIcon loadIcon(final String str) {
		URL url = this.getClass().getResource(str);
		System.out.println(url);
		if (url != null)
			return new ImageIcon(url);
		else
			return null;
	}

	private void btnOpen_action() {
		if (m_fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		try {
			m_lastFile = m_fileChooser.getSelectedFile();

			Builder b = BuilderUtil.getSuitableBuilder(m_lastFile);
			if( b== null)return;
				
			m_model = b.parse(m_lastFile);

			Dimension model_size = m_model
					.getSize(getFontMetrics(m_currentFont));

			m_current_pic = new BufferedImage((int) model_size.width +5,
					(int) model_size.height+5, BufferedImage.TYPE_INT_RGB);

			Graphics2D g = m_current_pic.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, m_current_pic.getWidth(), m_current_pic.getHeight());
			g.setColor(Color.BLACK);
			g.setFont(m_currentFont);

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(m_currentFont);
			g.setColor(Color.white);
			g.setColor(Color.RED);

			m_model.paintOn(g, 0, 0, model_size.width, model_size.height);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage(),
					"Fehler beim Laden", JOptionPane.ERROR_MESSAGE);
		}
		m_draw.setImage(m_current_pic);
	}

	private void btnExport_action() {
		String filename = m_lastFile.getName();
		filename = filename.substring(0, filename.lastIndexOf('.')) + ".png";
		File png = new File(m_lastFile.getParentFile(), filename);
		boolean error = false;
		try {
			ImageIO.write(m_current_pic, "png", png);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(),
					"Fehler beim Exportieren", JOptionPane.ERROR_MESSAGE);
			error = true;
		} finally {
			if (!error)
				JOptionPane.showMessageDialog(this, "Struktgramm exportiert",
						"Export.", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Invoked when an action occurs.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == m_btnExport)
			btnExport_action();
		else if (e.getSource() == m_btnOpen)
			btnOpen_action();
		else
			btnShowOption_action();
	}

	private void btnShowOption_action() {
		repaint();
	}

	/*
	 * public class DrawPanel extends JPanel { private static final long
	 * serialVersionUID = 0L;
	 * 
	 * public DrawPanel(boolean b) { super(b); setBackground(Color.WHITE); }
	 * 
	 * @Override public Dimension getMinimumSize() { return m_model.getSize(); }
	 * 
	 * public void paint(Graphics g) {
	 * 
	 * if (m_model == null) return;
	 * 
	 * Dimension model_size = m_model .getSize(getFontMetrics(m_currentFont));
	 * 
	 * Rectangle r = new Rectangle(); r.setLocation(m_draw.getLocation());
	 * r.setSize(m_draw.getSize());
	 * 
	 * g.setColor(Color.WHITE); g.fillRect((int) r.getX(), (int) r.getY(), (int)
	 * r.getWidth(), (int) r.getHeight()); g.setColor(Color.BLACK);
	 * g.setFont(m_currentFont);
	 * 
	 * BufferedImage img = new BufferedImage(model_size.width + 10,
	 * model_size.height + 10, BufferedImage.TYPE_INT_RGB);
	 * 
	 * Graphics2D g1 = (Graphics2D) img.getGraphics();
	 * 
	 * g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 * RenderingHints.VALUE_ANTIALIAS_ON); g1.setFont(m_currentFont);
	 * g1.setColor(Color.white); g1.fillRect(0, 0, img.getWidth(),
	 * img.getHeight()); g1.setColor(Color.RED);
	 * 
	 * m_model.paintOn(g1, 0, 0, model_size.width, model_size.height);
	 * g.drawImage(img, 5, 5, this);
	 * 
	 * m_current_pic = img; } }
	 */
}
