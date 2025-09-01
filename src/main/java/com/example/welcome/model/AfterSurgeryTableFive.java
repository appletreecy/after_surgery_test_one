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
    private Integer numberOfFollowUpsForCriticallyIllPatients;
    private Integer numberOfCirticalRescueCases;
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

