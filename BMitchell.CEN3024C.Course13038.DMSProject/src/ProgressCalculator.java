/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 17, 2026
 * Class: ProgressCalculator
 * Objective: This class calculates a meister-and-weapon team's
 * progress toward Death Scythe qualification based on the number
 * of kishin-egg souls and witch souls collected.
*/
public class ProgressCalculator {

    /**
     * Method: calculateProgress
     * Purpose: Calculates the team's percentage of progress toward
     * Death Scythe qualification, capped at 100 percent.
     * Arguments: record (the soul mission record to calculate).
     * Return: The team's qualification progress as a percentage,
     * or -1.0 if the supplied record is null.
     */
    public double calculatePercentage (SoulMissionRecord record){
        if (record == null) {
            return -1.0;
        }

        int kishinSouls = Math.min(
                record.getKishinSoulsCollected(), 99);

        int witchSouls = Math.min(
                record.getWitchSoulsCollected(), 1);

        return (kishinSouls + witchSouls);
    }

    /**
     * Method: calculateSoulsNeeded
     * Purpose: Calculates how many additional souls are needed
     * for Death Scythe qualification (both kishin and witch souls).
     * Arguments: record (the soul mission record to calculate).
     * Return: The number of souls still needed, or -1 if the
     * supplied record is null.
     */
    public int calculateSoulsNeeded(SoulMissionRecord record) {
        if (record == null) {
            return -1;
        }

        int kishinSouls = Math.min(
                record.getKishinSoulsCollected(), 99);

        int witchSouls = Math.min(
                record.getWitchSoulsCollected(), 1);

        return 100 - (kishinSouls + witchSouls);
    }

    /**
     * Method: isQualified
     * Purpose: Determines whether the team has met the
     * Death Scythe qualification soul collection goal.
     * Arguments: record (the soul mission record to check).
     * Return: true if the team has collected at least 99 kishin-egg
     * souls and 1 witch soul; otherwise, false.
     */
    public boolean isQualified(SoulMissionRecord record) {
        if (record == null) {
            return false;
        }

        return record.getKishinSoulsCollected() >= 99
                && record.getWitchSoulsCollected() >= 1;
    }

    /**
     * Method: createProgressReport
     * Purpose: Creates a formatted report showing the team's Death Scythe qualification progress,
     * souls collected,souls still needed, and qualification status.
     * Arguments: record (the soul mission record to report on).
     * Return: A String containing the team's progress report,
     * or an error message if the record is null.
     */
    public String createProgressReport(SoulMissionRecord record) {
        if (record == null) {
            return "Error: Cannot create a progress report for a null record.";
        }

        double progress = calculatePercentage(record);
        int soulsNeeded = calculateSoulsNeeded(record);
        boolean qualified = isQualified(record);

        return "===== Death Scythe Qualification Progress =====\n"
                + "Record ID: " + record.getRecordId() + "\n"
                + "Team Name: " + record.getTeamName() + "\n"
                + "Kishin Souls Collected: "
                + record.getKishinSoulsCollected() + " / 99\n"
                + "Witch Souls Collected: "
                + record.getWitchSoulsCollected() + " / 1\n"
                + "Qualification Progress: " + progress + "%\n"
                + "Souls Still Needed: " + soulsNeeded + "\n"
                + "Qualification Status: "
                + (qualified ? "Qualified!" : "Not Yet Qualified")
                + "\n==============================================";
    }

}


