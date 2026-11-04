/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 23, 2026
 * Class: RecordManagerTest
 * Objective: To test of all CRUD operations in the RecordManager Class. Also checks how the recordManager handles
 * duplicate ID's and nonexistent records.
 **/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
class RecordManagerTest {
    // Create objects to be tested.
    RecordManager manager;
    SoulMissionRecord record;

    /**
     * Method: setUp
     * Purpose: Creates a new RecordManager and a sample SoulMissionRecord before each test, then adds the sample
     * record to the manager.
     * Arguments: None.
     * Return: void.
     */
    @BeforeEach
    void setUp() {
        // Create a new manager before each test.
        manager = new RecordManager();

        // Supply test data to a record object.
        record = new SoulMissionRecord(123456, "Be'Nijah", "Weapon",88,
                0,88.0,true,LocalDate.of(2026,10,23));

        // Adding the test record to the manager, for all test.
        manager.addRecord(record);
    }

    /**
     * Method: addRecordTest
     * Purpose: Tests whether a new record can be added successfully and verifies the record count and the ability to
     * find the record by ID.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Add Record Test")
    void addRecordTest() {
        // Creating a new record to add to the manager.
        SoulMissionRecord newRecord = new SoulMissionRecord(654321, "Sugar Plum", "Meister",
                50, 0, 75.0, true,
                LocalDate.of(2026, 10, 20));

        // Add the new record.
        boolean result = manager.addRecord(newRecord);

        //Verify that the record was added successfully.
        assertTrue(result, "Error: The record was not added.");

        //Compares the two values and displays a message if they aren't equal.
        assertEquals(2,manager.getRecordCount(),"Error: The record count should be 2.");

        // Verify that the record can be found by its ID.
        assertNotNull(manager.findById(654321), "Error: The record could not be found.");

        // Test success message.
        System.out.println("Success: The record was added!");
    }
    /**
     * Method: addDuplicateRecordNegativeTest
     * Purpose: Tests how the manager handles an attempt to add a record that already exists.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Negative Test: Add Duplicate Record")
    void addDuplicateRecordNegativeTest() {
        // Adding the same record again.
        boolean result = manager.addRecord(record);

        // Incorrect expectation: adding a duplicate should succeed.
        assertTrue(result,
                "Error: The duplicate record was not added.");

        System.out.println("Success: Duplicate record added!");
    }

    /**
     * Method: deleteRecordTest
     * Purpose: Tests whether an existing record can be deleted and verifies that it can no longer be found and that
     * the record count becomes zero.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Remove Record Test")
    void deleteRecordTest() {
        // Remove the record by ID.
        boolean result = manager.deleteRecord(123456);

        // Verify that the record was removed successfully.
       assertTrue(result, "Error: The record was not removed.");

        // Verify that the record can no longer be found.
        assertNull(manager.findById(123456), "Error: The record still exists.");

        // Verify that the manager is now empty.
        assertEquals(0, manager.getRecordCount(), "Error: The record count should be 0.");

        // Test success message.
        System.out.println("Success: The record was removed!");
    }
    /**
     * Method: deleteNonexistentRecordNegativeTest
     * Purpose: Tests how the manager handles an attempt to delete a record using an ID that does not exist.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Negative Test: Delete Nonexistent Record")
    void deleteNonexistentRecordNegativeTest() {
        //Deleting the non-existent record.
        boolean result = manager.deleteRecord(654321);

        // Incorrect expectation: deletion should succeed.
        assertTrue(result,
                "Error: The nonexistent record was not deleted.");

        System.out.println("Success: Nonexistent record deleted!");
    }

    /**
     * Method: updateRecord
     * Purpose: Tests whether an existing record's team name, witch soul count, and training level can be updated
     * and verifies the saved changes.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Update Record Test")
    void updateRecord() {
        // Updating the existing record's information.
        record.setTeamName("Buttercup");
        record.setWitchSoulsCollected(1);
        record.setTrainingLevel(90.0);

        // Update the record manager.
        boolean result = manager.updateRecord(record);

        //Retrieving the updated record.
        SoulMissionRecord updatedRecord = manager.findById(123456);

        // Verify that the changes were saved.
        assertEquals("Buttercup", updatedRecord.getTeamName(), "Error: The team name was not updated.");
        assertEquals(90.0, updatedRecord.getTrainingLevel(), "Error: The training level was not updated.");
        System.out.println("Success: The record was updated!");
    }
    /**
     * Method: updateNonexistentRecordNegativeTest
     * Purpose: Tests how the manager handles an attempt to update a record whose ID does not match an existing record.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Negative Test: Update Nonexistent Record")
    void updateNonexistentRecordNegativeTest() {

        SoulMissionRecord newRecord = new SoulMissionRecord(654321, "New Team", "Meister",
                10, 0, 50.0, true,
                LocalDate.of(2026, 10, 20));

        boolean result = manager.updateRecord(newRecord);

        // Incorrect expectation: update should succeed.
        assertTrue(result,
                "Error: The nonexistent record was not updated.");

        System.out.println("Success: Nonexistent record updated!");
    }

    /**
     * Method: getAllRecordsTest
     * Purpose: Tests whether the manager contains the expected record and displays all records in the console.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Display Records Test")
    void getAllRecordsTest() {
        // Verify that the record exists.
        assertNotNull(manager.findById(123456),
                "Error: The record could not be found.");

        // Verify that the record count is correct.
        assertEquals(1, manager.getRecordCount(),
                "Error: The record count should be 1.");

        // Display all records in the console.
        manager.getAllRecords();

        // Test success message.
        System.out.println("Success: The records were displayed!");
    }
}