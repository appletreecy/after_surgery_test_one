package com.example.welcome.dto;

public record MonthlyTotalsTableFive(
        Integer year,
        Integer month,
        Long totalNumOfFollowUpsForCriticallyIllPatients,
        Long totalNumOfCriticalRescueCases,
        Long totalNumOfDeaths
) {
    public String yearMonth() {
        return String.format("%04d-%02d", year, month);
    }
}
