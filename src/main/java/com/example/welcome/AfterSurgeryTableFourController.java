package com.example.welcome;
import com.example.welcome.model.AfterSurgeryTableFour;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.repository.AfterSurgeryTableFourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;

import java.util.List;

@Controller
@RequestMapping("afterSurgeryTableFour")
public class AfterSurgeryTableFourController {

    @Autowired
    private AfterSurgeryTableFourRepository afterSurgeryTableFourRepository;

    @Autowired
    private AfterSurgeryTableOneRepository afterSurgeryRepositoryTableOne;

    @GetMapping({"/", ""})
    public String showTableOne(Model model) {
        List<AfterSurgeryTableFour> tableFourRecords = afterSurgeryTableFourRepository.findAll();

        int totalNumOfFormulationOne = tableFourRecords.stream()
                .filter(r -> r.getNumOfFormulationOne() != null)
                .mapToInt(AfterSurgeryTableFour::getNumOfFormulationOne)
                .sum();

        int totalNumOfFormulationTwo = tableFourRecords.stream()
                .filter(r -> r.getNumOfFormulationTwo() != null)
                .mapToInt(AfterSurgeryTableFour::getNumOfFormulationTwo)
                .sum();

        int totalNumOfFormulationThree = tableFourRecords.stream()
                .filter(r -> r.getNumOfFormulationThree() != null)
                .mapToInt(AfterSurgeryTableFour::getNumOfFormulationThree)
                .sum();

        int totalNumOfFormulationFour = tableFourRecords.stream()
                .filter(r -> r.getNumOfFormulationFour() != null)
                .mapToInt(AfterSurgeryTableFour::getNumOfFormulationFour)
                .sum();

        int totalNumOfFormulationFive = tableFourRecords.stream()
                .filter(r -> r.getNumOfFormulationFive() != null)
                .mapToInt(AfterSurgeryTableFour::getNumOfFormulationFive)
                .sum();

        int totalNumOfFormulationSix = tableFourRecords.stream()
                .filter(r -> r.getNumOfFormulationSix() != null)
                .mapToInt(AfterSurgeryTableFour::getNumOfFormulationSix)
                .sum();



        // TableOne
        List<AfterSurgeryTableOne> tableOneRecords = afterSurgeryRepositoryTableOne.findAll();

        int totalAdverseReactions = tableOneRecords.stream()
                .filter(r -> r.getNumOfAdverseReactionCases() != null)
                .mapToInt(AfterSurgeryTableOne::getNumOfAdverseReactionCases)
                .sum();

        // Proportion

        float proportionOfFormulationOne = (float) totalNumOfFormulationOne / totalAdverseReactions;

        float proportionOfFormulationTwo = (float) totalNumOfFormulationTwo / totalAdverseReactions;

        float proportionOfFormulationThree = (float) totalNumOfFormulationThree / totalAdverseReactions;

        float proportionOfFormulationFour = (float) totalNumOfFormulationFour / totalAdverseReactions;

        float proportionOfFormulationFive = (float) totalNumOfFormulationFive / totalAdverseReactions;

        float proportionOfFormulationSix = (float) totalNumOfFormulationSix / totalAdverseReactions;

        model.addAttribute("tableFourRecords", tableFourRecords);

        model.addAttribute("totalNumOfFormulationOne", totalNumOfFormulationOne);
        model.addAttribute("totalNumOfFormulationTwo", totalNumOfFormulationTwo);
        model.addAttribute("totalNumOfFormulationThree", totalNumOfFormulationThree);
        model.addAttribute("totalNumOfFormulationFour", totalNumOfFormulationFour);
        model.addAttribute("totalNumOfFormulationFive", totalNumOfFormulationFive);
        model.addAttribute("totalNumOfFormulationSix", totalNumOfFormulationSix);

        model.addAttribute("proportionOfFormulationOne", proportionOfFormulationOne);
        model.addAttribute("proportionOfFormulationTwo", proportionOfFormulationTwo);
        model.addAttribute("proportionOfFormulationThree", proportionOfFormulationThree);
        model.addAttribute("proportionOfFormulationFour", proportionOfFormulationFour);
        model.addAttribute("proportionOfFormulationFive", proportionOfFormulationFive);
        model.addAttribute("proportionOfFormulationSix", proportionOfFormulationSix);


        return "afterSurgeryTableFour";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("afterSurgeryTableFour", new AfterSurgeryTableFour());
        return "addAfterSurgeryTableFour";
    }

    @PostMapping("/add")
    public String submitForm(@ModelAttribute AfterSurgeryTableFour afterSurgeryTableFour){
        afterSurgeryTableFourRepository.save(afterSurgeryTableFour);
        return "redirect:/afterSurgeryTableFour";
    }

    @GetMapping("/delete")
    public String showDeleteForm() {
        return "deleteAfterSurgeryTableFour";
    }

    @PostMapping("/delete")
    public String deleteRecord(@RequestParam("id") Long id, Model model) {
        boolean exists = afterSurgeryTableFourRepository.existsById(id);
        if (exists) {
            afterSurgeryTableFourRepository.deleteById(id);
            model.addAttribute("message", "Record with ID " + id + " has been deleted.");
        } else {
            model.addAttribute("message", "No record found with ID " + id + ".");
        }
        return "deleteAfterSurgeryTableOne";
    }

    // Get: Show update form one
    @GetMapping("/editone")
    public String showEditFormOne() {
        return "editAfterSurgeryTableFourOne";
    }

    // GET: Show update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AfterSurgeryTableFour record = afterSurgeryTableFourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));

        System.out.println("Loaded date: " + record.getDate()); // 🔍 Check if null
        model.addAttribute("afterSurgeryTableFour", record);
        return "editAfterSurgeryTableFour";
    }

    // POST: Handle update form submission
    @PostMapping("/edit")
    public String updateAfterSurgery(@ModelAttribute AfterSurgeryTableFour record) {
        afterSurgeryTableFourRepository.save(record);
        return "redirect:/afterSurgeryTableFour"; // Redirect to dashboard
    }

}
