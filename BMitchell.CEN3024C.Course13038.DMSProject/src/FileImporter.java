
/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 17, 2026
 * Class: FileImporter
 * Objective: This class handles importing soul mission records from a text
 * file into the DWMA Soul Mission Management System. It reads each record,
 * separates the information into the required fields, validates the data,
 * and creates SoulMissionRecord objects that can be added to the collection.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FileImporter {

    private InputValidator inputValidator;

    // Constructor
    public FileImporter(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    /**
     * Method: importRecords
     * Purpose: Reads soul mission records from a text file, converts valid
     * records into SoulMissionRecord objects, and adds them to the record
     * collection. Invalid records are skipped and a warning message is displayed.
     * Arguments:
     * fileName (the name or path of the text file containing records).
     * recordManager (the RecordManager used to store the imported records).
     * Return: None. Record information and import results are displayed
     * directly to the console.
     */
    public void importRecords(String fileName, RecordManager recordManager) {
        if (fileName == null || fileName.isBlank()) {
            System.out.println("** Error: File path cannot be blank. **");
            return;
        }

        if (recordManager == null || inputValidator == null) {
            System.out.println("** Error: Importer is not configured correctly. **");
            return;
        }

        File file = new File(fileName);

        try (Scanner fileScanner = new Scanner(file)) {

            if (!fileScanner.hasNextLine()) {
                System.out.println("** The input file is empty. **");
                return;
            }

            int importedCount = 0;
            int rejectedCount = 0;
            int lineNumber = 0;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                lineNumber++;

                if (line.isBlank()) {
                    System.out.println("** Empty record skipped on line "
                            + lineNumber + ". **");
                    rejectedCount++;
                    continue;
                }

                SoulMissionRecord record = parseRecordLine(line);

                if (record == null) {
                    System.out.println("** Invalid record skipped on line "
                            + lineNumber + ": " + line + " **");
                    rejectedCount++;
                    continue;
                }

                if (recordManager.containsId(record.getRecordId())) {
                    System.out.println("** Duplicate record ID skipped: "
                            + record.getRecordId() + " **");
                    rejectedCount++;
                    continue;
                }

                if (recordManager.addRecord(record)) {
                    importedCount++;
                } else {
                    System.out.println("** Record could not be added: "
                            + record.getRecordId() + " **");
                    rejectedCount++;
                }
            }

            System.out.println("** Import completed. **");
            System.out.println("** Records imported successfully: "
                    + importedCount + " **");
            System.out.println("** Records rejected: "
                    + rejectedCount + " **");

        } catch (FileNotFoundException e) {
            System.out.println(
                    "** Error: The specified file could not be found. **");
        } catch (SecurityException e) {
            System.out.println(
                    "** Error: Permission denied when accessing the file. **");
        }
    }

    /**
     * Method: importRecordsForGUI
     * Purpose: Imports records from a text file and returns the results
     * as a String so they can be displayed in the GUI.
     * Arguments: fileName and recordManager.
     * Return: A report containing the number of imported and rejected records.
     */
    public String importRecordsForGUI(String fileName, RecordManager recordManager) {

        if (fileName == null || fileName.isBlank()) {
            return "Error: File path cannot be blank.";
        }

        if (recordManager == null || inputValidator == null) {
            return "Error: Importer is not configured correctly.";
        }

        int importedCount = 0;
        int rejectedCount = 0;
        int lineNumber = 0;

        StringBuilder report = new StringBuilder();

        try (Scanner fileScanner = new Scanner(new File(fileName))) {

            if (!fileScanner.hasNextLine()) {
                return "The selected file is empty.";
            }

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                lineNumber++;

                if (line.isBlank()) {
                    rejectedCount++;
                    report.append("Line ")
                            .append(lineNumber)
                            .append(": Empty record rejected.\n");
                    continue;
                }

                SoulMissionRecord record = parseRecordLine(line);

                if (record == null) {
                    rejectedCount++;
                    report.append("Line ")
                            .append(lineNumber)
                            .append(": Invalid record rejected.\n");
                    continue;
                }

                if (recordManager.containsId(record.getRecordId())) {
                    rejectedCount++;
                    report.append("Line ")
                            .append(lineNumber)
                            .append(": Duplicate ID ")
                            .append(record.getRecordId())
                            .append(" rejected.\n");
                    continue;
                }

                if (recordManager.addRecord(record)) {
                    importedCount++;
                } else {
                    rejectedCount++;
                    report.append("Line ")
                            .append(lineNumber)
                            .append(": Record could not be added.\n");
                }
            }

            report.insert(0,
                    "IMPORT RESULTS\n"
                            + "------------------------------\n"
                            + "Successfully imported: " + importedCount + "\n"
                            + "Rejected records: " + rejectedCount + "\n"
                            + "------------------------------\n\n");

            if (rejectedCount == 0) {
                report.append("All records were imported successfully!");
            }

            return report.toString();

        } catch (FileNotFoundException e) {

            return "Error: The selected file could not be found.";

        } catch (SecurityException e) {

            return "Error: Permission denied when accessing the file.";

        }
    }

    /**
     * Method: parseRecordLine
     * Purpose: Separates one line from the input file into the record's
     * ID, team name, partner type, kishin souls, witch souls, training
     * level, active status, and last mission date. It creates a
     * SoulMissionRecord object if all required data is valid.
     * Arguments: line (one complete soul mission record read from the file).
     * Return: A SoulMissionRecord object if the record is valid; otherwise, null.
     */
    public SoulMissionRecord parseRecordLine(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        // Separating the eight required fields.
        String[] fields = line.split(",", -1);

        if (fields.length != 8) {
            return null;
        }

        String idText = fields[0].trim();
        String teamName = fields[1].trim();
        String partnerType = fields[2].trim();
        String kishinText = fields[3].trim();
        String witchText = fields[4].trim();
        String trainingText = fields[5].trim();
        String activeText = fields[6].trim();
        String dateText = fields[7].trim();

        // Validating that the required fields are not blank.
        for (String field : fields) {
            if (field.isBlank()) {
                return null;
            }
        }

        try {
            // Validate the ID before converting it to an integer.
            if (!inputValidator.validateRecordId(idText).isEmpty()) {
                return null;
            }

            int recordId = Integer.parseInt(idText);

            if (inputValidator.validateTeamName(teamName).length() > 0) {
                return null;
            }

            if (inputValidator.validatePartnerType(partnerType).length() > 0) {
                return null;
            }

            int kishinSouls = Integer.parseInt(kishinText);
            int witchSouls = Integer.parseInt(witchText);
            double trainingLevel = Double.parseDouble(trainingText);

            if (!inputValidator.validateKishinSouls(kishinSouls).isEmpty()
                    || !inputValidator.validateWitchSouls(witchSouls).isEmpty()) {
                return null;
            }

            if (!inputValidator.validateTrainingLevel(trainingLevel).isEmpty()) {
                return null;
            }

            // Active status must be exactly true or false.
            if (!activeText.equals("true")
                    && !activeText.equals("false")) {
                return null;
            }

            if (!inputValidator.validateDate(dateText).isEmpty()) {
                return null;
            }

            LocalDate lastMissionDate = LocalDate.parse(dateText);

            return new SoulMissionRecord(
                    recordId,
                    teamName,
                    partnerType,
                    kishinSouls,
                    witchSouls,
                    trainingLevel,
                    Boolean.parseBoolean(activeText),
                    lastMissionDate
            );

        } catch (NumberFormatException | DateTimeParseException e) {
            return null;
        }
    }
}
