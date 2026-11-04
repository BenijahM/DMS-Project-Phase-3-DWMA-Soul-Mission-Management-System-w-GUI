/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 17, 2026
 * Class: ConsoleUI
 * Objective: Provides the command-line interface for the DWMA Soul Mission Management System.
 * Allows users to add, import, display, search, update, delete, and calculate qualification progress for records.
 * User input is validated to prevent invalid input from crashing the application.
 */

import java.time.LocalDate; import java.time.format.DateTimeParseException; import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;
    private final RecordManager recordManager;
    private final InputValidator inputValidator;
    private final ProgressCalculator progressCalculator;
    private final FileImporter fileImporter;

    private boolean running;

    /**
     * Method: ConsoleUI
     * Purpose: Initializes the scanner and creates the objects needed to manage
     * records, validate input, calculate progress, and import records from files.
     * Arguments: None.
     * Return: None. This is a constructor.
     */
    public ConsoleUI() {
        scanner = new Scanner(System.in);
        recordManager = new RecordManager();
        inputValidator = new InputValidator();
        progressCalculator = new ProgressCalculator();
        fileImporter = new FileImporter(inputValidator);
        running = true;
    }

    /**
     * Method: start
     * Purpose: Starts the application, displays the main menu, and processes
     * the user's menu selections until the application is stopped.
     * Arguments: None.
     * Return: None.
     */
    public void start() {
        System.out.println("==============================================");
        System.out.println("  DWMA SOUL MISSION MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        while (running) {
            displayMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addRecord();
                    break;
                case 2:
                    importRecords();
                    break;
                case 3:
                    displayAllRecords();
                    break;
                case 4:
                    searchById();
                    break;
                case 5:
                    searchByTeamName();
                    break;
                case 6:
                    updateRecord();
                    break;
                case 7:
                    deleteRecord();
                    break;
                case 8:
                    calculateProgress();
                    break;
                case 9:
                    exitApplication();
                    break;
                default:
                    System.out.println(
                            "Invalid menu choice. Please select 1-9.");
            }
        }
    }

    /**
     * Method: displayMenu
     * Purpose: Displays the main menu and the available operations the user
     * can perform in the application.
     * Arguments: None.
     * Return: None.
     */
    private void displayMenu() {
        System.out.println();
        System.out.println("**************** MAIN MENU ****************");
        System.out.println("1. Add a Record Manually");
        System.out.println("2. Import Records from a Text File");
        System.out.println("3. Display All Records");
        System.out.println("4. Search by Record ID");
        System.out.println("5. Search by Team Name");
        System.out.println("6. Update a Record");
        System.out.println("7. Delete a Record");
        System.out.println("8. Calculate Qualification Progress");
        System.out.println("9. Exit");
        System.out.println("********************************************");
    }

    /**
     * Method: readInt
     * Purpose: Reads an integer from the user and repeats the prompt until
     * the user enters a valid whole number.
     * Arguments: prompt - the message displayed before requesting input.
     * Return: An integer entered by the user.
     */
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Method: readRecordId
     * Purpose: Reads and validates a record ID, repeating the prompt until
     * the user enters an ID that passes validation and can be converted to an integer.
     * Arguments: prompt - the message displayed before requesting the record ID.
     * Return: A valid record ID as an integer.
     */
    private int readRecordId(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            String error = inputValidator.validateRecordId(input);

            if (!error.isEmpty()) {
                System.out.println("Error: " + error);
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid record ID. Please try again.");
            }
        }
    }

    /**
     * Method: readTeamName
     * Purpose: Reads and validates a team name, rejecting invalid names and
     * names containing digits.
     * Arguments: None.
     * Return: A valid team name as a String.
     */
    private String readTeamName() {
        while (true) {
            System.out.print("Enter team name: ");
            String name = scanner.nextLine().trim();

            String error = inputValidator.validateTeamName(name);

            if (!error.isEmpty()) {
                System.out.println("Error: " + error);
                continue;
            }

            // Reject names containing digits.
            if (name.matches(".*\\d.*")) {
                System.out.println(
                        "Error: Team name cannot contain numbers.");
                continue;
            }
            return name;
        }
    }

    /**
     * Method: readPartnerType
     * Purpose: Prompts the user to enter a partner type and validates the
     * input against the supported types: Weapon, Meister, and Team.
     * Arguments: None.
     * Return: The validated partner type as a String, formatted as
     * "Weapon", "Meister", or "Team".
     */
    private String readPartnerType() {
        while (true) {
            System.out.println("\nSelect partner type:");
            System.out.println("1. Team");
            System.out.println("2. Meister");
            System.out.println("3. Weapon");

            int choice = readInt("Enter your choice (1-3): ");

            switch (choice) {
                case 1:
                    return "Team";
                case 2:
                    return "Meister";
                case 3:
                    return "Weapon";
                default:
                    System.out.println(
                            "Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    /**
     * Method: readSoulCount
     * Purpose: Reads a soul count and validates it, repeating the prompt
     * until the user enters an acceptable count.
     * Arguments: prompt - the message displayed before requesting the soul count.
     * Return: A validated soul count as an integer.
     */
    private int readSoulCount(String prompt) {
        while (true) {
            int count = readInt(prompt);

            String error = inputValidator.validateSoulCount(count);

            if (error.isEmpty()) {
                return count;
            }

            System.out.println("Error: " + error);
        }
    }


    /**
     * Method: readKishinSouls
     * Purpose: Reads and validates the number of kishin souls,
     * accepting values between 0 and 99.
     * Arguments: None.
     * Return: A validated kishin soul count as an integer.
     */
    private int readKishinSouls() {
        while (true) {
            int count = readInt("Enter kishin souls collected: ");

            String error = inputValidator.validateKishinSouls(count);

            if (error.isEmpty()) {
                return count;
            }

            System.out.println("Error: " + error);
        }
    }

    /**
     * Method: readWitchSouls
     * Purpose: Reads and validates the number of witch souls,
     * accepting only 0 or 1.
     * Arguments: None.
     * Return: A validated witch soul count as an integer.
     */
    private int readWitchSouls() {
        while (true) {
            int count = readInt("Enter witch souls collected: ");

            String error = inputValidator.validateWitchSouls(count);

            if (error.isEmpty()) {
                return count;
            }

            System.out.println("Error: " + error);
        }
    }


    /**
     * Method: readTrainingLevel
     * Purpose: Reads and validates the user's training level, accepting
     * only numeric values that satisfy the validation rules.
     * Arguments: None.
     * Return: A valid training level as a double.
     */
    private double readTrainingLevel() {
        while (true) {
            System.out.print("Enter training level (1.0-100.0): ");
            String input = scanner.nextLine().trim();

            try {
                double level = Double.parseDouble(input);

                String error =
                        inputValidator.validateTrainingLevel(level);

                if (error.isEmpty()) {
                    return level;
                }

                System.out.println("Error: " + error);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid training level. Enter a number.");
            }
        }
    }

    /**
     * Method: readActiveStatus
     * Purpose: Prompts the user to enter the team's active status and
     * accepts only true or false, ignoring capitalization.
     * Arguments: None.
     * Return: A boolean indicating whether the team is active.
     */
    private boolean readActiveStatus() {
        while (true) {
            System.out.print("Is the team active? (T/F): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("t")) {
                return true;
            }

            if (input.equalsIgnoreCase("f")) {
                return false;
            }

            System.out.println(
                    "Invalid status. Enter T or F.");
        }
    }

    /**
     * Method: readMissionDate
     * Purpose: Reads and validates the team's last mission date, requiring
     * the date to follow the yyyy-MM-dd format and be parseable as a LocalDate.
     * Arguments: None.
     * Return: The validated mission date as a LocalDate object.
     */
    private LocalDate readMissionDate() {
        while (true) {
            System.out.print(
                    "Enter last mission date (yyyy-MM-dd): ");
            String input = scanner.nextLine().trim();

            String error = inputValidator.validateDate(input);

            if (error.isEmpty()) {
                try {
                    return LocalDate.parse(input);
                } catch (DateTimeParseException e) {
                    System.out.println(
                            "Invalid date. Please try again.");
                }
            } else {
                System.out.println("Error: " + error);
            }
        }
    }

    /**
     * Method: readNewRecord
     * Purpose: Collects all required record information from the user,
     * checks that the record ID is unique, and creates a new soul mission record.
     * Arguments: None.
     * Return: A new SoulMissionRecord object containing the user's input.
     */
    private SoulMissionRecord readNewRecord() {
        int id = readRecordId("Enter six-digit record ID: ");

        while (recordManager.containsId(id)) {
            System.out.println(
                    "That record ID already exists. Choose another.");
            id = readRecordId("Enter six-digit record ID: ");
        }

        String name = readTeamName();
        String partnerType = readPartnerType();

        int kishinSouls = readKishinSouls();
        int witchSouls = readWitchSouls();

        double trainingLevel = readTrainingLevel();
        boolean active = readActiveStatus();
        LocalDate missionDate = readMissionDate();

        return new SoulMissionRecord(
                id,
                name,
                partnerType,
                kishinSouls,
                witchSouls,
                trainingLevel,
                active,
                missionDate
        );
    }

    /**
     * Method: addRecord
     * Purpose: Collects a new record from the user, validates it, and adds
     * it to the record manager if the record passes validation.
     * Arguments: None.
     * Return: None.
     */
    private void addRecord() {
        System.out.println("\n--- ADD NEW RECORD ---");

        SoulMissionRecord record = readNewRecord();

        String error = inputValidator.validateRecord(record);

        if (!error.isEmpty()) {
            System.out.println(
                    "Record validation failed: " + error);
            return;
        }

        if (recordManager.addRecord(record)) {
            System.out.println("Record added successfully!");
            System.out.println(record);
        } else {
            System.out.println(
                    "Record could not be added. Check the ID.");
        }
    }

    /**
     * Method: importRecords
     * Purpose: Prompts the user for a text file path and uses the file
     * importer to import records into the record manager.
     * Arguments: None.
     * Return: None.
     */
    private void importRecords() {
        System.out.println("\n--- IMPORT RECORDS ---");
        System.out.print("Enter the text file path: ");

        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("Error: File path cannot be blank.");
            return;
        }

        fileImporter.importRecords(filePath, recordManager);
    }

    /**
     * Method: displayAllRecords
     * Purpose: Displays the total number of records and requests that the
     * record manager display all stored soul mission records.
     * Arguments: None.
     * Return: None.
     */
    private void displayAllRecords() {
        System.out.println("\n--- ALL SOUL MISSION RECORDS ---");
        System.out.println(
                "Total records: " + recordManager.getRecordCount());

        recordManager.getAllRecords();
    }

    /**
     * Method: searchById
     * Purpose: Prompts the user for a record ID and searches for the
     * corresponding record, displaying it if found.
     * Arguments: None.
     * Return: None.
     */
    private void searchById() {
        System.out.println("\n--- SEARCH BY RECORD ID ---");

        int id = readRecordId("Enter record ID: ");
        SoulMissionRecord record = recordManager.findById(id);

        if (record != null) {
            System.out.println("Record found:");
            System.out.println(record);
        } else {
            System.out.println(
                    "No record found with ID " + id + ".");
        }
    }

    /**
     * Method: searchByTeamName
     * Purpose: Prompts the user for a team name and searches for a matching
     * record, displaying the record if a match is found.
     * Arguments: None.
     * Return: None.
     */
    private void searchByTeamName() {
        System.out.println("\n--- SEARCH BY TEAM NAME ---");

        String name = readTeamName();
        SoulMissionRecord record =
                recordManager.findByTeamName(name);

        if (record != null) {
            System.out.println("Record found:");
            System.out.println(record);
        } else {
            System.out.println(
                    "No record found for team \"" + name + "\".");
        }
    }

    /**
     * Method: updateRecord
     * Purpose: Finds an existing record by ID and allows the user to modify
     * its fields individually. Validates the record during editing and
     * updates the record manager with the changes.
     * Arguments: None.
     * Return: None.
     */
    private void updateRecord() {
        System.out.println("\n--- UPDATE RECORD ---");

        int id = readRecordId(
                "Enter the ID of the record to update: ");

        SoulMissionRecord existing = recordManager.findById(id);

        if (existing == null) {
            System.out.println("Record not found.");
            return;
        }

        System.out.println("Current record:");
        System.out.println(existing);

        boolean editing = true;

        while (editing) {
            System.out.println("\nSelect the field to update:");
            System.out.println("1. Team Name");
            System.out.println("2. Partner Type");
            System.out.println("3. Kishin Souls Collected");
            System.out.println("4. Witch Souls Collected");
            System.out.println("5. Training Level");
            System.out.println("6. Active Status");
            System.out.println("7. Last Mission Date");
            System.out.println("8. Finish Updating");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    existing.setTeamName(readTeamName());
                    break;
                case 2:
                    existing.setPartnerType(readPartnerType());
                    break;
                case 3:
                    existing.setKishinSoulsCollected(readKishinSouls());
                    break;

                case 4:
                    existing.setWitchSoulsCollected(readWitchSouls());
                    break;
                case 5:
                    existing.setTrainingLevel(readTrainingLevel());
                    break;
                case 6:
                    existing.setActiveStatus(readActiveStatus());
                    break;
                case 7:
                    existing.setLastMissionDate(readMissionDate());
                    break;
                case 8:
                    editing = false;
                    break;
                default:
                    System.out.println(
                            "Invalid choice. Select 1-8.");
            }

            if (editing) {
                String error =
                        inputValidator.validateRecord(existing);

                if (!error.isEmpty()) {
                    System.out.println(
                            "Validation error: " + error);
                } else {
                    recordManager.updateRecord(existing);
                    System.out.println("Current record:");
                    System.out.println(existing);
                }
            }
        }

        String error = inputValidator.validateRecord(existing);

        if (!error.isEmpty()) {
            System.out.println(
                    "Record has invalid data: " + error);
        } else {
            recordManager.updateRecord(existing);
            System.out.println("Record updated successfully!");
            System.out.println(existing);
        }
    }

    /**
     * Method: deleteRecord
     * Purpose: Finds a record by ID, displays it for confirmation, and
     * deletes it from the record manager if the user confirms the deletion.
     * Arguments: None.
     * Return: None.
     */
    private void deleteRecord() {
        System.out.println("\n--- DELETE RECORD ---");

        int id = readRecordId("Enter the ID to delete: ");
        SoulMissionRecord record = recordManager.findById(id);

        if (record == null) {
            System.out.println("Record not found.");
            return;
        }

        System.out.println("Record selected for deletion:");
        System.out.println(record);

        System.out.print(
                "Are you sure you want to delete this record? (y/n): ");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("y")) {
            if (recordManager.deleteRecord(id)) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println(
                        "The record could not be deleted.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    /**
     * Method: calculateProgress
     * Purpose: Finds a record by ID and generates a Death Scythe
     * qualification progress report for that record.
     * Arguments: None.
     * Return: None.
     */
    private void calculateProgress() {
        System.out.println(
                "\n--- DEATH SCYTHE QUALIFICATION PROGRESS ---");

        int id = readRecordId(
                "Enter the record ID to calculate progress: ");

        SoulMissionRecord record = recordManager.findById(id);

        if (record == null) {
            System.out.println("Record not found.");
            return;
        }

        System.out.println(
                progressCalculator.createProgressReport(record));
    }

    /**
     * Method: exitApplication
     * Purpose: Asks the user to confirm whether they want to exit the
     * application. Stops the main menu loop if the user confirms.
     * Arguments: None.
     * Return: None.
     */
    private void exitApplication() {
        System.out.print("Are you sure you want to exit? (y/n): ");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("y")) {
            running = false;
            System.out.println(
                    "Exiting DWMA Soul Mission Management System. Goodbye!");
        } else if (confirmation.equalsIgnoreCase("n")) {
            System.out.println("Returning to the main menu.");
        } else {
            System.out.println("Invalid input. Enter y or n.");
        }
    }

    /**
     * Method: main
     * Purpose: Serves as the program's entry point by creating a ConsoleUI
     * object and starting the application's main menu.
     * Arguments: args - a String array containing command-line arguments.
     * Return: None. This method is void.
     */
    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.start();
    }


}