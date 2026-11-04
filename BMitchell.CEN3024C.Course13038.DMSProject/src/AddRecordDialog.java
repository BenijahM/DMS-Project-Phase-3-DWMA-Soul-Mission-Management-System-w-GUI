/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 29, 2026
 * Class: AddRecordDialog
 * Objective: This class provides a graphical form for entering new
 * SoulMissionRecord information. It collects the record ID, team name,
 * partner type, souls collected, training level, active status, and
 * last mission date. It validates the entered information and adds
 * the new record to RecordManager if all information is valid and
 * the record ID is unique.
 */

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddRecordDialog extends JDialog {
    private JPanel addRecordPanel;
    private JLabel formTitleLabel;
    private JLabel recordId;
    private JTextField recordIdTextField;
    private JLabel teamName;
    private JLabel partnerType;
    private JLabel kishinSouls;
    private JLabel witchSouls;
    private JLabel trainingLevel;
    private JLabel activeStatus;
    private JLabel missionDate;
    private JTextField teamNameTextField;
    private JTextField kishinSoulsTextField;
    private JTextField witchSoulsTextField;
    private JTextField trainingLevelTextField;
    private JTextField missionDateTextField;
    private JComboBox partnerTypeComboBox;
    private JComboBox activeStatuscomboBox;
    private JButton cancelButton;
    private JButton saveButton;
    private JPanel buttonPanel;

    private RecordManager recordManager;
    private InputValidator inputValidator;

    // Constructor
    public AddRecordDialog(JFrame parent, RecordManager recordManager, InputValidator inputValidator) {
        this.recordManager = recordManager;
        this.inputValidator = inputValidator;

        setContentPane(addRecordPanel);
        setTitle("Add New Soul Mission Record");
        setModal(true);
        pack();
        setLocationRelativeTo(parent);

        saveButton.addActionListener(e -> saveRecord());
        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Method:
     * Purpose: Validates the information entered in the Add Record form,
     * creates a new SoulMissionRecord object, and adds it to RecordManager
     * if all information is valid and the record ID is unique.
     * Arguments: None
     * Return:None
     */
    private void saveRecord() {

        // Read information from the text fields
        String idText = recordIdTextField.getText().trim();
        String name = teamNameTextField.getText().trim();
        String partnerType = String.valueOf(partnerTypeComboBox.getSelectedItem());

        String kishinText = kishinSoulsTextField.getText().trim();
        String witchText = witchSoulsTextField.getText().trim();
        String trainingText = trainingLevelTextField.getText().trim();

        String activeText = String.valueOf(activeStatuscomboBox.getSelectedItem());
        String dateText = missionDateTextField.getText().trim();


        // Validate Record ID
        String error = inputValidator.validateRecordId(idText);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }

        int id = Integer.parseInt(idText);

        // Check for duplicate IDs
        if (recordManager.containsId(id)) {
            showError("That record ID already exists.");
            return;
        }

        // Validate Team Name
        error = inputValidator.validateTeamName(name);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }

        // Validate Partner Type
        error = inputValidator.validatePartnerType(partnerType);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }


        // Convert and validate soul counts
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


        // Convert and validate training level
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


        // Validate Active Status
        if (!activeText.equalsIgnoreCase("true")
                && !activeText.equalsIgnoreCase("false")) {
            showError("Please select true or false for active status.");
            return;
        }

        boolean active = Boolean.parseBoolean(activeText);

        // Validate Mission Date
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


        // Create the new record
        SoulMissionRecord record = new SoulMissionRecord(id, name, partnerType, kishin,
                witch, training, active, missionDate);


        // Validate the complete record
        error = inputValidator.validateRecord(record);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }


        // Add record to the ArrayList
        if (recordManager.addRecord(record)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Soul Mission Record added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } else {
            showError("Record could not be added.");
        }
    }

    /**
     * Method: showError
     * Purpose: Displays an error message in a graphical dialog box to
     * inform the user when invalid information is entered or an operation
     * cannot be completed.
     * Arguments: message (a string with the error message to display).
     * Return:
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
