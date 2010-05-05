package weigl.stgr.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FilenameUtils;

import weigl.stgr.controller.FileFilters;
import weigl.stgr.exception.NoSuitableBuilderFoundException;

public class StgrIDE extends JFrame implements ChangeListener {
    private static final long serialVersionUID = 6257836907736132965L;

    private Action m_formatCode = new FormatDocument();
    private Action m_openDocument = new OpenDocumentAction();
    private Action m_newDocuemnt = new NewDocumentAction();
    private Action m_closeApp = new CloseApplicationAction();
    private Action m_saveDocumentAs = new SaveAsDocumentAction();
    private Action m_saveDocument = new SaveDocumentAction();
    private Action m_previewDocument = new PreviewDocumentAction();
    private Action m_exportDocument = new ExportDocumentAction(this);
    private Action m_exportDocumentAs = new ExportAsDocumentAction(this);

    private JFileChooser m_fileChooser = createFileChooser();
    private JTabbedPane m_openFiles = new JTabbedPane();

    private StgrAdvGuiDocument m_default = new StgrAdvGuiDocument();

    public StgrIDE() {
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setTitle("Stgr Editor");
	setContentPane(createContentPane());
	addDocument(m_default);
	m_openFiles.addChangeListener(this);
	setSize(600, 400);
    }

    private JPanel createContentPane() {
	JPanel p = new JPanel(new BorderLayout(0, 0));
	p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	p.add(createToolPane(), BorderLayout.NORTH);
	p.add(m_openFiles);
	return p;
    }

    /**
     * @return
     * 
     */
    private JFileChooser createFileChooser() {
	JFileChooser jf = new JFileChooser(
		"D:/workspace/StruktoGrammCompiler/resources/examples/");
	jf.setFileHidingEnabled(true);
	jf.setMultiSelectionEnabled(true);
	jf.setDialogTitle("Struktogrammdatei auswählen ...");

	jf.setFileFilter(FileFilters.getJFXMLFileFilter());
	jf.setFileFilter(FileFilters.getJFStgrFileFilter());
	jf.setFileFilter(FileFilters.getJFAllFileFilter());
	return jf;
    }

    /**
     * @return JXTaskPaneContainer
     * 
     */
    @SuppressWarnings("unused")
//    private JXTaskPaneContainer createTaskPaneContainer() {
//	JXTaskPaneContainer container = new JXTaskPaneContainer();
//	JXTaskPane taskPane = new JXTaskPane();
//	taskPane.setTitle("Application");
//	taskPane.setSpecial(true);
//	taskPane.add(m_newDocuemnt);
//	taskPane.add(m_openDocument);
//	taskPane.add(m_closeApp);
//	container.add(taskPane);
//
//	taskPane = new JXTaskPane();
//	taskPane.setTitle("Document");
//	taskPane.add(m_previewDocument);
//	taskPane.add(m_saveDocument);
//	taskPane.add(m_saveDocumentAs);
//	taskPane.add(m_formatCode);
//	container.add(taskPane);
//
//	taskPane = new JXTaskPane();
//	taskPane.setTitle("Exportieren");
//	taskPane.add(m_exportDocument);
//	taskPane.add(m_exportDocumentAs);
//	container.add(taskPane);
//
//	taskPane = new JXTaskPane();
//	taskPane.setTitle("Baumansicht");
//	container.add(taskPane);
//
//	return container;
//    }

    private JToolBar createToolPane() {
	JToolBar taskPane = new JToolBar();
	taskPane.setRollover(true);
	taskPane.setFloatable(false);
	taskPane.add(m_newDocuemnt);
	taskPane.add(m_openDocument);
	taskPane.add(m_saveDocumentAs);
	taskPane.addSeparator();
	taskPane.add(m_previewDocument);
	taskPane.add(m_formatCode);
	taskPane.addSeparator();
//	taskPane.add(m_saveDocument);
//	taskPane.add(m_exportDocument);
	taskPane.add(m_exportDocumentAs);
	return taskPane;
    }

    public void newDocument() {
	StgrAdvGuiDocument new_doc = new StgrAdvGuiDocument();
	addDocument(new_doc);
    }

    void addDocument(StgrAdvGuiDocument sagd) {
	m_openFiles.add(sagd);
    }

    public void openDocument() {
	if (m_fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    for (File f : m_fileChooser.getSelectedFiles())
		openFile(f);
	}
    }

    void openFile(File file) {
	StgrAdvGuiDocument doc;
	try {
	    doc = StgrAdvGuiDocument.openFile(file);
	    addDocument(doc);
	} catch (NoSuitableBuilderFoundException e) {
	    JOptionPane
		    .showMessageDialog(
			    this,
			    "Programmfehler",
			    String
				    .format(
					    "Zur der Datei '%s' konnte kein passender Builder "
						    + "gefunden werden.\nBitte überprüfen Sie die Dateisuffix.",
					    file.getAbsolutePath()),
			    JOptionPane.ERROR_MESSAGE);
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(this, "Fehler beim Lesen der Datei",
		    "Beim Einlesen der Datei '" + file.getAbsolutePath()
			    + "' ist ein Fehler aufgetreten.",
		    JOptionPane.ERROR_MESSAGE);
	}
    }

    public static Icon getIcon(String name) {
	URL url = StgrIDE.class.getResource("icons/" + name);
	if (url == null)
	    throw new NullPointerException("Can not find " + name);

	Icon ico = new ImageIcon(url);
	return ico;
    }

    public void stateChanged(ChangeEvent e) {
	StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
		.getSelectedComponent();
	if (sel == null) {
	    m_saveDocument.setEnabled(false);
	    m_saveDocumentAs.setEnabled(false);
	    m_exportDocumentAs.setEnabled(false);
	    m_exportDocument.setEnabled(false);
	} else {
	    m_exportDocumentAs.setEnabled(true);
	    m_saveDocumentAs.setEnabled(true);
	    m_exportDocument.setEnabled(sel.getDocumentFile() != null);
	    m_saveDocument.setEnabled(sel.getDocumentFile() != null);
	}
    }

    public void saveDocumentAs() {
	if (m_fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
	    StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
		    .getSelectedComponent();
	    sel.setDocumentFile(m_fileChooser.getSelectedFile());
	    saveDocument();
	}
    }

    public void saveDocument() {
	StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
		.getSelectedComponent();

	if (sel != null) {
	    try {
		sel.saveDocument();
	    } catch (IOException e) {
		JOptionPane.showMessageDialog(this, "Fehler beim Schreiben",
			"Beim Schreiben der Datei '"
				+ sel.getDocumentFile().getAbsolutePath()
				+ "' trat ein Fehler auf.",
			JOptionPane.ERROR_MESSAGE);
	    } catch (NullPointerException npe) {
		saveDocumentAs();
	    }
	}
    }

    public void exportDocument(File file) {
	ImageWriter imgWriter = ImageIO.getImageWritersBySuffix("png").next();
	StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
		.getSelectedComponent();

	try {
	    MemoryCacheImageOutputStream output = new MemoryCacheImageOutputStream(
		    new FileOutputStream(file));
	    imgWriter.setOutput(output);

	    imgWriter.write(sel.generateModel().getImage());
	    output.flush();
	    output.close();
	    JOptionPane.showMessageDialog(this, file.getAbsolutePath()
		    + " saved");
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void exportDocument() {
	StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
		.getSelectedComponent();

	File file = new File(sel.getDocumentFile().getParent(), FilenameUtils
		.getBaseName(sel.getDocumentFile().getAbsolutePath())
		+ ".png");
	System.out.println(file.getAbsolutePath());
	exportDocument(file);
    }

    public void exportDocumentAs() {
	if (m_fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
	    exportDocument(m_fileChooser.getSelectedFile());
	}
    }

    class CloseApplicationAction extends AbstractAction {
	private static final long serialVersionUID = -1407878865322939597L;

	public CloseApplicationAction() {
	    putValue(NAME, "Programm schließen");
	    putValue(LONG_DESCRIPTION, "Beendet die Applikation.");
	    putValue(SMALL_ICON, StgrIDE.getIcon("process-stop.png"));
	}

	public void actionPerformed(ActionEvent e) {
	    setVisible(false);
	}
    }

    public class CloseDocumentAction extends AbstractAction {
	private static final long serialVersionUID = -8773424215752172201L;

	public CloseDocumentAction() {
	    putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_W,
		    InputEvent.CTRL_DOWN_MASK));
	    putValue(NAME, "Datei schließen ...");
	    putValue(LONG_DESCRIPTION, "Schließt akutelles Document.");
	    putValue(SMALL_ICON, StgrIDE.getIcon("media-record.png"));
	}

	public void actionPerformed(ActionEvent e) {
	    m_openFiles.remove(m_openFiles.getSelectedComponent());
	}
    }

    public class ExportAsDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 5715441439622728749L;

	public ExportAsDocumentAction(StgrIDE application) {
	    putValue(MNEMONIC_KEY, KeyEvent.VK_E);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_E,
		    InputEvent.CTRL_DOWN_MASK));
	    putValue(NAME, "Export as");
	    putValue(
		    LONG_DESCRIPTION,
		    "Exportiert das Struktogramm als Bild im ausgwählten Typ, fragt woher Dateinamen.");
	    putValue(SMALL_ICON, StgrIDE.getIcon("export.png"));
	}

	public void actionPerformed(ActionEvent e) {
	    if (m_fileChooser.showSaveDialog(StgrIDE.this) == JFileChooser.APPROVE_OPTION) {
		exportDocument(m_fileChooser.getSelectedFile());
	    }
	}
    }

    class ExportDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 5715441439622728749L;

	public ExportDocumentAction(StgrIDE application) {
	    putValue(MNEMONIC_KEY, KeyEvent.VK_X);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_X,
		    InputEvent.CTRL_DOWN_MASK));
	    putValue(NAME, "Export");
	    putValue(LONG_DESCRIPTION,
		    "Exportiert das Struktogramm als Bild im ausgwählten Typ.");
	    putValue(SMALL_ICON, StgrIDE.getIcon("document-save.png"));
	}

	public void actionPerformed(ActionEvent e) {
	    StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
		    .getSelectedComponent();

	    File file = new File(sel.getDocumentFile().getParent(),
		    FilenameUtils.getBaseName(sel.getDocumentFile()
			    .getAbsolutePath())
			    + ".png");
	    System.out.println(file.getAbsolutePath());
	    exportDocument(file);
	}
    }

    public class NewDocumentAction extends AbstractAction {
	private static final long serialVersionUID = -8773424215752172201L;

	public NewDocumentAction() {

	    putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_N,
		    InputEvent.CTRL_DOWN_MASK));
	    putValue(NAME, "Neue Datei");
	    putValue(LONG_DESCRIPTION, "Erstellt eine neue Datei.");
	    putValue(SMALL_ICON, StgrIDE.getIcon("document-new.png"));
	}

	public void actionPerformed(ActionEvent e) {
	    StgrAdvGuiDocument new_doc = new StgrAdvGuiDocument();
	    addDocument(new_doc);
	}
    }

    public class OpenDocumentAction extends AbstractAction {
	private static final long serialVersionUID = -9116135824721168221L;

	public OpenDocumentAction() {

	    putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_O,
		    InputEvent.CTRL_DOWN_MASK));
	    putValue(NAME, "Datei öffnen ...");
	    putValue(LONG_DESCRIPTION, "Öffnet ein neues Document.");
	    putValue(SMALL_ICON, StgrIDE.getIcon("document-open.png"));
	}

	public void actionPerformed(ActionEvent e) {
	    if (m_fileChooser.showOpenDialog(StgrIDE.this) == JFileChooser.APPROVE_OPTION) {
		for (File f : m_fileChooser.getSelectedFiles())
		    openFile(f);
	    }
	}
    }

    public class PreviewDocumentAction extends AbstractAction {
	private static final long serialVersionUID = 5715441439622728749L;

	public PreviewDocumentAction() {
	    putValue(MNEMONIC_KEY, KeyEvent.VK_P);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_P,
		    InputEvent.CTRL_DOWN_MASK));
	    putValue(NAME, "Preview");
	    putValue(LONG_DESCRIPTION, "Zeigt eine Vorschau der Datei an.");

	}

	public void actionPerformed(ActionEvent e) {
	    StgrAdvGuiDocument doc = (StgrAdvGuiDocument) m_openFiles
		    .getSelectedComponent();
	    doc.previewDocument();
	}
    }

    class SaveAsDocumentAction extends AbstractAction {
	private static final long serialVersionUID = -9116135824721168221L;

	public SaveAsDocumentAction() {

	    putValue(MNEMONIC_KEY, KeyEvent.VK_V);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_S,
		    InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
	    putValue(NAME, "Datei speichern als ...");
	    putValue(LONG_DESCRIPTION, "Speichert aktuelle Datei.");
	    putValue(SMALL_ICON, StgrIDE.getIcon("document-save-as.png"));
	}

	public void actionPerformed(ActionEvent e) {
	    if (m_fileChooser.showSaveDialog(StgrIDE.this) == JFileChooser.APPROVE_OPTION) {
		StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
			.getSelectedComponent();
		sel.setDocumentFile(m_fileChooser.getSelectedFile());
		saveDocument();
	    }
	}
    }

    public class SaveDocumentAction extends AbstractAction {
	private static final long serialVersionUID = -9116135824721168221L;

	public SaveDocumentAction() {
	    putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_S,
		    InputEvent.CTRL_DOWN_MASK));
	    putValue(NAME, "Datei speichern");
	    putValue(LONG_DESCRIPTION, "Speichert aktuelle Datei.");
	    putValue(SMALL_ICON, StgrIDE.getIcon("document-save.png"));
	}

	public void actionPerformed(ActionEvent e) {
	    StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
		    .getSelectedComponent();

	    if (sel != null) {
		try {
		    sel.saveDocument();
		} catch (IOException e1) {
		    JOptionPane.showMessageDialog(StgrIDE.this,
			    "Fehler beim Schreiben",
			    "Beim Schreiben der Datei '"
				    + sel.getDocumentFile().getAbsolutePath()
				    + "' trat ein Fehler auf.",
			    JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException npe) {
		    saveDocumentAs();
		}
	    }
	}
    }

    class FormatDocument extends AbstractAction {
	private static final long serialVersionUID = -9116135824721168221L;

	public FormatDocument() {
	    putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	    putValue(ACCELERATOR_KEY, KeyStroke.getAWTKeyStroke(KeyEvent.VK_F,
		    InputEvent.CTRL_DOWN_MASK));
	    putValue(NAME, "Format");
	    putValue(LONG_DESCRIPTION, "Formatiere das aktuelle Document");
	}

	public void actionPerformed(ActionEvent e) {
	    StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
		    .getSelectedComponent();
	    if (sel != null)
		sel.formatCode();
	}
    }
}
