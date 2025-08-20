package com.example.welcome;
import com.example.welcome.model.AfterSurgeryTableFour;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.repository.AfterSurgeryTableFourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

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

    // Add this to the controller
    @GetMapping("/upload")
    public String showUploadForm() {
        return "uploadAfterSurgeryTableFour";
    }

    @PostMapping("/upload")
    @Transactional // so either everything valid saves, or nothing
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        List<AfterSurgeryTableFour> rows = new ArrayList<>();
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
                if (f.length != 7) {
                    badColumnLines.add(lineNo);
                    continue;
                }

                LocalDate date = LocalDate.parse(f[0].trim());
                if (!seenInFile.add(date)) {
                    dupInFile.add(date);
                }

                AfterSurgeryTableFour r = new AfterSurgeryTableFour();
                r.setDate(date);
                r.setNumOfFormulationOne(Integer.parseInt(f[1].trim()));
                r.setNumOfFormulationTwo(Integer.parseInt(f[2].trim()));
                r.setNumOfFormulationThree(Integer.parseInt(f[3].trim()));
                r.setNumOfFormulationFour(Integer.parseInt(f[4].trim()));
                r.setNumOfFormulationFive(Integer.parseInt(f[5].trim()));
                r.setNumOfFormulationSix(Integer.parseInt(f[6].trim()));

                rows.add(r);
            }
        } catch (Exception e) {
            model.addAttribute("message", "Failed to read CSV: " + e.getMessage());
            return "uploadAfterSurgeryTableFour";
        }

        // Column count errors
        if (!badColumnLines.isEmpty()) {
            model.addAttribute("message",
                    "Error: some lines are missing columns (need 7). Problem lines: " + badColumnLines);
            return "uploadAfterSurgeryTableFour";
        }

        // Duplicates *within* the file
        if (!dupInFile.isEmpty()) {
            model.addAttribute("message",
                    "Error: duplicate dates found in the file: " + dupInFile);
            return "uploadAfterSurgeryTableFour";
        }

        // Conflicts with DB
        Set<LocalDate> dates = rows.stream().map(AfterSurgeryTableFour::getDate).collect(java.util.stream.Collectors.toSet());
        Set<LocalDate> exists = afterSurgeryTableFourRepository.findExistingDates(dates);
        if (!exists.isEmpty()) {
            model.addAttribute("message",
                    "Error: these dates already exist in the system: " + exists +
                            ". Please remove them or switch to 'update existing' mode.");
            return "uploadAfterSurgeryTableFour";
        }

        // All good → save
        afterSurgeryTableFourRepository.saveAll(rows);
        model.addAttribute("message", "Successfully uploaded " + rows.size() + " records.");
        return "uploadAfterSurgeryTableFour";
    }

}
