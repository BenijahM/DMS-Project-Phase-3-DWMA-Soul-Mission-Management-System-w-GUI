/**
 * Be'Nijah Mitchell
 * CEN 3024-13038 - Software Development 1
 * October 17, 2026
 * Class: SoulMissionRecord
 * Objective: This class represents an individual record in
 * the Document Management System. It stores the record ID, team name, partner type,
 * number of kishin & witch souls collected, training level, active status, and last
 * mission date. The class provides methods to access and update record information
 * and to display the record's information in a readable format.
 */
import java.time.LocalDate;
public class SoulMissionRecord {
    private int recordId;
    private String teamName;
    private String partnerType;
    private int kishinSoulsCollected;
    private int witchSoulsCollected;
    private double trainingLevel;
    private boolean activeStatus;
    private LocalDate lastMissionDate;

    // Constructor
    public SoulMissionRecord(int recordId,String teamName, String partnerType, int kishinSoulsCollected,
                             int witchSoulsCollected, double trainingLevel, boolean activeStatus, LocalDate lastMissionDate){
        this.recordId = recordId;
        this.teamName = teamName;
        this.partnerType = partnerType;
        this.kishinSoulsCollected = kishinSoulsCollected;
        this.witchSoulsCollected = witchSoulsCollected;
        this.trainingLevel = trainingLevel;
        this.activeStatus = activeStatus;
        this.lastMissionDate = lastMissionDate;
    }

    // Getters and Setters
    public int getRecordId() {
        return recordId;
    }
    public String getTeamName() {
        return teamName;
    }
    public String getPartnerType() {
        return partnerType;
    }
    public int getKishinSoulsCollected() {
        return kishinSoulsCollected;
    }
    public int getWitchSoulsCollected() {
        return witchSoulsCollected;
    }
    public double getTrainingLevel() {
        return trainingLevel;
    }
    public boolean isActiveStatus(){
        return activeStatus;
    }
    public LocalDate getLastMissionDate() {
        return lastMissionDate;
    }

    public void setRecordId(int recordId) { this.recordId = recordId;}
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }
    public void setKishinSoulsCollected(int kishinSoulsCollected) {
        this.kishinSoulsCollected = kishinSoulsCollected;
    }
    public void setWitchSoulsCollected(int witchSoulsCollected) {
        this.witchSoulsCollected = witchSoulsCollected;
    }
    public void setTrainingLevel(double trainingLevel) {
        this.trainingLevel = trainingLevel;
    }
    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
    public void setLastMissionDate(LocalDate lastMissionDate) {
        this.lastMissionDate = lastMissionDate;
    }

    /**
     * Method: toString
     * Purpose: Returns the record's information as a formatted String so
     * that the record's details can be easily displayed by the DMS.
     * Arguments: None.
     * Return: A String containing the record ID, team name, partner type,
     * number of kishin & witch souls collected, training level, active status,
     * and last mission date.
     */
    @Override
    public String toString() {
        return "Record ID: " + recordId +
                ", Team Name: " + teamName +
                ", Partner Type: " + partnerType +
                ", Kishin Souls Collected: " + kishinSoulsCollected +
                ", Witch Souls Collected: " + witchSoulsCollected +
                ", Training Level: " + trainingLevel +
                ", Active Status: " + activeStatus +
                ", Last Mission Date: " + lastMissionDate;
    }
}
