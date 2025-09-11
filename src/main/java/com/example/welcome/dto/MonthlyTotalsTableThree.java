package com.example.welcome.dto;

public record MonthlyTotalsTableThree(
        Integer year,
        Integer month,
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
    public String yearMonth() {
        return String.format("%04d-%02d", year, month);
    }
}
