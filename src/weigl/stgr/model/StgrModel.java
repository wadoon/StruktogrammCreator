package weigl.stgr.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Date;

import weigl.stgr.controller.Options;

/**
 * @author alex
 */
public class StgrModel implements ICommand {
    private String m_author;
    private Date m_createDate;

    private StgrCommandBlock m_code = new StgrCommandBlock();

    private Dimension m_size = new Dimension();

    private String m_label = "TEST";

    private Options options = new Options();

    /**
     * Height of the horizentel space area
     */
    public static final int MARGIN_TOP = 30;

    /**
     * Width of the vertical space area
     */
    public static final int MARGIN_SIDE = 20;

    public StgrModel() {
	// Do Nothing ...
    }

    public StgrModel(String author, Date date) {
	super();
	this.m_author = author;
	this.m_createDate = date;
    }

    public String getAuthor() {
	return m_author;
    }

    public void setAuthor(String author) {
	this.m_author = author;
    }

    public Date getCreateDate() {
	return m_createDate;
    }

    public void setCreateDate(Date date) {
	this.m_createDate = date;
    }

    public StgrCommandBlock getCode() {
	return m_code;
    }

    public void setRootBloc(StgrCommandBlock begin) {
	this.m_code = begin;
    }

    public String toString() {
	return m_code.toString();

    }

    public int getTextWidth(FontMetrics m) {
	return m.stringWidth(m_label);
    }

    public int getTextHeight(FontMetrics m) {
	return m.getHeight();
    }

    public void paintOn(Graphics2D g, int x, int y, int width, int height) {
	Graphics2D f = (Graphics2D) g.create();

	options.configureDraw(f);
	f.drawRect(x, y, m_size.width, m_size.height);
	options.configureFont(f);
	f.drawString(m_label, x + MARGIN_SIDE, y + MARGIN_TOP - 5);

	m_code.paintOn(g, x + MARGIN_SIDE, y + MARGIN_TOP, width - MARGIN_SIDE,
		height);
    }

    public Dimension calculateSize(FontMetrics f) {
	Dimension s = m_code.calculateSize(f);
	m_size = s;

	this.m_size.height += MARGIN_TOP * 2;
	this.m_size.width += MARGIN_SIDE;
	return s;
    }

    public String getLabel() {
	return m_label;
    }

    public void setLabel(String label) {
	m_label = label;
    }

    public Dimension getSize() {
	return m_size;
    }

    public void setSize(Dimension size) {
	this.m_size = size;
    }

    public void append(ICommand command) {
	m_code.append(command);

    }

    public void remove(ICommand command) {
	m_code.remove(command);
    }

    public BufferedImage getImage() {
	return getImage(new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR)
		.createGraphics().getFontMetrics());
    }

    private BufferedImage getImage(FontMetrics metrics) {
	Dimension d = calculateSize(metrics);
	BufferedImage img = new BufferedImage(d.width + 3, d.height + 3,
		BufferedImage.TYPE_INT_RGB);

	Graphics2D g = img.createGraphics();
	g.setFont(metrics.getFont());
	g.setColor(Color.WHITE);
	g.fillRect(0, 0, img.getWidth(), img.getHeight());
	g.setColor(Color.BLACK);
	paintOn(g, 0, 0, d.width, d.height);
	return img;
    }

    public int childCount() {
	return m_code.size();
    }

    @Override
    public void setOptions(Options o) {
	if (o == null)
	    o = new Options();
	this.options = o;
    }

    @Override
    public void toCode(StringBuilder str, int indent) {
	Utils.addIndent(str, indent);
	m_code.toCode(str, indent);
    }

    public void toCode(StringBuilder str) {
	m_code.toCode(str, 0);
    }
}
