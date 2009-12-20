package weigl.stgr.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import org.apache.commons.io.FileUtils;

import weigl.stgr.controller.Builder;
import weigl.stgr.controller.BuilderUtil;
import weigl.stgr.controller.StgrBuilder;
import weigl.stgr.controller.XmlBuilder;
import weigl.stgr.exception.NoSuitableBuilderFoundException;
import weigl.stgr.model.StgrModel;

public class StgrAdvGuiDocument extends JPanel {
	private static final long serialVersionUID = 2405089703963164257L;

	protected JTextPane m_textArea = new JTextPane();
	private File m_documentFile;
	private Builder m_builder;

	public StgrAdvGuiDocument() {
		setName("new document");
		m_textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		m_textArea.setBorder(BorderFactory.createEmptyBorder());
		setLayout(new BorderLayout());
		add(new EditorScrollPane(m_textArea));
	}

	public File getDocumentFile() {
		return m_documentFile;
	}

	public void setDocumentFile(File documentFile) {
		m_documentFile = documentFile;
	}

	public static StgrAdvGuiDocument openFile(File file) throws NoSuitableBuilderFoundException, IOException   {
		StgrAdvGuiDocument gui = new StgrAdvGuiDocument();
		Builder builder = BuilderUtil.getSuitableBuilder(file);
		gui.setBuilder(builder);
		gui.setName(file.getName());
		gui.m_textArea.setText(FileUtils.readFileToString(file,"utf-8"));
		gui.setDocumentFile(file);
		return gui;
	}
	
	public void saveDocument() throws IOException
	{
		if(m_documentFile == null)
			throw new NullPointerException("no file is set to save document");
		
		String text = m_textArea.getText();
		FileUtils.writeStringToFile(m_documentFile, text, "utf-8");
	}

	public Builder getBuilder() {
		return m_builder;
	}

	public void setBuilder(Builder suitableBuilder) {
		m_builder = suitableBuilder;
	}

	public StgrModel generateModel() throws Exception {
		if (m_builder == null)
			if (m_textArea.getText().startsWith("<?xml version=\"1.0\" encoding=\"utf-8\" ?>"))
				m_builder = XmlBuilder.INSTANCE;
			else
				m_builder = StgrBuilder.INSTANCE;
		return m_builder.parse(m_textArea.getText());
	}

}