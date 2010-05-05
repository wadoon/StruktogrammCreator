package weigl.stgr.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.commons.io.FileUtils;

import weigl.gui.editor.EditorScrollPane;
import weigl.gui.editor.EditorScrollPane.Highlight;
import weigl.stgr.controller.CommandType;
import weigl.stgr.controller.StgrBuilder;
import weigl.stgr.exception.BuildException;
import weigl.stgr.exception.BuildExceptions;
import weigl.stgr.exception.NoSuitableBuilderFoundException;
import weigl.stgr.model.StgrModel;
import weigl.stgr.model.gui.JTreeRenderer;
import weigl.stgr.model.gui.StgrModelJTreeAdapter;

public class StgrAdvGuiDocument extends JPanel {
    private static final long serialVersionUID = -7242003445274140303L;

    private JTextPane m_textArea = new JTextPane();
    private EditorScrollPane editorScrollPane = createEditorPane(m_textArea);
    private File m_documentFile;
    private PreviewPanel m_preview = new PreviewPanel();
    private JTree m_tree = new JTree();

    public StgrAdvGuiDocument() {
	setName("new document");
	m_textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
	m_textArea.setBorder(BorderFactory.createEmptyBorder());
	m_tree.setCellRenderer(new JTreeRenderer());
	setLayout(new BorderLayout());

	JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		editorScrollPane, m_preview);
	sp.setDividerLocation(0.3);

	add(new JScrollPane(m_tree), BorderLayout.WEST);
//	add(m_tree, BorderLayout.WEST);
//	add(m_preview, BorderLayout.EAST);
	add(sp);
    }

    private EditorScrollPane createEditorPane(JTextPane mTextArea) {
	EditorScrollPane editorScrollPane = new EditorScrollPane(mTextArea);
	CommandType[] array = CommandType.values();
	String[] keywords = new String[array.length];
	String s = "(";
	for (int i = 0; i < array.length - 1; i++) {
	    s += array[i].toString() + "|";
	    keywords[i] = array[i].toString();
	}
	s += array[array.length - 1].toString() + ")";
	keywords[array.length - 1] = array[array.length - 1].toString();

	editorScrollPane.setKeywords(keywords);

	Highlight h = new Highlight(Highlight.compile(s, true)).setForeground(
		Color.GREEN).isBold();
	editorScrollPane.addHighlight(h);
	editorScrollPane.addHighlight("\\{.*\\}", Color.GRAY);

	editorScrollPane.getDocument().addDocumentListener(
		new DocumentListener() {

		    @Override
		    public void removeUpdate(DocumentEvent arg0) {
			previewDocument();
		    }

		    @Override
		    public void insertUpdate(DocumentEvent arg0) {
			previewDocument();

		    }

		    @Override
		    public void changedUpdate(DocumentEvent arg0) {
			previewDocument();
		    }
		});

	return editorScrollPane;
    }

    void previewDocument() {
	try {
	    StgrModel document = generateModel();
	    m_tree.setModel(new StgrModelJTreeAdapter(document));
	    m_preview.setModel(document);
	} catch (BuildException e) {
	    editorScrollPane.addErrorHint(e.getLine(), e.getMessage());
	} catch (BuildExceptions e1) {
	    for (BuildException ex : e1.getExceptions())
		editorScrollPane.addErrorHint(ex.getLine(), ex.getMessage());
	}
    }

    public File getDocumentFile() {
	return m_documentFile;
    }

    public void setDocumentFile(File documentFile) {
	m_documentFile = documentFile;
    }

    public static StgrAdvGuiDocument openFile(File file)
	    throws NoSuitableBuilderFoundException, IOException {
	StgrAdvGuiDocument gui = new StgrAdvGuiDocument();
	gui.setName(file.getName());
	gui.m_textArea.setText(FileUtils.readFileToString(file, "utf-8"));
	gui.setDocumentFile(file);
	return gui;
    }

    public void saveDocument() throws IOException {
	if (m_documentFile == null)
	    throw new NullPointerException("no file is set to save document");

	String text = m_textArea.getText();
	FileUtils.writeStringToFile(m_documentFile, text, "utf-8");
    }

    public EditorScrollPane getEditorScrollPane() {
	return editorScrollPane;
    }

    public StgrModel generateModel() throws BuildException, BuildExceptions {
	return StgrBuilder.INSTANCE.parse(m_textArea.getText());
    }

    public void formatCode() {
	try {
	    StringBuilder sb = new StringBuilder();
	    final StgrModel model = generateModel();
	    model.toCode(sb);
	    m_textArea.setText(sb.toString());
	} catch (Exception e) {
	    System.err.println(e);
	}
    }

}