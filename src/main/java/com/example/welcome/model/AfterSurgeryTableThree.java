package com.example.welcome.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "afterSurgeryTableThree")
public class AfterSurgeryTableThree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in MySQL
    private Long id;

    private LocalDate date;

    private Integer numOfJointComplicationCount;
    private Integer numOfMotorDysfunctionCount;
    private Integer numOfTraumaComplicationCount;
    private Integer numOfAnkleComplicationCount;
    private Integer numOfPediatricAdverseEventCount;
    private Integer numOfSpinalComplicationCount;
    private Integer numOfHandSurgeryComplicationCount;
    private Integer numOfObstetricAdverseEventCount;
    private Integer numOfGynecologicalAdverseEventCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

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
}
