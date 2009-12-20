package weigl.stgr.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FilenameUtils;
import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXErrorDialog;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;

import weigl.stgr.controller.FileFilters;
import weigl.stgr.exception.NoSuitableBuilderFoundException;
import weigl.stgr.gui.action.CloseApplicationAction;
import weigl.stgr.gui.action.CloseDocumentAction;
import weigl.stgr.gui.action.ExportAsDocumentAction;
import weigl.stgr.gui.action.ExportDocumentAction;
import weigl.stgr.gui.action.NewDocumentAction;
import weigl.stgr.gui.action.OpenDocumentAction;
import weigl.stgr.gui.action.PreviewDocumentAction;
import weigl.stgr.gui.action.SaveAsDocumentAction;
import weigl.stgr.gui.action.SaveDocumentAction;

public class StgrAdvGui extends JXFrame implements ChangeListener {
	private static final long serialVersionUID = 6257836907736132965L;

	private Action m_openDocument = new OpenDocumentAction(this);
	private Action m_closeDocument = new CloseDocumentAction(this);
	private Action m_newDocuemnt = new NewDocumentAction(this);
	private Action m_closeApp = new CloseApplicationAction(this);
	private Action m_saveDocumentAs = new SaveAsDocumentAction(this);
	private Action m_saveDocument = new SaveDocumentAction(this);
	private Action m_previewDocument = new PreviewDocumentAction(this);
	private Action m_exportDocument = new ExportDocumentAction(this);
	private Action m_exportDocumentAs = new ExportAsDocumentAction(this);

	private JXComboBox m_exportTypes = new JXComboBox(ExportDocumentAction
			.getSuffixes());
	private PreviewPanel m_preview = createPreview();
	private JXTaskPaneContainer m_taskPaneContainer = createTaskPaneContainer();
	private JFileChooser m_fileChooser = createFileChooser();
	private JTabbedPane m_openFiles = new JTabbedPane();

	private StgrAdvGuiDocument m_default = new StgrAdvGuiDocument();

	public StgrAdvGui() {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Advanced Struktogramm IDE");
		setContentPane(createContentPane());
		addDocument(m_default);
		m_openFiles.addChangeListener(this);
		pack();
	}

	private JPanel createContentPane() {
		JPanel p = new JPanel(new BorderLayout(0, 0));
		p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		p.add(m_taskPaneContainer, BorderLayout.WEST);
		// p.add(m_preview, BorderLayout.EAST);
		// p.add(m_openFiles);
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				m_openFiles, m_preview);
		p.add(sp);
		return p;
	}

	private PreviewPanel createPreview() {
		final PreviewPanel pp = new PreviewPanel(this);
		return pp;
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
	private JXTaskPaneContainer createTaskPaneContainer() {
		JXTaskPaneContainer container = new JXTaskPaneContainer();
		JXTaskPane taskPane = new JXTaskPane();
		taskPane.setTitle("Application");
		taskPane.setSpecial(true);
		taskPane.add(m_newDocuemnt);
		taskPane.add(m_openDocument);
		taskPane.add(m_closeApp);
		container.add(taskPane);

		taskPane = new JXTaskPane();
		taskPane.setTitle("Document");
		taskPane.add(m_previewDocument);
		taskPane.add(m_saveDocument);
		taskPane.add(m_saveDocumentAs);
		container.add(taskPane);

		taskPane = new JXTaskPane();
		taskPane.setTitle("Exportieren");
		taskPane.add(new JLabel("Format wählen:"));
		taskPane.add(m_exportTypes);
		taskPane.add(m_exportDocument);
		taskPane.add(m_exportDocumentAs);

		container.add(taskPane);
		return container;
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
			JXErrorDialog
					.showDialog(
							this,
							"Programmfehler",
							"Zur der Datei '"
									+ file.getAbsolutePath()
									+ "' konnte kein passender Builder gefunden werden.\nBitte überprüfen Sie die Dateisuffix.",
							e);
		} catch (IOException e) {
			JXErrorDialog.showDialog(this, "Fehler beim Lesen der Datei",
					"Beim Einlesen der Datei '" + file.getAbsolutePath()
							+ "' ist ein Fehler aufgetreten.", e);
		}
	}

	public void closeDocument() {
		m_openFiles.remove(m_openFiles.getSelectedComponent());
	}

	public void previewDocument() {

		try {
			StgrAdvGuiDocument doc = (StgrAdvGuiDocument) m_openFiles
					.getSelectedComponent();
			m_preview.setModel(doc.generateModel());

			if (!m_preview.isVisible()) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						m_preview.setVisible(true);
						m_preview.requestFocus();
					}
				});
			}
			m_preview.requestFocus();
		} catch (Exception e) {
			JXErrorDialog.showDialog(this, e.getMessage(), e);
		}
	}

	public static Icon getIcon(String name) {
		URL url = StgrAdvGui.class.getResource("icons/" + name);
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
				JXErrorDialog.showDialog(this, "Fehler beim Schreiben",
						"Beim Schreiben der Datei '"
								+ sel.getDocumentFile().getAbsolutePath()
								+ "' trat ein Fehler auf.", e);
			} catch (NullPointerException npe) {
				saveDocumentAs();
			}
		}
	}

	public void exportDocument(File file) {
		ImageWriter imgWriter = ImageIO.getImageWritersBySuffix(
				m_exportTypes.getSelectedItem().toString()).next();
		StgrAdvGuiDocument sel = (StgrAdvGuiDocument) m_openFiles
				.getSelectedComponent();

		try {
			MemoryCacheImageOutputStream output = new MemoryCacheImageOutputStream(
					new FileOutputStream(file));
			imgWriter.setOutput(output);

			imgWriter.write(sel.generateModel().getImage(
					getFontMetrics(new Font(Font.MONOSPACED, Font.PLAIN, 14))));
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
				+ "." + m_exportTypes.getSelectedItem().toString());
		System.out.println(file.getAbsolutePath());
		exportDocument(file);
	}

	public void exportDocumentAs() {
		if (m_fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			exportDocument(m_fileChooser.getSelectedFile());
		}
	}

	public Action getOpenDocument() {
		return m_openDocument;
	}

	public Action getCloseDocument() {
		return m_closeDocument;
	}

	public Action getNewDocuemnt() {
		return m_newDocuemnt;
	}

	public Action getCloseApp() {
		return m_closeApp;
	}

	public Action getSaveDocumentAs() {
		return m_saveDocumentAs;
	}

	public Action getSaveDocument() {
		return m_saveDocument;
	}

	public Action getPreviewDocument() {
		return m_previewDocument;
	}

	public Action getExportDocument() {
		return m_exportDocument;
	}

	public Action getExportDocumentAs() {
		return m_exportDocumentAs;
	}
}
