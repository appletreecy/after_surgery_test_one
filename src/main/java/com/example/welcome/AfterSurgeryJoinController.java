// src/main/java/com/example/welcome/controller/AfterSurgeryJoinController.java
package com.example.welcome;

import com.example.welcome.dto.AfterSurgeryJoinDto;
import com.example.welcome.repository.AfterSurgeryJoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AfterSurgeryJoinController {

    @Autowired
    private AfterSurgeryJoinRepository joinRepository;

    @GetMapping("/afterSurgery/joined")
    public String showJoinedTable(Model model) {
        List<Object[]> raw = joinRepository.fetchJoinedData();
        List<AfterSurgeryJoinDto> joinedList = new ArrayList<>();

        for (Object[] row : raw) {
            AfterSurgeryJoinDto dto = new AfterSurgeryJoinDto();
            dto.setDate((row[0] instanceof java.sql.Date) ? ((java.sql.Date) row[0]).toLocalDate() : (LocalDate) row[0]);
            dto.setNumOfPostoperativeVisits((Integer) row[1]);
            dto.setNumOfPostoperativeAnalgesiaCases((Integer) row[2]);
            dto.setNumOfAdverseReactionCases((Integer) row[3]);
            dto.setNumOfInadequateAnalgesia((Integer) row[4]);
            dto.setNumOfNauseaAndVomiting((Integer) row[5]);
            dto.setNumOfDizziness((Integer) row[6]);
            dto.setNumOfNauseaAndVomitingAndDizziness((Integer) row[7]);
            dto.setNumOfItching((Integer) row[8]);
            dto.setNumOfAllergicRash((Integer) row[9]);
            dto.setNumOfProlongedAnestheticRecovery((Integer) row[10]);
            dto.setNumOfPunctureSiteAbnormality((Integer) row[11]);
            dto.setNumOfAbdominalDistension((Integer) row[12]);
            dto.setNumOfEndotrachealIntubationDiscomfort((Integer) row[13]);
            dto.setNumOfEpigastricPain((Integer) row[14]);
            dto.setNumOfDelirium((Integer) row[15]);
            dto.setNumOfChestDiscomfort((Integer) row[16]);
            dto.setNumOfTourniquetReaction((Integer) row[17]);
            dto.setNumOfOther((Integer) row[18]);
            dto.setOtherComments((String) row[19]);
            dto.setNumOfJointComplicationCount((Integer) row[20]);
            dto.setNumOfMotorDysfunctionCount((Integer) row[21]);
            dto.setNumOfTraumaComplicationCount((Integer) row[22]);
            dto.setNumOfAnkleComplicationCount((Integer) row[23]);
            dto.setNumOfPediatricAdverseEventCount((Integer) row[24]);
            dto.setNumOfSpinalComplicationCount((Integer) row[25]);
            dto.setNumOfHandSurgeryComplicationCount((Integer) row[26]);
            dto.setNumOfObstetricAdverseEventCount((Integer) row[27]);
            dto.setNumOfGynecologicalAdverseEventCount((Integer) row[28]);
            dto.setNumOfFormulationOne((Integer) row[29]);
            dto.setNumOfFormulationTwo((Integer) row[30]);
            dto.setNumOfFormulationThree((Integer) row[31]);
            dto.setNumOfFormulationFour((Integer) row[32]);
            dto.setNumOfFormulationFive((Integer) row[33]);
            dto.setNumOfFormulationSix((Integer) row[34]);
            joinedList.add(dto);
        }

        model.addAttribute("joinedData", joinedList);
        return "afterSurgeryJoinedTable";
    }
}

