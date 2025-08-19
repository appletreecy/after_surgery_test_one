package com.example.welcome;
import com.example.welcome.model.AfterSurgery;
import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("afterSurgeryTableOne")
public class AfterSurgeryTableOneController {

    @Autowired
    private AfterSurgeryTableOneRepository afterSurgeryTableOneRepository;

    @GetMapping({"/", ""})
    public String showTableOne(Model model){

        List<AfterSurgeryTableOne> records = afterSurgeryTableOneRepository.findAll();
        int totalVisits = records.stream()
                .filter(r -> r.getNumOfPostoperativeVisits() != null)
                .mapToInt(AfterSurgeryTableOne::getNumOfPostoperativeVisits)
                .sum();

        int totalPostoperativeAnalgesia = records.stream()
                        .filter(r -> r.getNumOfPostoperativeAnalgesiaCases() != null)
                                .mapToInt(AfterSurgeryTableOne::getNumOfPostoperativeAnalgesiaCases)
                                        .sum();

        int totalAdverseReactions = records.stream()
                        .filter(r -> r.getNumOfAdverseReactionCases() != null)
                                .mapToInt(AfterSurgeryTableOne::getNumOfAdverseReactionCases)
                                        .sum();

        int totalInadequateAnalgesia = records.stream()
                        .filter(r -> r.getNumOfInadequateAnalgesia() != null)
                                .mapToInt(AfterSurgeryTableOne::getNumOfInadequateAnalgesia)
                                        .sum();

        float proportionOfTotalPostoperativeAnalgesiaCases = (float) totalPostoperativeAnalgesia / totalVisits;

        float proportionOfTotalAdverseReactions = (float) totalAdverseReactions / totalPostoperativeAnalgesia;

        float proportionOfTotalInadequateAnalgesia = 1 - ((float) totalInadequateAnalgesia / totalPostoperativeAnalgesia);


        model.addAttribute("afterSurgeryTableOne", records);
        model.addAttribute("totalVisits", totalVisits);
        model.addAttribute("totalPostoperativeAnalgesia", totalPostoperativeAnalgesia);
        model.addAttribute("totalAdverseReactions", totalAdverseReactions);
        model.addAttribute("totalInadequateAnalgesia", totalInadequateAnalgesia);

        model.addAttribute("proportionOfTotalPostoperativeAnalgesiaCases", proportionOfTotalPostoperativeAnalgesiaCases);
        model.addAttribute("proportionOfTotalAdverseReactions", proportionOfTotalAdverseReactions);
        model.addAttribute("proportionOfTotalInadequateAnalgesia", proportionOfTotalInadequateAnalgesia);



        return "afterSurgeryTableOne";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("afterSurgeryTableOne", new AfterSurgeryTableOne());
        return "addAfterSurgeryTableOne";
    }

    @PostMapping("/add")
    public String submitForm(@ModelAttribute AfterSurgeryTableOne afterSurgeryTableOne){
        afterSurgeryTableOneRepository.save(afterSurgeryTableOne);
        return "redirect:/afterSurgeryTableOne";
    }

    @GetMapping("/delete")
    public String showDeleteForm() {
        return "deleteAfterSurgeryTableOne";
    }

    @PostMapping("/delete")
    public String deleteRecord(@RequestParam("id") Long id, Model model) {
        boolean exists = afterSurgeryTableOneRepository.existsById(id);
        if (exists) {
            afterSurgeryTableOneRepository.deleteById(id);
            model.addAttribute("message", "Record with ID " + id + " has been deleted.");
        } else {
            model.addAttribute("message", "No record found with ID " + id + ".");
        }
        return "deleteAfterSurgeryTableOne";
    }

    // Get: Show update form one
    @GetMapping("/editone")
    public String showEditFormOne() {
        return "editAfterSurgeryTableOneOne";
    }

    // GET: Show update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AfterSurgeryTableOne record = afterSurgeryTableOneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));

        System.out.println("Loaded date:... " + record.getDate()); // 🔍 Check if null
        model.addAttribute("afterSurgeryTableOne", record);
        return "editAfterSurgeryTableOne";
    }

    // POST: Handle update form submission
    @PostMapping("/edit")
    public String updateAfterSurgery(@ModelAttribute AfterSurgeryTableOne record) {
        afterSurgeryTableOneRepository.save(record);
        return "redirect:/afterSurgeryTableOne"; // Redirect to dashboard
    }
}

