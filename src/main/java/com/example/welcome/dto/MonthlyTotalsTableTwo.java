package com.example.welcome.dto;

public record MonthlyTotalsTableTwo(
        Integer year,
        Integer month,
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
    public String yearMonth() {
        return String.format("%04d-%02d", year, month);
    }
}
