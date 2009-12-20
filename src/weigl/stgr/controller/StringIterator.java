package weigl.stgr.controller;

import java.util.Iterator;

public class StringIterator implements Iterator<String> {
	private String[] m_lines;
	private int position = -1;

	public StringIterator(String text) {
		m_lines = text.split("\n");
	}

	public boolean hasNext() {
		return position < m_lines.length;
	}

	public String next() {
		if (position >= 0 && position < m_lines.length)
			m_lines[position] = null;
		if (++position < m_lines.length)
			return m_lines[position];
		else
			return null;
	}

	public void remove() {
		return;
	}
}
