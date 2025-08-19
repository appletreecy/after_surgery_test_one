package com.example.welcome.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "afterSurgeryTableFour")
public class AfterSurgeryTableFour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in MySQL
    private Long id;

    private LocalDate date;

    private Integer numOfFormulationOne;
    private Integer numOfFormulationTwo;
    private Integer numOfFormulationThree;
    private Integer numOfFormulationFour;
    private Integer numOfFormulationFive;
    private Integer numOfFormulationSix;

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
}
