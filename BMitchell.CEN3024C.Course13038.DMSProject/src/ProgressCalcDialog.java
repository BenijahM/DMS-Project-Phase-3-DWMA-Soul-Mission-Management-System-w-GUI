/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 29, 2026
 * Class: ProgressCalcDialog
 * Objective: This class provides a graphical interface for
 * calculating and displaying a meister and weapon team's progress
 * toward Death Scythe qualification. It allows users to search
 * for a record using its ID, displays the qualification progress
 * report, and uses a progress bar to visually represent the
 * team's percentage of completion.
 */

import javax.swing.*;

public class ProgressCalcDialog extends JDialog {

    private JPanel progressCalcPanel;
    private JLabel formTitleLabel;
    private JLabel recordIdLabel;
    private JTextField recordIdTextField;
    private JButton searchButton;
    private JTextArea progressTextArea;
    private JProgressBar progressBar;
    private JButton closeButton;
    private JLabel recordInfoLabel;

    private RecordManager recordManager;
    private InputValidator inputValidator;
    private ProgressCalculator progressCalculator;

    // Constructor
    public ProgressCalcDialog(JFrame parent, RecordManager recordManager, InputValidator inputValidator,
            ProgressCalculator progressCalculator) {

        this.recordManager = recordManager;
        this.inputValidator = inputValidator;
        this.progressCalculator = progressCalculator;

        setContentPane(progressCalcPanel);
        setTitle("Death Scythe Qualification Progress");
        setModal(true);
        pack();
        setLocationRelativeTo(parent);

        // Make the progress information area non-editable.
        progressTextArea.setEditable(false);

        // Configure the progress bar.
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        // Button listeners.
        searchButton.addActionListener(e -> calculateProgress());
        closeButton.addActionListener(e -> dispose());
    }

    /**
     * Method: calculateProgress
     * Purpose: Retrieves and validates the record ID entered by the user,
     * searches for the corresponding SoulMissionRecord, and calculates
     * its Death Scythe qualification progress using ProgressCalculator.
     * Displays the qualification report and updates the progress bar.
     * Arguments: None.
     * Return: None.
     */
    private void calculateProgress() {
        // Retrieve the record ID.
        String idText = recordIdTextField.getText().trim();

        // Validate the record ID.
        String error = inputValidator.validateRecordId(idText);
        if (!error.isEmpty()) {
            showError(error);
            return;
        }

        int id = Integer.parseInt(idText);

        // Search for the record.
        SoulMissionRecord record = recordManager.findById(id);
        if (record == null) {
            progressTextArea.setText("");
            progressBar.setValue(0);

            showError("No record found with ID " + id + ".");
            return;
        }

        // Calculate qualification progress.
        double progress = progressCalculator.calculatePercentage(record);
        int soulsNeeded = progressCalculator.calculateSoulsNeeded(record);
        boolean qualified = progressCalculator.isQualified(record);

        // Display the progress report.
        progressTextArea.setText(
                "Record ID: " + record.getRecordId() + "\n"
                        + "Team Name: " + record.getTeamName() + "\n\n"
                        + "Kishin Souls Collected: "
                        + record.getKishinSoulsCollected() + " / 99\n"
                        + "Witch Souls Collected: "
                        + record.getWitchSoulsCollected() + " / 1\n\n"
                        + "Qualification Progress: " + progress + "%\n"
                        + "Souls Still Needed: " + soulsNeeded + "\n"
                        + "Qualification Status: "
                        + (qualified ? "Qualified!" : "Not Yet Qualified")
        );

        // Update the progress bar.
        progressBar.setValue((int) progress);

        // Update the record information label.
        recordInfoLabel.setText("Qualification Results for " + record.getTeamName());
    }

    /**
     * Method: showError
     * Purpose: Displays an error message in a graphical dialog box to
     * inform the user when an invalid record ID is entered, a record
     * cannot be found, or an operation cannot be completed.
     * Arguments: message (a String containing the error message to display).
     * Return: None.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Progress Calculation Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
