// src/main/java/com/example/welcome/dto/QuarterlyTotalsTableFour.java
package com.example.welcome.dto;

public record QuarterlyTotalsTableFour(
        Integer year,
        Integer quarter,
        Long totalNumOfFormulationOne,
        Long totalNumOfFormulationTwo,
        Long totalNumOfFormulationThree,
        Long totalNumOfFormulationFour,
        Long totalNumOfFormulationFive,
        Long totalNumOfFormulationSix
) {
    public String yearQuarter() {
        return String.format("%04d-Q%d", year, quarter);
    }
}

