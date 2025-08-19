package com.example.welcome.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "afterSurgeryTableOne")
public class AfterSurgeryTableOne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in MySQL
    private Long id;

    private LocalDate date;
    private Integer numOfPostoperativeVisits;
    private Integer numOfPostoperativeAnalgesiaCases;
    private Integer numOfAdverseReactionCases;
    private Integer numOfInadequateAnalgesia;

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

    public Integer getNumOfInadequateAnalgesia() { return numOfInadequateAnalgesia; }
    public void setNumOfInadequateAnalgesia(Integer value) { this.numOfInadequateAnalgesia = value; }
}
