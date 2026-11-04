/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 29, 2026
 * Class: DeleteRecordDialog
 * Objective: This class provides a graphical form for deleting
 * existing SoulMissionRecord objects. It allows users to search
 * for a record using its ID, displays the matching record's
 * information, and requests confirmation before permanently
 * removing the selected record from RecordManager.
 */

import javax.swing.*;

public class DeleteRecordDialog extends JDialog{
    private JPanel deleteRecordPanel;
    private JLabel formTitleLabel;
    private JLabel recordIdLabel;
    private JTextField recordIdTextField;
    private JButton searchButton;
    private JPanel searchButtonPanel;
    private JLabel recordInfoLabel;
    private JTextArea recordInfoTextArea;
    private JPanel buttonPanel;
    private JButton deleteButton;
    private JButton cancelButton;

    private RecordManager recordManager;
    private InputValidator inputValidator;
    private SoulMissionRecord selectedRecord;

    // Constructor

    public DeleteRecordDialog(
            JFrame parent,
            RecordManager recordManager,
            InputValidator inputValidator) {

        this.recordManager = recordManager;
        this.inputValidator = inputValidator;

        setContentPane(deleteRecordPanel);
        setTitle("Delete Soul Mission Record");
        setModal(true);
        pack();
        setLocationRelativeTo(parent);

        // Make the record information area non-editable.
        recordInfoTextArea.setEditable(false);

        // Initially disable the Delete button.
        deleteButton.setEnabled(false);

        // Button listeners.
        searchButton.addActionListener(e -> searchRecord());
        deleteButton.addActionListener(e -> deleteRecord());
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Method: searchRecord
     * Purpose: Retrieves the record ID entered by the user, validates the ID,
     * and searches for the corresponding SoulMissionRecord in RecordManager.
     * If a matching record is found, its information is displayed and the
     * Delete button is enabled. Otherwise, an error message is displayed.
     * Arguments: None.
     * Return: None.
     */
    private void searchRecord() {

        String idText = recordIdTextField.getText().trim();

        // Validate Record ID.
        String error = inputValidator.validateRecordId(idText);

        if (!error.isEmpty()) {
            showError(error);
            return;
        }

        int id = Integer.parseInt(idText);

        // Search for the record.
        selectedRecord = recordManager.findById(id);

        if (selectedRecord == null) {
            recordInfoTextArea.setText("");
            deleteButton.setEnabled(false);

            showError("No record found with ID " + id + ".");
            return;
        }

        // Display the selected record's information.
        recordInfoTextArea.setText(
                "Record ID: " + selectedRecord.getRecordId() + "\n"
                        + "Team Name: " + selectedRecord.getTeamName() + "\n"
                        + "Partner Type: " + selectedRecord.getPartnerType() + "\n"
                        + "Kishin Souls Collected: "
                        + selectedRecord.getKishinSoulsCollected() + "\n"
                        + "Witch Souls Collected: "
                        + selectedRecord.getWitchSoulsCollected() + "\n"
                        + "Training Level: "
                        + selectedRecord.getTrainingLevel() + "\n"
                        + "Active Status: "
                        + selectedRecord.isActiveStatus() + "\n"
                        + "Last Mission Date: "
                        + selectedRecord.getLastMissionDate()
        );

        // Enable the Delete button.
        deleteButton.setEnabled(true);
    }

    /**
     * Method: deleteRecord
     * Purpose: Displays the selected record's information and asks the user
     * to confirm deletion. If the user confirms, the record is removed from
     * RecordManager. If the user cancels, the record remains unchanged.
     * Arguments: None.
     * Return: None.
     */
    private void deleteRecord() {

        if (selectedRecord == null) {
            showError("Please search for a record first.");
            return;
        }

        // Ask the user to confirm deletion.
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this record?\n\n"
                        + selectedRecord,
                "Confirm Record Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation == JOptionPane.YES_OPTION) {

            int id = selectedRecord.getRecordId();

            if (recordManager.deleteRecord(id)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Soul Mission Record deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {
                showError("The record could not be deleted.");
            }
        }
    }

    /**
     * Method: showError
     * Purpose: Displays an error message in a graphical dialog box to
     * inform the user when invalid information is entered or an operation
     * cannot be completed.
     * Arguments: message (a String containing the error message to display).
     * Return: None.
     */
    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Input Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

