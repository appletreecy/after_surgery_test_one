package com.example.welcome.dto;

public record QuarterlyTotalsTableTwo(
        Integer year,
        Integer quarter,
        Long totalNumOfNauseaAndVomiting,
        Long totalNumOfDizziness,
        Long totalNumOfNauseaAndVomitingAndDizziness,
        Long totalNumOfItching,
        Long totalNumOfAllergicRash,
        Long totalNumOfProlongedAnestheticRecovery,
        Long totalNumOfPunctureSiteAbnormality,
        Long totalNumOfAbdominalDistension,
        Long totalNumOfEndotrachealIntubationDiscomfort,
        Long totalNumOfEpigastricPain,
        Long totalNumOfDelirium,
        Long totalNumOfChestDiscomfort,
        Long totalNumOfTourniquetReaction,
        Long totalNumOfOther
) {
    public String yearQuarter() {
        return String.format("%04d-Q%d", year, quarter);
    }
}
