/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 23, 2026
 * Class: FileImporterTest
 * Objective: To test the FileImporter class and verify that records can be imported from a text file
 * into the RecordManager. The tests also checks how the importer handles attempts to import a file that
 * does not exist.
 **/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
class FileImporterTest {

    RecordManager manager;
    InputValidator validator;
    FileImporter importer;

    /**
     * Method: setUp
     * Purpose: Creates a RecordManager, InputValidator, and FileImporter to prepare the objects needed
     * for each test.
     * Arguments: None.
     * Return: void.
     */
    @BeforeEach
    void setUp() {
        // Create the objects needed for testing.
        manager = new RecordManager();
        validator = new InputValidator();
        importer = new FileImporter(validator);
    }

    /**
     * Method: importRecords
     * Purpose: Tests whether records can be successfully imported from a text file and verifies that the expected
     * record is stored in the RecordManager.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Import Records Test")
    void importRecordsTest() {
        // Import records from the text file.
        importer.importRecords("testRecords.txt", manager);

        // Verify that the record was imported.
        assertEquals(1, manager.getRecordCount(), "Error: The record was not imported.");

        // Verify that the imported record can be found.
        assertNotNull(manager.findById(123456), "Error: The imported record could not be found.");

        // Test success message.
        System.out.println("Success: The record was imported!");
    }
    /**
     * Method: fileImportNegativeTest
     * Purpose: Tests how the importer handles an attempt to import a text file that does not exist
     * and checks the record count after the import attempt.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Negative Test: File Import")
    void fileImportNegativeTest() {
        // Attempt to open a file that does not exist.
        importer.importRecords("missingFile.txt", manager);

        // Verify that no records were imported.
        assertEquals(1, manager.getRecordCount(), "Error: No records have been imported," +
                " as the file does not exist.");

        System.out.println("Success: The missing file was handled imported!");
    }
}