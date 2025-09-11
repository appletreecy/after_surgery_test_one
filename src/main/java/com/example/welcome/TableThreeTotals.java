package com.example.welcome;

public record TableThreeTotals(
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
) {}
