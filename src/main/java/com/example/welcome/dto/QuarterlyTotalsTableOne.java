package com.example.welcome.dto;

public record QuarterlyTotalsTableOne(
        Integer year,
        Integer quarter,
        Long totalVisits,
        Long totalAnalgesia,
        Long totalAdverse,
        Long totalInadequate
) {
    public String yearQuarter() {
        return String.format("%04d-Q%d", year, quarter);
    }
}
