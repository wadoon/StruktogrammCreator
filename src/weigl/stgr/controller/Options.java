package weigl.stgr.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.HashMap;

public class Options extends HashMap<String, String> {
    private static final long serialVersionUID = 6293885552842608389L;
    private static final String K_ITALIC = "italic";
    private static final String K_BOLD = "bold";
    private static final String K_SANS = "sans";
    private static final String K_MONO = "mono";
    private static final String K_SERIF = "serif";
    private static final String K_FONT_COLOR = "fc";
    private static final String K_DRAW_COLOR = "dc";
    private static final String K_BOLD_ITALIC = "bolditalic";
    private static final String K_STROKE = "lw";

    public void set(String field) {

	try {
	    if (field.indexOf(":") >= 0) {
		String[] fields = field.split(":");
		put(fields[0], fields[1]);
	    } else {
		put(field, "enabled");
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	    // we know that, but for live update
	    System.err.println();
	    System.err.println();
	}

    }

    public Font deriveFont(Font f) {
	if (containsKey(K_SANS))
	    f = new Font(Font.SANS_SERIF, f.getStyle(), f.getSize());
	if (containsKey(K_MONO))
	    f = new Font(Font.MONOSPACED, f.getStyle(), f.getSize());
	if (containsKey(K_SERIF))
	    f = new Font(Font.SERIF, f.getStyle(), f.getSize());
	if (containsKey(K_BOLD))
	    f = f.deriveFont(Font.BOLD);
	if (containsKey(K_ITALIC))
	    f = f.deriveFont(Font.ITALIC);
	if (containsKey(K_BOLD_ITALIC))
	    f = f.deriveFont(Font.ITALIC|Font.BOLD);
	return f; 
    }

    public void configureFont(Graphics2D g) {
	Font f = deriveFont(g.getFont());
	if (containsKey(K_FONT_COLOR))
	    g.setColor(toColor(K_FONT_COLOR));
	g.setFont(f);
    }

    public void configureDraw(Graphics2D g) {
	if (containsKey(K_DRAW_COLOR))
	    g.setColor(toColor(K_DRAW_COLOR));
	if (containsKey(K_STROKE))
	    g.setStroke(toStroke(K_STROKE));
    }

    private Stroke toStroke(String kStroke) {
	String val = get(kStroke);
	System.err.println(val);
	float f;
	try {
	    f = Float.parseFloat(val);
	} catch (Exception e) {
	    f = 1F;
	}
	System.err.println(f);
	return new BasicStroke(f);
    }

    private Color toColor(String key) {
	String val = get(key);
	if (val == null)
	    return Color.BLACK;

	Color c;
	try {
	    int cl = Integer.parseInt(val, 16);
	    c = new Color(cl);
	} catch (NumberFormatException e) {
	    System.err.println("no color " + val + " found!");
	    return Color.black;
	}
	return c;
    }
}
