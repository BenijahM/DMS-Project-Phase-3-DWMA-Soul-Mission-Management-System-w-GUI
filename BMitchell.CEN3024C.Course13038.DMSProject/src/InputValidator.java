/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 17, 2026
 * Class: InputValidator
 * Objective: This class validates information entered for the DWMA Soul Mission Management System.
 * It ensures that record data follows the required rules before being stored.
 */
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public class InputValidator {
    /**
     * Method: validateRecordId
     * Purpose: Validates that a record ID contains exactly six digits,
     * with no letters, special characters, or spaces.
     * Arguments: recordId (the ID entered by the user).
     * Return: An empty String if the ID is valid, or an error message if the ID is invalid.
     */
    public String validateRecordId(String recordId) {
        if (recordId == null) {
            return "Record ID cannot be empty.";
        }

        if (!recordId.matches("[0-9]{6}")) {
            return "Record ID must contain exactly six digits with no spaces or special characters.";
        }

        if (recordId.equals("000000")) {
            return "Record ID must be a positive six-digit number.";
        }

        return "";
    }

    /**
     * Method: validateTeamName
     * Purpose: Validates that the team name is not null or blank.
     * Arguments: teamName (the name of the meister-weapon team).
     * Return: An empty String if the team name is valid, or an error
     * message if it is invalid.
     */
    public String validateTeamName(String teamName) {
        if (teamName == null || teamName.isBlank()) {
            return "Team name cannot be blank.";
        }

        return "";
    }

    /**
     * Method: validatePartnerType
     * Purpose: Validates that the partner type matches one of the
     * approved values: Weapon, Meister, or Team. (not case sensitive)
     * Arguments: partnerType (the type of partner associated with the team).
     * Return: An empty String if the partner type is valid, or an error
     * message if it is invalid.
     */
    public String validatePartnerType(String partnerType) {
        if (partnerType == null || partnerType.isBlank()) {
            return "Partner type cannot be blank.";
        }

        if (!partnerType.equalsIgnoreCase("Weapon")
                && !partnerType.equalsIgnoreCase("Meister")
                && !partnerType.equalsIgnoreCase("Team")) {
            return "Partner type must be Weapon, Meister, or Team.";
        }

        return "";
    }

    /**
     * Method: validateSoulCount
     * Purpose: Validates that the number of souls collected is a non-negative whole number.
     * Arguments: soulCount (the number of souls collected).
     * Return: An empty String if the soul count is valid, or an error message if it is invalid.
     */
    public String validateSoulCount(int soulCount) {
        if (soulCount < 0) {
            return "Soul count cannot be negative.";
        }

        return "";
    }

    /**
     * Method: validateKishinSouls
     * Purpose: Validates that the number of kishin souls collected
     * is between 0 and 99.
     * Arguments: kishinSouls (the number of kishin souls collected).
     * Return: An empty String if the value is valid, or an error
     * message if the value is invalid.
     */
    public String validateKishinSouls(int kishinSouls) {
        if (kishinSouls < 0 || kishinSouls > 99) {
            return "Kishin souls must be between 0 and 99.";
        }

        return "";
    }

    /**
     * Method: validateWitchSouls
     * Purpose: Validates that the number of witch souls collected
     * is either 0 or 1.
     * Arguments: witchSouls (the number of witch souls collected).
     * Return: An empty String if the value is valid, or an error
     * message if the value is invalid.
     */
    public String validateWitchSouls(int witchSouls) {
        if (witchSouls < 0 || witchSouls > 1) {
            return "Witch souls must be either 0 or 1.";
        }

        return "";
    }

    /**
     * Method: validateTrainingLevel
     * Purpose: Validates that the training level is a finite number between 1.0 and 100.0, inclusive.
     * Arguments: trainingLevel (the team's current training level).
     * Return: An empty String if the training level is valid, or an error message if it is invalid.
     */
    public String validateTrainingLevel(double trainingLevel) {
        if (!Double.isFinite(trainingLevel)
                || trainingLevel < 1.0
                || trainingLevel > 100.0) {
            return "Training level must be between 1.0 and 100.0.";
        }

        return "";
    }

    /**
     * Method: validateDate
     * Purpose: Validates that the date is a real calendar date in yyyy-MM-dd format.
     * Arguments: date (the date entered by the user).
     * Return: An empty String if the date is valid, or an error message if it is invalid.
     */
    public String validateDate(String date) {
        if (date == null || date.isBlank()) {
            return "Mission date cannot be blank.";
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date);

            if (!parsedDate.toString().equals(date)) {
                return "Date must use yyyy-MM-dd format.";
            }
        } catch (DateTimeParseException e) {
            return "Invalid date. Enter a valid date in yyyy-MM-dd format.";
        }

        return "";
    }

    /**
     * Method: validateRecord
     * Purpose: Validates all fields of a SoulMissionRecord.
     * Arguments: record (the soul mission record to validate).
     * Return: An empty String if all fields are valid, or the first error message encountered.
     * Note: Had help from AI for this validation method.
     */
    public String validateRecord(SoulMissionRecord record) {
        if (record == null) {
            return "Record cannot be null.";
        }

        String error;

        error = validateRecordId(
                String.valueOf(record.getRecordId()));
        if (!error.isEmpty()) {
            return error;
        }

        error = validateTeamName(record.getTeamName());
        if (!error.isEmpty()) {
            return error;
        }

        error = validateKishinSouls(record.getKishinSoulsCollected());
        if (!error.isEmpty()) {
            return error;
        }

        error = validateWitchSouls(record.getWitchSoulsCollected());
        if (!error.isEmpty()) {
            return error;
        }

        error = validateSoulCount(record.getWitchSoulsCollected());
        if (!error.isEmpty()) {
            return "Witch souls: " + error;
        }

        error = validateTrainingLevel(record.getTrainingLevel());
        if (!error.isEmpty()) {
            return error;
        }

        if (record.getLastMissionDate() == null) {
            return "Mission date cannot be null.";
        }

        error = validateDate(record.getLastMissionDate().toString());
        if (!error.isEmpty()) {
            return error;
        }

        return "";
    }
}
