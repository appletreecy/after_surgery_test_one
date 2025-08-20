package com.example.welcome;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.repository.AfterSurgeryTableThreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import org.springframework.transaction.annotation.Transactional;  // For @Transactional
import java.util.HashSet;          // For HashSet
import java.util.LinkedHashSet;    // For LinkedHashSet


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

    // Add this to the controller
    @GetMapping("/upload")
    public String showUploadForm() {
        return "uploadAfterSurgeryTableThree";
    }

    @PostMapping("/upload")
    @Transactional // so either everything valid saves, or nothing
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        List<AfterSurgeryTableThree> rows = new ArrayList<>();
        List<Integer> badColumnLines = new ArrayList<>();
        Set<LocalDate> seenInFile = new HashSet<>();
        Set<LocalDate> dupInFile = new LinkedHashSet<>();
        int lineNo = 0;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNo++;
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;
                if (lineNo == 1 && trimmed.toLowerCase().startsWith("date,")) continue;

                String[] f = trimmed.split(",", -1);
                if (f.length != 10) {
                    badColumnLines.add(lineNo);
                    continue;
                }

                LocalDate date = LocalDate.parse(f[0].trim());
                if (!seenInFile.add(date)) {
                    dupInFile.add(date);
                }

                AfterSurgeryTableThree r = new AfterSurgeryTableThree();
                r.setDate(date);
                r.setNumOfJointComplicationCount(Integer.parseInt(f[1].trim()));
                r.setNumOfMotorDysfunctionCount(Integer.parseInt(f[2].trim()));
                r.setNumOfTraumaComplicationCount(Integer.parseInt(f[3].trim()));
                r.setNumOfAnkleComplicationCount(Integer.parseInt(f[4].trim()));
                r.setNumOfPediatricAdverseEventCount(Integer.parseInt(f[5].trim()));
                r.setNumOfSpinalComplicationCount(Integer.parseInt(f[6].trim()));
                r.setNumOfHandSurgeryComplicationCount(Integer.parseInt(f[7].trim()));
                r.setNumOfObstetricAdverseEventCount(Integer.parseInt(f[8].trim()));
                r.setNumOfGynecologicalAdverseEventCount(Integer.parseInt(f[9].trim()));

                rows.add(r);
            }
        } catch (Exception e) {
            model.addAttribute("message", "Failed to read CSV: " + e.getMessage());
            return "uploadAfterSurgeryTableThree";
        }

        // Column count errors
        if (!badColumnLines.isEmpty()) {
            model.addAttribute("message",
                    "Error: some lines are missing columns (need 10). Problem lines: " + badColumnLines);
            return "uploadAfterSurgeryTableThree";
        }

        // Duplicates *within* the file
        if (!dupInFile.isEmpty()) {
            model.addAttribute("message",
                    "Error: duplicate dates found in the file: " + dupInFile);
            return "uploadAfterSurgeryTableThree";
        }

        // Conflicts with DB
        Set<LocalDate> dates = rows.stream().map(AfterSurgeryTableThree::getDate).collect(java.util.stream.Collectors.toSet());
        Set<LocalDate> exists = afterSurgeryTableThreeRepository.findExistingDates(dates);
        if (!exists.isEmpty()) {
            model.addAttribute("message",
                    "Error: these dates already exist in the system: " + exists +
                            ". Please remove them or switch to 'update existing' mode.");
            return "uploadAfterSurgeryTableThree";
        }

        // All good → save
        afterSurgeryTableThreeRepository.saveAll(rows);
        model.addAttribute("message", "Successfully uploaded " + rows.size() + " records.");
        return "uploadAfterSurgeryTableThree";
    }


}

