package weigl.stgr.gui;

import java.io.StringWriter;

import javax.swing.JEditorPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@Deprecated
public class LoggerPanel extends EditorScrollPane implements DocumentListener {
	private static final long serialVersionUID = -395576208984721126L;

	public LoggerPanel() {
		super(new JEditorPane());
		m_textComponent.setEditable(false);
	}

	public void appendText(String text) {
		m_textComponent.setText(m_textComponent.getText() + text);
	}

	public void appendText(Exception e) 
	{
		appendText(e);
		appendText(e.getStackTrace());
		Throwable ourCause = e.getCause();
		if (ourCause != null)
		{
			appendText("\n----------------------------\nCause:\n");
			appendText(ourCause.getStackTrace());
		}
	}
	
	public void appendText(StackTraceElement[] element)
	{
		StringWriter writer = new StringWriter();
		for (int i = 0; i < element.length; i++)
			writer.write("\tat " + element[i] + "\n");
		appendText(writer.getBuffer().toString());
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		onTextUpdate();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		onTextUpdate();

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		onTextUpdate();
	}

	private void onTextUpdate() {
		getVerticalScrollBar().setValue(getViewport().getMaximumSize().height);
	}
}