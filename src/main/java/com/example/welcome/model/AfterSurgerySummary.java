package com.example.welcome.model;

import java.time.LocalDate;

public class AfterSurgerySummary {
    private Long id;
    private LocalDate date;
    private Integer analgesiaCases;
    private Integer adverseReactions;
    private Double adverseReactionRate;

    public AfterSurgerySummary(Long id, LocalDate date, Integer analgesia, Integer adverse){
        this.id = id;
        this.date = date;
        this.analgesiaCases = analgesia;
        this.adverseReactions = adverse;
        if (analgesia != null && analgesia > 0 && adverse != null){
            this.adverseReactionRate = (double) adverse / analgesia;
        } else {
            this.adverseReactionRate = null;
        }
    }

    // Getters
    public Long getId() {return id;}
    public LocalDate getDate(){ return date;}
    public Integer getAnalgesiaCases() {return analgesiaCases;}
    public Integer getAdverseReactions() {return adverseReactions;}
    public Double getAdverseReactionRate() {return adverseReactionRate;}
}
