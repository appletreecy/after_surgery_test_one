package com.example.welcome.dto;

import java.time.LocalDate;

public class AfterSurgeryJoinDto {
    private LocalDate date;

    // From Table One
    private Integer numOfPostoperativeVisits;
    private Integer numOfPostoperativeAnalgesiaCases;
    private Integer numOfAdverseReactionCases;
    private Integer numOfInadequateAnalgesia;

    // From Table Two
    private Integer numOfNauseaAndVomiting;
    private Integer numOfDizziness;
    private Integer numOfNauseaAndVomitingAndDizziness;
    private Integer numOfItching;
    private Integer numOfAllergicRash;
    private Integer numOfProlongedAnestheticRecovery;
    private Integer numOfPunctureSiteAbnormality;
    private Integer numOfAbdominalDistension;
    private Integer numOfEndotrachealIntubationDiscomfort;
    private Integer numOfEpigastricPain;
    private Integer numOfDelirium;
    private Integer numOfChestDiscomfort;
    private Integer numOfTourniquetReaction;
    private Integer numOfOther;
    private String otherComments;

    // TableThree
    private Integer numOfJointComplicationCount;
    private Integer numOfMotorDysfunctionCount;
    private Integer numOfTraumaComplicationCount;
    private Integer numOfAnkleComplicationCount;
    private Integer numOfPediatricAdverseEventCount;
    private Integer numOfSpinalComplicationCount;
    private Integer numOfHandSurgeryComplicationCount;
    private Integer numOfObstetricAdverseEventCount;
    private Integer numOfGynecologicalAdverseEventCount;
    private Integer numOfSurgicalTreatmentCount;

    // TableFour
    private Integer numOfFormulationOne;
    private Integer numOfFormulationTwo;
    private Integer numOfFormulationThree;
    private Integer numOfFormulationFour;
    private Integer numOfFormulationFive;
    private Integer numOfFormulationSix;

    // TableFive
    private Integer numberOfFollowUpsForCriticallyIllPatients;
    private Integer numberOfCirticalRescueCases;
    private Integer numberOfDeaths;

    // Getters and Setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Integer getNumOfPostoperativeVisits() { return numOfPostoperativeVisits; }
    public void setNumOfPostoperativeVisits(Integer value) { this.numOfPostoperativeVisits = value; }

    public Integer getNumOfPostoperativeAnalgesiaCases() { return numOfPostoperativeAnalgesiaCases; }
    public void setNumOfPostoperativeAnalgesiaCases(Integer value) { this.numOfPostoperativeAnalgesiaCases = value; }

    public Integer getNumOfAdverseReactionCases() { return numOfAdverseReactionCases; }
    public void setNumOfAdverseReactionCases(Integer value) { this.numOfAdverseReactionCases = value; }

    public Integer getNumOfInadequateAnalgesia() { return numOfInadequateAnalgesia; }
    public void setNumOfInadequateAnalgesia(Integer value) { this.numOfInadequateAnalgesia = value; }

    public Integer getNumOfNauseaAndVomiting() { return numOfNauseaAndVomiting; }
    public void setNumOfNauseaAndVomiting(Integer value) { this.numOfNauseaAndVomiting = value; }

    public Integer getNumOfDizziness() { return numOfDizziness; }
    public void setNumOfDizziness(Integer value) { this.numOfDizziness = value; }

    public Integer getNumOfNauseaAndVomitingAndDizziness() { return numOfNauseaAndVomitingAndDizziness; }
    public void setNumOfNauseaAndVomitingAndDizziness(Integer value) { this.numOfNauseaAndVomitingAndDizziness = value; }

    public Integer getNumOfItching() { return numOfItching; }
    public void setNumOfItching(Integer value) { this.numOfItching = value; }

    public Integer getNumOfAllergicRash() { return numOfAllergicRash; }
    public void setNumOfAllergicRash(Integer value) { this.numOfAllergicRash = value; }

    public Integer getNumOfProlongedAnestheticRecovery() { return numOfProlongedAnestheticRecovery; }
    public void setNumOfProlongedAnestheticRecovery(Integer value) { this.numOfProlongedAnestheticRecovery = value; }

    public Integer getNumOfPunctureSiteAbnormality() { return numOfPunctureSiteAbnormality; }
    public void setNumOfPunctureSiteAbnormality(Integer value) { this.numOfPunctureSiteAbnormality = value; }

    public Integer getNumOfAbdominalDistension() { return numOfAbdominalDistension; }
    public void setNumOfAbdominalDistension(Integer value) { this.numOfAbdominalDistension = value; }

    public Integer getNumOfEndotrachealIntubationDiscomfort() { return numOfEndotrachealIntubationDiscomfort; }
    public void setNumOfEndotrachealIntubationDiscomfort(Integer value) { this.numOfEndotrachealIntubationDiscomfort = value; }

    public Integer getNumOfEpigastricPain() { return numOfEpigastricPain; }
    public void setNumOfEpigastricPain(Integer value) { this.numOfEpigastricPain = value; }

    public Integer getNumOfDelirium() { return numOfDelirium; }
    public void setNumOfDelirium(Integer value) { this.numOfDelirium = value; }

    public Integer getNumOfChestDiscomfort() { return numOfChestDiscomfort; }
    public void setNumOfChestDiscomfort(Integer value) { this.numOfChestDiscomfort = value; }

    public Integer getNumOfTourniquetReaction() { return numOfTourniquetReaction; }
    public void setNumOfTourniquetReaction(Integer value) { this.numOfTourniquetReaction = value; }

    public Integer getNumOfOther() { return numOfOther; }
    public void setNumOfOther(Integer value) { this.numOfOther = value; }

    public String getOtherComments() { return otherComments; }
    public void setOtherComments(String value) { this.otherComments = value; }

    public Integer getNumOfJointComplicationCount() {
        return numOfJointComplicationCount;
    }

    public void setNumOfJointComplicationCount(Integer numOfJointComplicationCount) {
        this.numOfJointComplicationCount = numOfJointComplicationCount;
    }

    public Integer getNumOfMotorDysfunctionCount() {
        return numOfMotorDysfunctionCount;
    }

    public void setNumOfMotorDysfunctionCount(Integer numOfMotorDysfunctionCount) {
        this.numOfMotorDysfunctionCount = numOfMotorDysfunctionCount;
    }

    public Integer getNumOfTraumaComplicationCount() {
        return numOfTraumaComplicationCount;
    }

    public void setNumOfTraumaComplicationCount(Integer numOfTraumaComplicationCount) {
        this.numOfTraumaComplicationCount = numOfTraumaComplicationCount;
    }

    public Integer getNumOfAnkleComplicationCount() {
        return numOfAnkleComplicationCount;
    }

    public void setNumOfAnkleComplicationCount(Integer numOfAnkleComplicationCount) {
        this.numOfAnkleComplicationCount = numOfAnkleComplicationCount;
    }

    public Integer getNumOfPediatricAdverseEventCount() {
        return numOfPediatricAdverseEventCount;
    }

    public void setNumOfPediatricAdverseEventCount(Integer numOfPediatricAdverseEventCount) {
        this.numOfPediatricAdverseEventCount = numOfPediatricAdverseEventCount;
    }

    public Integer getNumOfSpinalComplicationCount() {
        return numOfSpinalComplicationCount;
    }

    public void setNumOfSpinalComplicationCount(Integer numOfSpinalComplicationCount) {
        this.numOfSpinalComplicationCount = numOfSpinalComplicationCount;
    }

    public Integer getNumOfHandSurgeryComplicationCount() {
        return numOfHandSurgeryComplicationCount;
    }

    public void setNumOfHandSurgeryComplicationCount(Integer numOfHandSurgeryComplicationCount) {
        this.numOfHandSurgeryComplicationCount = numOfHandSurgeryComplicationCount;
    }

    public Integer getNumOfObstetricAdverseEventCount() {
        return numOfObstetricAdverseEventCount;
    }

    public void setNumOfObstetricAdverseEventCount(Integer numOfObstetricAdverseEventCount) {
        this.numOfObstetricAdverseEventCount = numOfObstetricAdverseEventCount;
    }

    public Integer getNumOfGynecologicalAdverseEventCount() {
        return numOfGynecologicalAdverseEventCount;
    }

    public void setNumOfGynecologicalAdverseEventCount(Integer numOfGynecologicalAdverseEventCount) {
        this.numOfGynecologicalAdverseEventCount = numOfGynecologicalAdverseEventCount;
    }

    public Integer getNumOfSurgicalTreatmentCount() {
        return numOfSurgicalTreatmentCount;
    }

    public void setNumOfSurgicalTreatmentCount(Integer numOfSurgicalTreatmentCount) {
        this.numOfSurgicalTreatmentCount = numOfSurgicalTreatmentCount;
    }

    public Integer getNumOfFormulationOne() {
        return numOfFormulationOne;
    }

    public void setNumOfFormulationOne(Integer numOfFormulationOne) {
        this.numOfFormulationOne = numOfFormulationOne;
    }

    public Integer getNumOfFormulationTwo() {
        return numOfFormulationTwo;
    }

    public void setNumOfFormulationTwo(Integer numOfFormulationTwo) {
        this.numOfFormulationTwo = numOfFormulationTwo;
    }

    public Integer getNumOfFormulationThree() {
        return numOfFormulationThree;
    }

    public void setNumOfFormulationThree(Integer numOfFormulationThree) {
        this.numOfFormulationThree = numOfFormulationThree;
    }

    public Integer getNumOfFormulationFour() {
        return numOfFormulationFour;
    }

    public void setNumOfFormulationFour(Integer numOfFormulationFour) {
        this.numOfFormulationFour = numOfFormulationFour;
    }

    public Integer getNumOfFormulationFive() {
        return numOfFormulationFive;
    }

    public void setNumOfFormulationFive(Integer numOfFormulationFive) {
        this.numOfFormulationFive = numOfFormulationFive;
    }

    public Integer getNumOfFormulationSix() {
        return numOfFormulationSix;
    }

    public void setNumOfFormulationSix(Integer numOfFormulationSix) {
        this.numOfFormulationSix = numOfFormulationSix;
    }

    public Integer getNumberOfFollowUpsForCriticallyIllPatients() {
        return numberOfFollowUpsForCriticallyIllPatients;
    }

    public void setNumberOfFollowUpsForCriticallyIllPatients(Integer numberOfFollowUpsForCriticallyIllPatients) {
        this.numberOfFollowUpsForCriticallyIllPatients = numberOfFollowUpsForCriticallyIllPatients;
    }

    public Integer getNumberOfCirticalRescueCases() {
        return numberOfCirticalRescueCases;
    }

    public void setNumberOfCirticalRescueCases(Integer numberOfCirticalRescueCases) {
        this.numberOfCirticalRescueCases = numberOfCirticalRescueCases;
    }

    public Integer getNumberOfDeaths() {
        return numberOfDeaths;
    }

    public void setNumberOfDeaths(Integer numberOfDeaths) {
        this.numberOfDeaths = numberOfDeaths;
    }
}

