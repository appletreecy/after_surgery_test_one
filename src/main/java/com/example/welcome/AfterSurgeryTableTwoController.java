package com.example.welcome;
import com.example.welcome.model.AfterSurgeryTableTwo;
import com.example.welcome.repository.AfterSurgeryTableTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;

import java.util.List;

@Controller
@RequestMapping("afterSurgeryTableTwo")
public class AfterSurgeryTableTwoController {

    @Autowired
    private AfterSurgeryTableTwoRepository afterSurgeryTableTwoRepository;

    @Autowired
    private AfterSurgeryTableOneRepository afterSurgeryRepositoryTableOne;


    @GetMapping({"/", ""})
    public String showTableOne(Model model){

        List<AfterSurgeryTableTwo> tableTwoRecords = afterSurgeryTableTwoRepository.findAll();

        List<AfterSurgeryTableOne> tableOneRecords = afterSurgeryRepositoryTableOne.findAll();

        int totalNauseaAndVomiting = tableTwoRecords.stream()
                .filter(r -> r.getNumOfNauseaAndVomiting() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfNauseaAndVomiting)
                .sum();

        int totalDizziness = tableTwoRecords.stream()
                .filter(r -> r.getNumOfDizziness() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfDizziness)
                .sum();

        int totalNauseaAndVomitingAndDizziness = tableTwoRecords.stream()
                .filter(r -> r.getNumOfNauseaAndVomitingAndDizziness() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfNauseaAndVomitingAndDizziness)
                .sum();

        int totalAllergicRash = tableTwoRecords.stream()
                .filter(r -> r.getNumOfAllergicRash() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfAllergicRash)
                .sum();

        int totalProlongedAnestheticRecovery = tableTwoRecords.stream()
                .filter(r -> r.getNumOfProlongedAnestheticRecovery() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfProlongedAnestheticRecovery)
                .sum();

        int totalPunctureSiteAbnormality = tableTwoRecords.stream()
                .filter(r -> r.getNumOfPunctureSiteAbnormality() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfPunctureSiteAbnormality)
                .sum();

        int totalAbdominalDistension = tableTwoRecords.stream()
                .filter(r -> r.getNumOfAbdominalDistension() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfAbdominalDistension)
                .sum();

        int totalEndotrachealIntubationDiscomfort = tableTwoRecords.stream()
                .filter(r -> r.getNumOfEndotrachealIntubationDiscomfort() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfEndotrachealIntubationDiscomfort)
                .sum();

        int totalEpigastricPain = tableTwoRecords.stream()
                .filter(r -> r.getNumOfEpigastricPain() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfEpigastricPain)
                .sum();

        int totalDelirium = tableTwoRecords.stream()
                .filter(r -> r.getNumOfDelirium() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfDelirium)
                .sum();

        int totalChestDiscomfort = tableTwoRecords.stream()
                .filter(r -> r.getNumOfChestDiscomfort() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfChestDiscomfort)
                .sum();

        int totalTourniquetReaction = tableTwoRecords.stream()
                .filter(r -> r.getNumOfTourniquetReaction() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfTourniquetReaction)
                .sum();

        int totalOther = tableTwoRecords.stream()
                .filter(r -> r.getNumOfOther() != null)
                .mapToInt(AfterSurgeryTableTwo::getNumOfOther)
                .sum();

        // TableOne
        int totalAdverseReactions = tableOneRecords.stream()
                .filter(r -> r.getNumOfAdverseReactionCases() != null)
                .mapToInt(AfterSurgeryTableOne::getNumOfAdverseReactionCases)
                .sum();

        float proportionOfNauseaAndVomiting = (float) totalNauseaAndVomiting / totalAdverseReactions;
        float proportionOfDizziness = (float) totalDizziness / totalAdverseReactions;
        float proportionOfNauseaAndVomitingAndDizziness = (float) totalNauseaAndVomitingAndDizziness / totalAdverseReactions;
        float proportionOfAllergicRash = (float) totalAllergicRash / totalAdverseReactions;
        float proportionOfProlongedAnestheticRecovery = (float) totalProlongedAnestheticRecovery / totalAdverseReactions;
        float proportionOfPunctureSiteAbnormality = (float) totalPunctureSiteAbnormality / totalAdverseReactions;
        float proportionOfAbdominalDistension = (float) totalAbdominalDistension / totalAdverseReactions;
        float proportionOfEndotrachealIntubationDiscomfort = (float) totalEndotrachealIntubationDiscomfort / totalAdverseReactions;
        float proportionOfEpigastricPain = (float) totalEpigastricPain / totalAdverseReactions;
        float proportionOfDelirium = (float) totalDelirium / totalAdverseReactions;
        float proportionOfChestDiscomfort = (float) totalChestDiscomfort / totalAdverseReactions;
        float proportionOfTourniquetReaction = (float) totalTourniquetReaction / totalAdverseReactions;
        float proportionOfOther = (float) totalOther / totalAdverseReactions;


        model.addAttribute("tableTwoRecords", tableTwoRecords);

        model.addAttribute("totalNauseaAndVomiting", totalNauseaAndVomiting);
        model.addAttribute("totalDizziness", totalDizziness);
        model.addAttribute("totalNauseaAndVomitingAndDizziness", totalNauseaAndVomitingAndDizziness);
        model.addAttribute("totalAllergicRash", totalAllergicRash);
        model.addAttribute("totalProlongedAnestheticRecovery", totalProlongedAnestheticRecovery);
        model.addAttribute("totalPunctureSiteAbnormality", totalPunctureSiteAbnormality);
        model.addAttribute("totalAbdominalDistension", totalAbdominalDistension);
        model.addAttribute("totalEndotrachealIntubationDiscomfort", totalEndotrachealIntubationDiscomfort);
        model.addAttribute("totalEpigastricPain", totalEpigastricPain);
        model.addAttribute("totalDelirium", totalDelirium);
        model.addAttribute("totalChestDiscomfort", totalChestDiscomfort);
        model.addAttribute("totalTourniquetReaction", totalTourniquetReaction);
        model.addAttribute("totalOther", totalOther);

        model.addAttribute("proportionOfNauseaAndVomiting", proportionOfNauseaAndVomiting);
        model.addAttribute("proportionOfDizziness", proportionOfDizziness);
        model.addAttribute("proportionOfNauseaAndVomitingAndDizziness", proportionOfNauseaAndVomitingAndDizziness);
        model.addAttribute("proportionOfAllergicRash", proportionOfAllergicRash);
        model.addAttribute("proportionOfProlongedAnestheticRecovery", proportionOfProlongedAnestheticRecovery);
        model.addAttribute("proportionOfPunctureSiteAbnormality", proportionOfPunctureSiteAbnormality);
        model.addAttribute("proportionOfAbdominalDistension", proportionOfAbdominalDistension);
        model.addAttribute("proportionOfEndotrachealIntubationDiscomfort", proportionOfEndotrachealIntubationDiscomfort);
        model.addAttribute("proportionOfEpigastricPain", proportionOfEpigastricPain);
        model.addAttribute("proportionOfDelirium", proportionOfDelirium);
        model.addAttribute("proportionOfChestDiscomfort", proportionOfChestDiscomfort);
        model.addAttribute("proportionOfTourniquetReaction", proportionOfTourniquetReaction);
        model.addAttribute("proportionOfOther", proportionOfOther);



//        model.addAttribute("totalPostoperativeAnalgesia", totalPostoperativeAnalgesia);
//        model.addAttribute("totalAdverseReactions", totalAdverseReactions);
//        model.addAttribute("totalInadequateAnalgesia", totalInadequateAnalgesia);
//
//        model.addAttribute("proportionOfTotalPostoperativeAnalgesiaCases", proportionOfTotalPostoperativeAnalgesiaCases);
//        model.addAttribute("proportionOfTotalAdverseReactions", proportionOfTotalAdverseReactions);
//        model.addAttribute("proportionOfTotalInadequateAnalgesia", proportionOfTotalInadequateAnalgesia);



        return "afterSurgeryTableTwo";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("afterSurgeryTableTwo", new AfterSurgeryTableTwo());
        return "addAfterSurgeryTableTwo";
    }

    @PostMapping("/add")
    public String submitForm(@ModelAttribute AfterSurgeryTableTwo afterSurgeryTableTwo){
        afterSurgeryTableTwoRepository.save(afterSurgeryTableTwo);
        return "redirect:/afterSurgeryTableTwo";
    }

    @GetMapping("/delete")
    public String showDeleteForm() {
        return "deleteAfterSurgeryTableTwo";
    }

    @PostMapping("/delete")
    public String deleteRecord(@RequestParam("id") Long id, Model model) {
        boolean exists = afterSurgeryTableTwoRepository.existsById(id);
        if (exists) {
            afterSurgeryTableTwoRepository.deleteById(id);
            model.addAttribute("message", "Record with ID " + id + " has been deleted.");
        } else {
            model.addAttribute("message", "No record found with ID " + id + ".");
        }
        return "deleteAfterSurgeryTableTwo";
    }

    // Get: Show update form one
    @GetMapping("/editone")
    public String showEditFormOne() {
        return "editAfterSurgeryTableTwoOne";
    }

    // GET: Show update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AfterSurgeryTableTwo record = afterSurgeryTableTwoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));

        System.out.println("Loaded date: " + record.getDate()); // 🔍 Check if null
        model.addAttribute("afterSurgeryTableTwo", record);
        return "editAfterSurgeryTableTwo";
    }

    // POST: Handle update form submission
    @PostMapping("/edit")
    public String updateAfterSurgery(@ModelAttribute AfterSurgeryTableTwo record) {
        afterSurgeryTableTwoRepository.save(record);
        return "redirect:/afterSurgeryTableTwo"; // Redirect to dashboard
    }
}


