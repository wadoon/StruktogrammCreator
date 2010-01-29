package weigl.stgr.model;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import weigl.stgr.controller.CommandType;

/**
 * @author  alex
 */
public class StgrIfElse extends StgrCommand {
	private StgrCommandBlock m_else = new StgrCommandBlock();
	private StgrCommandBlock m_if = super.m_commands;

	private int text_width = 0;

	public static final int HEIGHT = 30;

	public StgrIfElse(String cmd) {
		super(cmd);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("if( ").append(super.toString()).append(" )\n{\n").append(
				m_if.toString()).append("}\nelse\n{").append(m_else.toString())
				.append("}\n");
		return sb.toString();
	}

	public int getHeight(FontMetrics m) {
		int h_if = m_if.getSize(m).height;
		int h_else = m_else.getSize(m).height;
		return Math.max(h_if, h_else) + getLabelHeight(m) + HEIGHT;
	}

	@Override
	public Dimension getSize(FontMetrics f) {
		text_width = f.stringWidth(this.m_label);
		m_size.height = getHeight(f);
		m_size.width = m_if.getSize(f).width
				+ m_else.getSize(f).width;

		return m_size;
	}

	@Override
	public void paintOn(Graphics2D g, int x, int y, int w, int h) {
		int y1 = y + HEIGHT + m_label_height;
		g.drawRect(x, y, w, HEIGHT + m_label_height);

		g.drawRect(x, y, w, getSize().height);

		g.drawLine(x, y, x + m_if.getDimension().width, y1);
		g.drawLine(x + w, y, x + m_if.getDimension().width, y1);

		g.drawString("T", x + 5, y1 - 5);
		g.drawString("F", x + w - 10, y1 - 5);

		g.drawString(m_label, x + (m_if.getDimension().width - text_width / 2),
				y + 15);

		printBloc(g, x, y1, m_if.getDimension().width, h, m_if);
		printBloc(g, x + m_if.getDimension().width, y1,
				m_else.getDimension().width, h, m_else);
	}

	public void printBloc(Graphics2D g, int x, int y, int w, int h,
			StgrCommandBlock bloc) {
		if (bloc.size() > 0) {
			bloc.paintOn(g, x, y, w, h);
		} else {
			g.drawRect(x, y, w, h);
			g.drawLine(x, y, x + w, y + h);
		}
	}
  
	public void setCurrentBloc(CommandType t)
	{
		if(t == CommandType.ELSE)
		{
			m_commands = m_else;
		}
		else
		{
			m_commands = m_if;
		}
	}

  public StgrCommandBlock getElse()
  {
    return m_else;
  }

  public StgrCommandBlock getIf()
  {
    return m_if;
  }

}
