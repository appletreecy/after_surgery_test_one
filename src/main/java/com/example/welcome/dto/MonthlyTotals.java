// src/main/java/com/example/welcome/dto/MonthlyTotals.java
package com.example.welcome.dto;

public record MonthlyTotals(
        Integer year,
        Integer month,
        Long totalVisits,
        Long totalAnalgesia,
        Long totalAdverse,
        Long totalInadequate
) {
    public String yearMonth() {
        return String.format("%04d-%02d", year, month);
    }
}

