// src/main/java/com/example/welcome/dto/QuarterlyTotalsTableFour.java
package com.example.welcome.dto;

public record QuarterlyTotalsTableFive(
        Integer year,
        Integer quarter,
        Long totalNumOfFollowUpsForCriticallyIllPatients,
        Long totalNumOfCriticalRescueCases,
        Long totalNumOfDeaths
) {
    public String yearQuarter() {
        return String.format("%04d-Q%d", year, quarter);
    }
}
