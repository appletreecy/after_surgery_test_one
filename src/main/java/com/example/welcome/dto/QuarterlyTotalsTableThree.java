package com.example.welcome.dto;

public record QuarterlyTotalsTableThree(
        Integer year,
        Integer quarter,
        Long totalNumOfJointComplicationCount,
        Long totalNumOfMotorDysfunctionCount,
        Long totalNumOfTraumaComplicationCount,
        Long totalNumOfAnkleComplicationCount,
        Long totalNumOfPediatricAdverseEventCount,
        Long totalNumOfSpinalComplicationCount,
        Long totalNumOfHandSurgeryComplicationCount,
        Long totalNumOfObstetricAdverseEventCount,
        Long totalNumOfGynecologicalAdverseEventCount,
        Long totalNumOfSurgicalTreatmentCount
) {
    public String yearQuarter() {
        return String.format("%04d-Q%d", year, quarter);
    }
}
