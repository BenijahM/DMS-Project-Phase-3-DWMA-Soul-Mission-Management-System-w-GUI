/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 29, 2026
 * Class: MainFrame
 * Objective: This class serves as the main graphical user interface
 * for the DWMA Soul Mission Management System. It displays all
 * stored SoulMissionRecord objects in a JTable and provides buttons
 * and functions for adding, updating, deleting, searching, importing,
 * and calculating the qualification progress of records. It also
 * manages navigation between the application's GUI forms.
 */

import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame {
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JLabel titleLabel;
    private JLabel searchLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JTable recordTable;
    private JScrollPane ScrollPane;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton updateButton;
    private JButton refreshButton;
    private JButton deleteButton;
    private JButton progressButton;
    private JButton importButton;
    private JButton exitButton;
    private JLabel statusLabel;
    private JLabel emblemLabel;
    private JLabel yellowCircleLabel;
    private JLabel whiteCircleLabel;
    private JLabel blueCircleLabel;

    private RecordManager recordManager;
    private InputValidator inputValidator;
    private ProgressCalculator progressCalculator;
    private DefaultTableModel tableModel;
    private FileImporter fileImporter;

    // Constructor
    public MainFrame() {
        // Initialize the classes
        recordManager = new RecordManager();
        inputValidator = new InputValidator();
        progressCalculator = new ProgressCalculator();
        fileImporter = new FileImporter(inputValidator);

        setupTable();
        refreshTable();

        // Opens Add Record window
        addButton.addActionListener(e -> {
            AddRecordDialog dialog = new AddRecordDialog(null, recordManager, inputValidator);
            dialog.setVisible(true);
            refreshTable();
        });

        // Opens Delete Record window.
        deleteButton.addActionListener(e -> {
            DeleteRecordDialog dialog = new DeleteRecordDialog(null, recordManager, inputValidator);
            dialog.setVisible(true);
            refreshTable();
        });

        // Opens Qualification Progress window.
        progressButton.addActionListener(e -> {
            ProgressCalcDialog dialog = new ProgressCalcDialog(null, recordManager, inputValidator,
                    progressCalculator);
            dialog.setVisible(true);
        });

        // Listeners for all of the Buttons.
        searchButton.addActionListener(e -> searchRecords());
        refreshButton.addActionListener(e -> {
            refreshTable();
            searchField.setText("");
            statusLabel.setText("All records displayed.");});
        exitButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(mainPanel);
            confirmExit(window);});
        importButton.addActionListener(e -> importRecords());
        updateButton.addActionListener(e -> updateSelectedRecord());

    }

    /**
     * Method: setupTable
     * Purpose: Creates and configures the JTable used to display all SoulMissionRecord objects.
     * Defines the column names and prevents users from directly editing record
     * information inside the table.
     * Arguments: None
     * Return: None
     */
    private void setupTable() {

        String[] columns = {
                "Record ID",
                "Team Name",
                "Partner Type",
                "Kishin Souls",
                "Witch Souls",
                "Training Level",
                "Active",
                "Last Mission Date"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recordTable.setModel(tableModel);
    }

    /**
     * Method: refreshTable
     * Purpose: Refreshes the JTable by removing its existing rows, retrieving all stored
     * SoulMissionRecord objects from RecordManager, and displaying their information in
     * the table.
     * Arguments: None
     * Return: None
     */
    private void refreshTable() {
        tableModel.setRowCount(0);

        for (SoulMissionRecord record : recordManager.getAllRecordsList()) {

            Object[] row = {
                    record.getRecordId(),
                    record.getTeamName(),
                    record.getPartnerType(),
                    record.getKishinSoulsCollected(),
                    record.getWitchSoulsCollected(),
                    record.getTrainingLevel(),
                    record.isActiveStatus(),
                    record.getLastMissionDate()
            };

            tableModel.addRow(row);
        }
    }

    /**
     * Method: searchRecords
     * Purpose: Searches for a SoulMissionRecord using either its record ID
     * or team name. Validates the search input, displays an error message
     * if the input is invalid or no matching record is found, and updates
     * the JTable to display the matching record.
     * Arguments: None
     * Return:None
     */
    private void searchRecords() {

        String searchText = searchField.getText().trim();

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Please enter a Record ID or Team Name.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        SoulMissionRecord record = null;

        // Search by Record ID if the input contains only digits.
        if (searchText.matches("[0-9]+")) {

            try {
                int id = Integer.parseInt(searchText);
                String error = inputValidator.validateRecordId(searchText);

                if (!error.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            mainPanel,
                            error,
                            "Invalid Record ID",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                record = recordManager.findById(id);

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        mainPanel,
                        "Invalid Record ID.",
                        "Search Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

        } else {

            record = recordManager.findByTeamName(searchText);
        }

        if (record == null) {

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "No matching record was found.",
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        // Clear the table and display only the matching record.
        tableModel.setRowCount(0);

        Object[] row = {
                record.getRecordId(),
                record.getTeamName(),
                record.getPartnerType(),
                record.getKishinSoulsCollected(),
                record.getWitchSoulsCollected(),
                record.getTrainingLevel(),
                record.isActiveStatus(),
                record.getLastMissionDate()
        };

        tableModel.addRow(row);

        statusLabel.setText("Record found: " + record.getTeamName());
    }

    /**
     * Method: importRecords
     * Purpose: Opens the ImporterDialog, allowing the user to select
     * a text file and import SoulMissionRecord objects into the system.
     * Refreshes the JTable after the dialog closes so newly imported
     * records are displayed.
     * Arguments: None.
     * Return: None.
     */
    private void importRecords() {

        ImporterDialog dialog = new ImporterDialog(
                null,
                recordManager,
                fileImporter
        );

        dialog.setVisible(true);

        // Refresh the table after the import dialog closes.
        refreshTable();

        statusLabel.setText("Record information refreshed.");
    }

    /**
     * Method: updateSelectedRecord
     * Purpose: Retrieves the record selected in the JTable and opens the
     * UpdateRecordDialog so the user can modify its information. Displays
     * an error message if no record is selected. Refreshes the table after
     * the update dialog closes.
     * Arguments: None.
     * Return: None.
     */
    private void updateSelectedRecord() {

        // Get the selected row.
        int selectedRow = recordTable.getSelectedRow();

        // Check whether a record was selected.
        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "Please select a record to update.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Get the record ID from the selected row.
        int recordId = (int) tableModel.getValueAt(selectedRow, 0);

        // Find the corresponding record.
        SoulMissionRecord record = recordManager.findById(recordId);

        if (record == null) {

            JOptionPane.showMessageDialog(
                    mainPanel,
                    "The selected record could not be found.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Open the Update Record dialog.
        UpdateRecordDialog dialog = new UpdateRecordDialog(
                null,
                recordManager,
                inputValidator,
                record
        );

        dialog.setVisible(true);

        // Refresh the table after the dialog closes.
        refreshTable();

        statusLabel.setText("Record information refreshed.");
    }

    /**
     * Method: confirmExit
     * Purpose: Asks the user to confirm before closing the application.
     * Arguments: window (the application window).
     * Return: None.
     */
    private void confirmExit(Window window) {

        int choice = JOptionPane.showConfirmDialog(
                mainPanel,
                "Are you sure you want to exit the DWMA Soul Mission Management System?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            window.dispose();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("DWMA Soul Mission Management System");
            MainFrame mainFrame = new MainFrame();
            frame.setContentPane(mainFrame.mainPanel);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    mainFrame.confirmExit(frame);
                }
            });
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
