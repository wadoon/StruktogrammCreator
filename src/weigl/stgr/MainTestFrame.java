package weigl.stgr;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;

import weigl.stgr.controller.XmlBuilder;
import weigl.stgr.model.StgrModel;

public class MainTestFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private Font fnt = new Font("Courier New", Font.PLAIN, 12);
    private FontMetrics metrics = getFontMetrics(fnt);
    private StgrModel model = null;

    private static StgrModel img;

    public MainTestFrame(StgrModel m) {
	model = m;
	setSize(400, 400);
	setTitle("Test");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	g.setFont(fnt);
	// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_ON);

	Dimension m = model.calculateSize(metrics);
	model.paintOn(g2, 10, 50, m.width, m.height);
    }

    public static void main(String[] args) {
	XmlBuilder builder;
	try {
	    builder = new XmlBuilder();
	    img = builder.parse(new File("stgr.xml"));
	} catch (Exception e) {
	    e.printStackTrace();
	}

	new MainTestFrame(img).setVisible(true);
    }
}
