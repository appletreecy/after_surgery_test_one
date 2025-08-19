// AfterSurgery.java
package com.example.welcome.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "afterSurgery")
public class AfterSurgery {

    @Id
    private Long id;
    private LocalDate date;
    private Integer numOfPostoperativeVisits;
    private Integer numOfPostoperativeAnalgesiaCases;
    private Integer numOfAdverseReactionCases;
    private Integer nauseaAndVomiting;
    private Integer dizziness;
    private Integer nauseaVomitingDizziness;
    private Integer skinItching;
    private Integer allergicRash;
    private Integer delayedRecoveryFromAnesthesia;
    private Integer abnormalityAtThePunctureSite;
    private Integer bloating;
    private String otherConditions;
    private Integer numberOfFollowupVisitsForCriticallyIIIPatients;
    private Integer poorAnalgesicEffect;
    private Integer totalNumberOfCriticallyIIIPatientsVisited;
    private Integer numberOfJointComplications;
    private Integer numberOfMotorDysfunctionClass;
    private Integer numberOfWoundComplications;
    private Integer numberOfFootAndAnkleComplications;
    private Integer numberOfPediatricAdverseEvents;
    private Integer numberOfSpinalComplications;
    private Integer numberOfHandSurgeryComplications;
    private Integer formulaOne;
    private Integer formulaTwo;
    private Integer formulaThree;
    private Integer formulaFour;
    private Integer formulaFive;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Integer getNumOfPostoperativeVisits() { return numOfPostoperativeVisits; }
    public void setNumOfPostoperativeVisits(Integer value) { this.numOfPostoperativeVisits = value; }

    public Integer getNumOfPostoperativeAnalgesiaCases() { return numOfPostoperativeAnalgesiaCases; }
    public void setNumOfPostoperativeAnalgesiaCases(Integer value) { this.numOfPostoperativeAnalgesiaCases = value; }

    public Integer getNumOfAdverseReactionCases() { return numOfAdverseReactionCases; }
    public void setNumOfAdverseReactionCases(Integer value) { this.numOfAdverseReactionCases = value; }

    public Integer getNauseaAndVomiting() { return nauseaAndVomiting; }
    public void setNauseaAndVomiting(Integer value) { this.nauseaAndVomiting = value; }

    public Integer getDizziness() { return dizziness; }
    public void setDizziness(Integer value) { this.dizziness = value; }

    public Integer getNauseaVomitingDizziness() { return nauseaVomitingDizziness; }
    public void setNauseaVomitingDizziness(Integer value) { this.nauseaVomitingDizziness = value; }

    public Integer getSkinItching() { return skinItching; }
    public void setSkinItching(Integer value) { this.skinItching = value; }

    public Integer getAllergicRash() { return allergicRash; }
    public void setAllergicRash(Integer value) { this.allergicRash = value; }

    public Integer getDelayedRecoveryFromAnesthesia() { return delayedRecoveryFromAnesthesia; }
    public void setDelayedRecoveryFromAnesthesia(Integer value) { this.delayedRecoveryFromAnesthesia = value; }

    public Integer getAbnormalityAtThePunctureSite() { return abnormalityAtThePunctureSite; }
    public void setAbnormalityAtThePunctureSite(Integer value) { this.abnormalityAtThePunctureSite = value; }

    public Integer getBloating() { return bloating; }
    public void setBloating(Integer value) { this.bloating = value; }

    public String getOtherConditions() { return otherConditions; }
    public void setOtherConditions(String value) { this.otherConditions = value; }

    public Integer getNumberOfFollowupVisitsForCriticallyIIIPatients() { return numberOfFollowupVisitsForCriticallyIIIPatients; }
    public void setNumberOfFollowupVisitsForCriticallyIIIPatients(Integer value) { this.numberOfFollowupVisitsForCriticallyIIIPatients = value; }

    public Integer getPoorAnalgesicEffect() { return poorAnalgesicEffect; }
    public void setPoorAnalgesicEffect(Integer value) { this.poorAnalgesicEffect = value; }

    public Integer getTotalNumberOfCriticallyIIIPatientsVisited() { return totalNumberOfCriticallyIIIPatientsVisited; }
    public void setTotalNumberOfCriticallyIIIPatientsVisited(Integer value) { this.totalNumberOfCriticallyIIIPatientsVisited = value; }

    public Integer getNumberOfJointComplications() { return numberOfJointComplications; }
    public void setNumberOfJointComplications(Integer value) { this.numberOfJointComplications = value; }

    public Integer getNumberOfMotorDysfunctionClass() { return numberOfMotorDysfunctionClass; }
    public void setNumberOfMotorDysfunctionClass(Integer value) { this.numberOfMotorDysfunctionClass = value; }

    public Integer getNumberOfWoundComplications() { return numberOfWoundComplications; }
    public void setNumberOfWoundComplications(Integer value) { this.numberOfWoundComplications = value; }

    public Integer getNumberOfFootAndAnkleComplications() { return numberOfFootAndAnkleComplications; }
    public void setNumberOfFootAndAnkleComplications(Integer value) { this.numberOfFootAndAnkleComplications = value; }

    public Integer getNumberOfPediatricAdverseEvents() { return numberOfPediatricAdverseEvents; }
    public void setNumberOfPediatricAdverseEvents(Integer value) { this.numberOfPediatricAdverseEvents = value; }

    public Integer getNumberOfSpinalComplications() { return numberOfSpinalComplications; }
    public void setNumberOfSpinalComplications(Integer value) { this.numberOfSpinalComplications = value; }

    public Integer getNumberOfHandSurgeryComplications() { return numberOfHandSurgeryComplications; }
    public void setNumberOfHandSurgeryComplications(Integer value) { this.numberOfHandSurgeryComplications = value; }

    public Integer getFormulaOne() { return formulaOne; }
    public void setFormulaOne(Integer value) { this.formulaOne = value; }

    public Integer getFormulaTwo() { return formulaTwo; }
    public void setFormulaTwo(Integer value) { this.formulaTwo = value; }

    public Integer getFormulaThree() { return formulaThree; }
    public void setFormulaThree(Integer value) { this.formulaThree = value; }

    public Integer getFormulaFour() { return formulaFour; }
    public void setFormulaFour(Integer value) { this.formulaFour = value; }

    public Integer getFormulaFive() { return formulaFive; }
    public void setFormulaFive(Integer value) { this.formulaFive = value; }
}
