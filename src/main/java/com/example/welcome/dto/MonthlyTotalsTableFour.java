package com.example.welcome.dto;

public record MonthlyTotalsTableFour(
        Integer year,
        Integer month,
        Long totalNumOfFormulationOne,
        Long totalNumOfFormulationTwo,
        Long totalNumOfFormulationThree,
        Long totalNumOfFormulationFour,
        Long totalNumOfFormulationFive,
        Long totalNumOfFormulationSix
) {
    public String yearMonth() {
        return String.format("%04d-%02d", year, month);
    }
}
