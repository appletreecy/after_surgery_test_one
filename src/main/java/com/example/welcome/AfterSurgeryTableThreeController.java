package com.example.welcome;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.repository.AfterSurgeryTableThreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;

import java.util.List;

@Controller
@RequestMapping("afterSurgeryTableThree")
public class AfterSurgeryTableThreeController {

    @Autowired
    private AfterSurgeryTableThreeRepository afterSurgeryTableThreeRepository;

    @Autowired
    private AfterSurgeryTableOneRepository afterSurgeryRepositoryTableOne;

    @GetMapping({"/", ""})
    public String showTableOne(Model model){

        List<AfterSurgeryTableThree> tableThreeRecords = afterSurgeryTableThreeRepository.findAll();
        int totalNumOfJointComplicationCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfJointComplicationCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfJointComplicationCount)
                .sum();

        int totalNumOfMotorDysfunctionCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfMotorDysfunctionCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfMotorDysfunctionCount)
                .sum();

        int totalNumOfTraumaComplicationCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfTraumaComplicationCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfTraumaComplicationCount)
                .sum();

        int totalNumOfAnkleComplicationCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfAnkleComplicationCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfAnkleComplicationCount)
                .sum();

        int totalNumOfPediatricAdverseEventCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfPediatricAdverseEventCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfPediatricAdverseEventCount)
                .sum();

        int totalNumOfSpinalComplicationCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfSpinalComplicationCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfSpinalComplicationCount)
                .sum();

        int totalNumOfHandSurgeryComplicationCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfHandSurgeryComplicationCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfHandSurgeryComplicationCount)
                .sum();

        int totalNumOfObstetricAdverseEventCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfObstetricAdverseEventCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfObstetricAdverseEventCount)
                .sum();

        int totalNumOfGynecologicalAdverseEventCount = tableThreeRecords.stream()
                .filter(r -> r.getNumOfGynecologicalAdverseEventCount() != null)
                .mapToInt(AfterSurgeryTableThree::getNumOfGynecologicalAdverseEventCount)
                .sum();

        List<AfterSurgeryTableOne> tableOneRecords = afterSurgeryRepositoryTableOne.findAll();

        // TableOne
        int totalAdverseReactions = tableOneRecords.stream()
                .filter(r -> r.getNumOfAdverseReactionCases() != null)
                .mapToInt(AfterSurgeryTableOne::getNumOfAdverseReactionCases)
                .sum();

        float proportionOfJointComplicationCount = (float) totalNumOfJointComplicationCount / totalAdverseReactions;

        float proportionOfMotorDysfunctionCount = (float) totalNumOfMotorDysfunctionCount / totalAdverseReactions;

        float proportionOfTraumaComplicationCount = (float) totalNumOfTraumaComplicationCount / totalAdverseReactions;

        float proportionOfAnkleComplicationCount = (float) totalNumOfAnkleComplicationCount / totalAdverseReactions;

        float proportionOfPediatricAdverseEventCount = (float) totalNumOfPediatricAdverseEventCount / totalAdverseReactions;

        float proportionOfSpinalComplicationCount = (float) totalNumOfSpinalComplicationCount / totalAdverseReactions;

        float proportionOfHandSurgeryComplicationCount = (float) totalNumOfHandSurgeryComplicationCount / totalAdverseReactions;


        model.addAttribute("tableThreeRecords", tableThreeRecords);

        model.addAttribute("totalNumOfJointComplicationCount", totalNumOfJointComplicationCount);
        model.addAttribute("totalNumOfMotorDysfunctionCount", totalNumOfMotorDysfunctionCount);
        model.addAttribute("totalNumOfTraumaComplicationCount", totalNumOfTraumaComplicationCount);
        model.addAttribute("totalNumOfAnkleComplicationCount", totalNumOfAnkleComplicationCount);
        model.addAttribute("totalNumOfPediatricAdverseEventCount", totalNumOfPediatricAdverseEventCount);
        model.addAttribute("totalNumOfSpinalComplicationCount", totalNumOfSpinalComplicationCount);
        model.addAttribute("totalNumOfHandSurgeryComplicationCount", totalNumOfHandSurgeryComplicationCount);

        model.addAttribute("proportionOfJointComplicationCount", proportionOfJointComplicationCount);
        model.addAttribute("proportionOfMotorDysfunctionCount", proportionOfMotorDysfunctionCount);
        model.addAttribute("proportionOfTraumaComplicationCount", proportionOfTraumaComplicationCount);
        model.addAttribute("proportionOfAnkleComplicationCount", proportionOfAnkleComplicationCount);
        model.addAttribute("proportionOfPediatricAdverseEventCount", proportionOfPediatricAdverseEventCount);
        model.addAttribute("proportionOfSpinalComplicationCount", proportionOfSpinalComplicationCount);
        model.addAttribute("proportionOfHandSurgeryComplicationCount", proportionOfHandSurgeryComplicationCount);



        return "afterSurgeryTableThree";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("afterSurgeryTableThree", new AfterSurgeryTableThree());
        return "addAfterSurgeryTableThree";
    }

    @PostMapping("/add")
    public String submitForm(@ModelAttribute AfterSurgeryTableThree afterSurgeryTableThree){
        afterSurgeryTableThreeRepository.save(afterSurgeryTableThree);
        return "redirect:/afterSurgeryTableThree";
    }

    @GetMapping("/delete")
    public String showDeleteForm() {
        return "deleteAfterSurgeryTableThree";
    }

    @PostMapping("/delete")
    public String deleteRecord(@RequestParam("id") Long id, Model model) {
        boolean exists = afterSurgeryTableThreeRepository.existsById(id);
        if (exists) {
            afterSurgeryTableThreeRepository.deleteById(id);
            model.addAttribute("message", "Record with ID " + id + " has been deleted.");
        } else {
            model.addAttribute("message", "No record found with ID " + id + ".");
        }
        return "deleteAfterSurgeryTableThree";
    }

    // Get: Show update form one
    @GetMapping("/editone")
    public String showEditFormOne() {
        return "editAfterSurgeryTableThreeOne";
    }

    // GET: Show update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AfterSurgeryTableThree record = afterSurgeryTableThreeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));

        System.out.println("Loaded date: " + record.getDate()); // 🔍 Check if null
        model.addAttribute("afterSurgeryTableThree", record);
        return "editAfterSurgeryTableThree";
    }

    // POST: Handle update form submission
    @PostMapping("/edit")
    public String updateAfterSurgery(@ModelAttribute AfterSurgeryTableThree record) {
        afterSurgeryTableThreeRepository.save(record);
        return "redirect:/afterSurgeryTableThree"; // Redirect to dashboard
    }

}

