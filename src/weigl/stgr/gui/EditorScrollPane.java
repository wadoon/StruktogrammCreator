package weigl.stgr.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;


public class EditorScrollPane extends JScrollPane implements DocumentListener,
		KeyListener {
	
	private static final long serialVersionUID = -1856478938455202466L;

	protected Box m_linePanel = new Box(BoxLayout.Y_AXIS);
	protected JTextComponent m_textComponent;
	protected Font m_currentFont;

	private DefaultHighlighter dh = new DefaultHighlighter();
	private DefaultHighlighter.DefaultHighlightPainter dhp = 
		new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);

	public EditorScrollPane(JTextComponent component) {
		m_textComponent = component;
		m_currentFont = component.getFont();

		m_linePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

		setViewportView(m_textComponent);
		setRowHeaderView(m_linePanel);
		component.getDocument().addDocumentListener(this);

		m_textComponent.setHighlighter(dh);

		m_textComponent.addKeyListener(this);

		update();
	}

	public void changedUpdate(DocumentEvent e) {
		update();
	}

	public void insertUpdate(DocumentEvent e) {
		update();
	}

	public void removeUpdate(DocumentEvent e) {
		update();
	}

	private int last_size = -1;

	public void update() {

		updateHighlighter();

		byte b[] = m_textComponent.getText().getBytes();
		int i = 1;
		for (byte c : b)
			if (c == (byte) '\n')
				++i;

		if (last_size == i)
			return;

		if (last_size == -1) {
			m_linePanel.removeAll();
			for (int j = 1; j <= i; j++)
				m_linePanel.add(createLabel(j));
		} else {
			int diff = last_size - i;

			if (i < last_size) {
				int count = m_linePanel.getComponentCount();
				for (int j = 1; j < diff; j++)
					m_linePanel.remove(count - j);
			} else {
				for (int j = (1 + last_size); j <= (1 + i); j++)
					m_linePanel.add(createLabel(j));
			}
		}
		m_linePanel.repaint();
	}

	private void updateHighlighter() {
		dh.removeAllHighlights();
		
		Pattern p = Pattern.compile("^\\s*(if|else|then|while|repeat|until)*.*",
				Pattern.DOTALL|Pattern.MULTILINE);
		Matcher m = p.matcher(m_textComponent.getText());
		
		if(!m.matches())return;
		
		for(int i = 1; i <= m.groupCount(); i++)
		{
			try {
				dh.addHighlight(m.start(i), m.end(i), dhp);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		
	}

	private Component createLabel(int i) {
		Formatter f = new Formatter((Appendable) null).format("%03d", i);
		JLabel lbl = new JLabel(f.out().toString());
		lbl.setFont(m_currentFont);
		return lbl;
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
		// System.out.println("Char: " + e.getKeyChar() +","+e.getModifiers());
		if (e.getKeyChar() == ' '
				&& (e.getModifiers() & KeyEvent.CTRL_MASK) > 0) {
			final JWindow m_toolTip = new JWindow();
			m_toolTip.setLayout(new BorderLayout());
			JList list = new JList(new String[] { "if", "else", "then",
					"while", "repeat", "until" });
			m_toolTip.add(list);
			m_toolTip.pack();
			System.out.println("EditorScrollPane.keyTyped()trigger");
			Point loc = m_textComponent.getCaret().getMagicCaretPosition();
			loc.translate(m_textComponent.getLocationOnScreen().x,
					m_textComponent.getLocationOnScreen().y);
			m_toolTip.setLocation(loc);
			m_toolTip.setVisible(true);
		}
	}
}