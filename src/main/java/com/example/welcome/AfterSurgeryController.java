package com.example.welcome;

import com.example.welcome.model.AfterSurgery;
import com.example.welcome.repository.AfterSurgeryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("afterSurgery")
public class AfterSurgeryController {

    @Autowired
    private AfterSurgeryRepository afterSurgeryRepository;

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("afterSurgery", new AfterSurgery());
        return "add-afterSurgery";
    }

    @PostMapping("/add")
    public String submitForm(@ModelAttribute AfterSurgery afterSurgery){
        afterSurgeryRepository.save(afterSurgery);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String showDeleteForm() {
        return "delete-afterSurgery";
    }

    @PostMapping("/delete")
    public String deleteRecord(@RequestParam("id") Long id, Model model) {
        boolean exists = afterSurgeryRepository.existsById(id);
        if (exists) {
            afterSurgeryRepository.deleteById(id);
            model.addAttribute("message", "Record with ID " + id + " has been deleted.");
        } else {
            model.addAttribute("message", "No record found with ID " + id + ".");
        }
        return "delete-afterSurgery";
    }

    // GET: Show update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AfterSurgery record = afterSurgeryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));

        System.out.println("Loaded date: " + record.getDate()); // 🔍 Check if null
        model.addAttribute("afterSurgery", record);
        return "edit-afterSurgery";
    }

    // POST: Handle update form submission
    @PostMapping("/edit")
    public String updateAfterSurgery(@ModelAttribute AfterSurgery record) {
        afterSurgeryRepository.save(record);
        return "redirect:/"; // Redirect to dashboard
    }

    // Get: Show update form one
    @GetMapping("/editone")
    public String showEditFormOne() {
        return "edit-afterSurgeryOne";
    }


}
