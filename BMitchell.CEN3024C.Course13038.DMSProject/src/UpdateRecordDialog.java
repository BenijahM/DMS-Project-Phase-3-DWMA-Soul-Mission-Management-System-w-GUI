/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 29, 2026
 * Class: UpdateRecordDialog
 * Objective: This class provides a graphical form for modifying
 * existing SoulMissionRecord information. It retrieves and displays
 * the selected record's current information, allows users to modify
 * its fields, validates the updated information, and saves the
 * changes through RecordManager.
 */

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UpdateRecordDialog extends JDialog{
    private JPanel updateRecordPanel;
    private JLabel formTitleLabel;
    private JLabel recordID;
    private JTextField recordIdTextField;
    private JTextField teamNameTextField;
    private JLabel teamName;
    private JLabel partnerType;
    private JComboBox partnerComboBox;
    private JLabel kishinSouls;
    private JTextField kishinTextField;
    private JLabel witchSouls;
    private JTextField witchTextField;
    private JLabel trainingLevel;
    private JTextField trainingTextField;
    private JLabel activeStatus;
    private JComboBox activeStatusComboBox;
    private JLabel missionDate;
    private JTextField missionDateTextField;
    private JButton updateButton;
    private JButton cancelButton;

    private RecordManager recordManager;
    private InputValidator inputValidator;
    private SoulMissionRecord record;

    // Constructor

    public UpdateRecordDialog(
            JFrame parent,
            RecordManager recordManager,
            InputValidator inputValidator,
            SoulMissionRecord record) {

        this.recordManager = recordManager;
        this.inputValidator = inputValidator;
        this.record = record;

        setContentPane(updateRecordPanel);
        setTitle("Update Soul Mission Record");
        setModal(true);
        pack();
        setLocationRelativeTo(parent);

        // Load the existing record information.
        loadRecordData();

        // Button listeners.
        updateButton.addActionListener(e -> updateRecord());
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Method: loadRecordData
     * Purpose: Loads the existing SoulMissionRecord information into the
     * corresponding form fields so the user can view and modify the current data.
     * The record ID is displayed but cannot be edited.
     * Arguments: None.
     * Return: None.
     */
    private void loadRecordData() {
        recordIdTextField.setText(
                String.valueOf(record.getRecordId()));

        recordIdTextField.setEditable(false);

        teamNameTextField.setText(record.getTeamName());

        partnerComboBox.setSelectedItem(
                record.getPartnerType());

        kishinTextField.setText(
                String.valueOf(record.getKishinSoulsCollected()));

        witchTextField.setText(
                String.valueOf(record.getWitchSoulsCollected()));

        trainingTextField.setText(
                String.valueOf(record.getTrainingLevel()));

        activeStatusComboBox.setSelectedItem(
                String.valueOf(record.isActiveStatus()));

        missionDateTextField.setText(
                record.getLastMissionDate().toString());
    }

    /**
     * Method: updateRecord
     * Purpose: Retrieves the modified information from the update form,
     * converts the values into the appropriate data types, and validates
     * the updated information. Creates an updated SoulMissionRecord object
     * and saves the changes through RecordManager if all information is valid.
     * Arguments: None.
     * Return: None.
     */
    private void updateRecord() {

        // Retrieve information from the form.
        String name = teamNameTextField.getText().trim();

        String partnerType = String.valueOf(
                partnerComboBox.getSelectedItem());

        String kishinText = kishinTextField.getText().trim();
        String witchText = witchTextField.getText().trim();
        String trainingText = trainingTextField.getText().trim();

        String activeText = String.valueOf(
                activeStatusComboBox.getSelectedItem());

        String dateText = missionDateTextField.getText().trim();

        // Validate Team Name.
        String error = inputValidator.validateTeamName(name);

        if (!error.isEmpty()) {
            showError(error);
            return;
        }

        // Validate Partner Type.
        error = inputValidator.validatePartnerType(partnerType);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }


        // Convert and validate soul counts.
        int kishin;
        int witch;

        try {
            kishin = Integer.parseInt(kishinText);
            witch = Integer.parseInt(witchText);

        } catch (NumberFormatException e) {
            showError("Soul counts must be whole numbers.");
            return;
        }

        error = inputValidator.validateKishinSouls(kishin);
        if (!error.isEmpty()) {
            showError("Kishin souls: " + error);
            return;
        }

        error = inputValidator.validateWitchSouls(witch);
        if (!error.isEmpty()) {
            showError("Witch souls: " + error);
            return;
        }

        // Convert and validate training level.
        double training;
        try {
            training = Double.parseDouble(trainingText);

        } catch (NumberFormatException e) {
            showError("Training level must be a number.");
            return;
        }

        error = inputValidator.validateTrainingLevel(training);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }

        // Validate Active Status.
        if (!activeText.equalsIgnoreCase("true")
                && !activeText.equalsIgnoreCase("false")) {

            showError("Please select true or false for active status.");
            return;
        }

        boolean active = Boolean.parseBoolean(activeText);

        // Validate Mission Date.
        error = inputValidator.validateDate(dateText);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }

        LocalDate missionDate;
        try {
            missionDate = LocalDate.parse(dateText);

        } catch (DateTimeParseException e) {
            showError("Please enter a valid date in yyyy-MM-dd format.");
            return;
        }

        // Create an updated record using the original record ID.
        SoulMissionRecord updatedRecord = new SoulMissionRecord(
                record.getRecordId(),
                name,
                partnerType,
                kishin,
                witch,
                training,
                active,
                missionDate
        );

        // Validate the complete updated record.
        error = inputValidator.validateRecord(updatedRecord);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }

        // Save the updated record.
        if (recordManager.updateRecord(updatedRecord)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Soul Mission Record updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showError("Record could not be updated.");}
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
