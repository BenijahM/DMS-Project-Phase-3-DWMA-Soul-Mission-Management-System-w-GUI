/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 29, 2026
 * Class: ImporterDialog
 * Objective: This class provides a graphical interface for importing
 * SoulMissionRecord objects from an external text file. It allows
 * users to browse for and select a file, initiate the import process,
 * and view a report displaying the number of successfully imported
 * and rejected records.
 */

import javax.swing.*;
import java.io.File;

public class ImporterDialog extends JDialog{
    private JTextField fileTextField;
    private JButton browseButton;
    private JButton importButton;
    private JButton cancelButton;
    private JPanel importRecordsPanel;
    private JLabel formTitleLabel;
    private JLabel filePathLabel;
    private JPanel importResultsPanel;
    private JTextArea importResultsTextArea;
    private JScrollPane resultScrollPane;

    private RecordManager recordManager;
    private FileImporter fileImporter;

    // Constructor
    public ImporterDialog(
            JFrame parent,
            RecordManager recordManager,
            FileImporter fileImporter) {

        this.recordManager = recordManager;
        this.fileImporter = fileImporter;

        setContentPane(importRecordsPanel);
        setTitle("Import Soul Mission Records");
        setModal(true);
        pack();
        setLocationRelativeTo(parent);

        // Make the import results area non-editable.
        importResultsTextArea.setEditable(false);

        // Button listeners.
        browseButton.addActionListener(e -> browseForFile());
        importButton.addActionListener(e -> importRecords());
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Method: browseForFile
     * Purpose: Opens a file chooser that allows the user to select
     * a text file containing SoulMissionRecord information. The
     * selected file's path is displayed in the file path text field.
     * Arguments: None.
     * Return: None.
     */
    private void browseForFile() {

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Select Soul Mission Records File");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();

            fileTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Method: importRecords
     * Purpose: Retrieves the selected file path and imports the
     * SoulMissionRecord objects using FileImporter. Displays the
     * import results in the GUI and informs the user when the
     * import process is complete.
     * Arguments: None.
     * Return: None.
     */
    private void importRecords() {

        String filePath = fileTextField.getText().trim();

        if (filePath.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a file to import.",
                    "Import Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Import records using the existing GUI method.
        String results = fileImporter.importRecordsForGUI(
                filePath,
                recordManager
        );

        // Display import results.
        importResultsTextArea.setText(results);
        importResultsTextArea.setCaretPosition(0);
    }
}
