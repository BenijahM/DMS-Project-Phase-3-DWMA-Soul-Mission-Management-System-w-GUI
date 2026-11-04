/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 23, 2026
 * Class: ProgressCalculatorTest
 * Objective: To test the ProgressCalculator class and verify that the Death Scythe qualification
 * progress percentage is calculated correctly based on a team's collected souls.
**/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
class ProgressCalculatorTest {
    ProgressCalculator calculator;
    SoulMissionRecord record;

    /**
     * Method: setUp
     * Purpose: Creates a ProgressCalculator and a sample SoulMissionRecord to provide test data
     * for calculating qualification progress.
     * Arguments: None.
     * Return: void.
     */
    @BeforeEach
    void setUp() {
        calculator = new ProgressCalculator();

        // Adding a new record.
        record = new SoulMissionRecord(123456, "Be'Nijah", "Weapon", 88,
                0, 88.0, true, LocalDate.of(2026, 10, 23));
    }

    /**
     * Method: calculateProgressTest
     * Purpose: Tests whether the calculator correctly calculates the team's qualification progress
     * and returns the expected percentage.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Calculate Qualification Progress Test")
    void calculateProgressTest() {
        // Calculate the team's progress.
        double result = calculator.calculatePercentage(record);

        // Verify the expected progress.
        assertEquals(88.0, result,
                "Error: Qualification progress should be 88%.");

        System.out.println("Success: Qualification progress calculated!");
    }
    /**
     * Method: incorrectProgressTest
     * Purpose: Tests the calculator using an intentionally incorrect expected percentage to check
     * whether the assertion identifies an unexpected qualification progress result.
     * Arguments: None.
     * Return: void.
     */
    @Test
    @DisplayName("Negative Test: Qualification Progress")
    void incorrectProgressTest() {
        double result = calculator.calculatePercentage(record);

        // Intentionally incorrect expectation.
        assertEquals(100.0, result, "Error: Progress should be 100%.");

        System.out.println("Success: Progress calculated!");
    }
}