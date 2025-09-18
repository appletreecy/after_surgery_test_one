package com.example.welcome.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "afterSurgeryTableFive",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "date")
        }
)
public class AfterSurgeryTableFive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in MySQL
    private Long id;

    private LocalDate date;

    private String criticalPatientsName;
    private Integer numberOfFollowUpsForCriticallyIllPatients;
    private String visitFindingsForCriticalPatient;
    private Integer numberOfCriticalRescueCases;
    private Integer numberOfDeaths;


    // Getters and Setters

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

    public Integer getNumberOfFollowUpsForCriticallyIllPatients() {
        return numberOfFollowUpsForCriticallyIllPatients;
    }

    public void setNumberOfFollowUpsForCriticallyIllPatients(Integer numberOfFollowUpsForCriticallyIllPatients) {
        this.numberOfFollowUpsForCriticallyIllPatients = numberOfFollowUpsForCriticallyIllPatients;
    }

    public Integer getNumberOfCriticalRescueCases() {
        return numberOfCriticalRescueCases;
    }

    public void setNumberOfCriticalRescueCases(Integer numberOfCriticalRescueCases) {
        this.numberOfCriticalRescueCases = numberOfCriticalRescueCases;
    }

    public Integer getNumberOfDeaths() {
        return numberOfDeaths;
    }

    public void setNumberOfDeaths(Integer numberOfDeaths) {
        this.numberOfDeaths = numberOfDeaths;
    }

    public String getCriticalPatientsName() {
        return criticalPatientsName;
    }

    public void setCriticalPatientsName(String criticalPatientsName) {
        this.criticalPatientsName = criticalPatientsName;
    }

    public String getVisitFindingsForCriticalPatient() {
        return visitFindingsForCriticalPatient;
    }

    public void setVisitFindingsForCriticalPatient(String visitFindingsForCriticalPatient) {
        this.visitFindingsForCriticalPatient = visitFindingsForCriticalPatient;
    }
}

