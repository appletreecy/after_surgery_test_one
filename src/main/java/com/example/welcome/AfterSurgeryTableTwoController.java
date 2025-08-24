package com.example.welcome;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.model.AfterSurgeryTableTwo;
import com.example.welcome.repository.AfterSurgeryTableTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("afterSurgeryTableTwo")
public class AfterSurgeryTableTwoController {

    @Autowired
    private AfterSurgeryTableTwoRepository afterSurgeryTableTwoRepository;

    @Autowired
    private AfterSurgeryTableOneRepository afterSurgeryTableOneRepository;


//    @GetMapping({"/", ""})
//    public String showTableOne(Model model){
//
//        List<AfterSurgeryTableTwo> tableTwoRecords = afterSurgeryTableTwoRepository.findAll();
//
//        List<AfterSurgeryTableOne> tableOneRecords = afterSurgeryRepositoryTableOne.findAll();
//
//        int totalNauseaAndVomiting = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfNauseaAndVomiting() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfNauseaAndVomiting)
//                .sum();
//
//        int totalDizziness = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfDizziness() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfDizziness)
//                .sum();
//
//        int totalNauseaAndVomitingAndDizziness = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfNauseaAndVomitingAndDizziness() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfNauseaAndVomitingAndDizziness)
//                .sum();
//
//        int totalAllergicRash = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfAllergicRash() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfAllergicRash)
//                .sum();
//
//        int totalProlongedAnestheticRecovery = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfProlongedAnestheticRecovery() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfProlongedAnestheticRecovery)
//                .sum();
//
//        int totalPunctureSiteAbnormality = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfPunctureSiteAbnormality() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfPunctureSiteAbnormality)
//                .sum();
//
//        int totalAbdominalDistension = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfAbdominalDistension() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfAbdominalDistension)
//                .sum();
//
//        int totalEndotrachealIntubationDiscomfort = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfEndotrachealIntubationDiscomfort() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfEndotrachealIntubationDiscomfort)
//                .sum();
//
//        int totalEpigastricPain = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfEpigastricPain() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfEpigastricPain)
//                .sum();
//
//        int totalDelirium = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfDelirium() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfDelirium)
//                .sum();
//
//        int totalChestDiscomfort = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfChestDiscomfort() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfChestDiscomfort)
//                .sum();
//
//        int totalTourniquetReaction = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfTourniquetReaction() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfTourniquetReaction)
//                .sum();
//
//        int totalOther = tableTwoRecords.stream()
//                .filter(r -> r.getNumOfOther() != null)
//                .mapToInt(AfterSurgeryTableTwo::getNumOfOther)
//                .sum();
//
//        // TableOne
//        int totalAdverseReactions = tableOneRecords.stream()
//                .filter(r -> r.getNumOfAdverseReactionCases() != null)
//                .mapToInt(AfterSurgeryTableOne::getNumOfAdverseReactionCases)
//                .sum();
//
//        float proportionOfNauseaAndVomiting = (float) totalNauseaAndVomiting / totalAdverseReactions;
//        float proportionOfDizziness = (float) totalDizziness / totalAdverseReactions;
//        float proportionOfNauseaAndVomitingAndDizziness = (float) totalNauseaAndVomitingAndDizziness / totalAdverseReactions;
//        float proportionOfAllergicRash = (float) totalAllergicRash / totalAdverseReactions;
//        float proportionOfProlongedAnestheticRecovery = (float) totalProlongedAnestheticRecovery / totalAdverseReactions;
//        float proportionOfPunctureSiteAbnormality = (float) totalPunctureSiteAbnormality / totalAdverseReactions;
//        float proportionOfAbdominalDistension = (float) totalAbdominalDistension / totalAdverseReactions;
//        float proportionOfEndotrachealIntubationDiscomfort = (float) totalEndotrachealIntubationDiscomfort / totalAdverseReactions;
//        float proportionOfEpigastricPain = (float) totalEpigastricPain / totalAdverseReactions;
//        float proportionOfDelirium = (float) totalDelirium / totalAdverseReactions;
//        float proportionOfChestDiscomfort = (float) totalChestDiscomfort / totalAdverseReactions;
//        float proportionOfTourniquetReaction = (float) totalTourniquetReaction / totalAdverseReactions;
//        float proportionOfOther = (float) totalOther / totalAdverseReactions;
//
//
//        model.addAttribute("tableTwoRecords", tableTwoRecords);
//
//        model.addAttribute("totalNauseaAndVomiting", totalNauseaAndVomiting);
//        model.addAttribute("totalDizziness", totalDizziness);
//        model.addAttribute("totalNauseaAndVomitingAndDizziness", totalNauseaAndVomitingAndDizziness);
//        model.addAttribute("totalAllergicRash", totalAllergicRash);
//        model.addAttribute("totalProlongedAnestheticRecovery", totalProlongedAnestheticRecovery);
//        model.addAttribute("totalPunctureSiteAbnormality", totalPunctureSiteAbnormality);
//        model.addAttribute("totalAbdominalDistension", totalAbdominalDistension);
//        model.addAttribute("totalEndotrachealIntubationDiscomfort", totalEndotrachealIntubationDiscomfort);
//        model.addAttribute("totalEpigastricPain", totalEpigastricPain);
//        model.addAttribute("totalDelirium", totalDelirium);
//        model.addAttribute("totalChestDiscomfort", totalChestDiscomfort);
//        model.addAttribute("totalTourniquetReaction", totalTourniquetReaction);
//        model.addAttribute("totalOther", totalOther);
//
//        model.addAttribute("proportionOfNauseaAndVomiting", proportionOfNauseaAndVomiting);
//        model.addAttribute("proportionOfDizziness", proportionOfDizziness);
//        model.addAttribute("proportionOfNauseaAndVomitingAndDizziness", proportionOfNauseaAndVomitingAndDizziness);
//        model.addAttribute("proportionOfAllergicRash", proportionOfAllergicRash);
//        model.addAttribute("proportionOfProlongedAnestheticRecovery", proportionOfProlongedAnestheticRecovery);
//        model.addAttribute("proportionOfPunctureSiteAbnormality", proportionOfPunctureSiteAbnormality);
//        model.addAttribute("proportionOfAbdominalDistension", proportionOfAbdominalDistension);
//        model.addAttribute("proportionOfEndotrachealIntubationDiscomfort", proportionOfEndotrachealIntubationDiscomfort);
//        model.addAttribute("proportionOfEpigastricPain", proportionOfEpigastricPain);
//        model.addAttribute("proportionOfDelirium", proportionOfDelirium);
//        model.addAttribute("proportionOfChestDiscomfort", proportionOfChestDiscomfort);
//        model.addAttribute("proportionOfTourniquetReaction", proportionOfTourniquetReaction);
//        model.addAttribute("proportionOfOther", proportionOfOther);
//
//
//
//        return "afterSurgeryTableTwo";
//    }

    @GetMapping({"", "/"})
    public String showTableOne(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "date") String sort,
            @RequestParam(defaultValue = "DESC") Sort.Direction dir,
            Model model
    ) {
        // Default to last 30 days (inclusive)
        LocalDate today = LocalDate.now();
        if (endDate == null) endDate = today;
        if (startDate == null) startDate = endDate.minusDays(29);

        // Guard rails
        if (startDate.isAfter(endDate)) {
            model.addAttribute("error", "Start date must be on or before end date. Showing last 30 days.");
            endDate = today;
            startDate = endDate.minusDays(29);
        }

        PageRequest pr = PageRequest.of(page, size, Sort.by(dir, sort));


        TableOneTotals totalsTableOne =
                afterSurgeryTableOneRepository.computeTotalsInRange(startDate, endDate);

        // TableThree
        Page<AfterSurgeryTableTwo> pageDataTableTwo =
                afterSurgeryTableTwoRepository.findByDateBetween(startDate, endDate, pr);

        TableTwoTotals totalsTableTwo =
                afterSurgeryTableTwoRepository.computeTotalsInRange(startDate, endDate);


        long totalAdverse = totalsTableOne.totalAdverse();

        long totalNumOfNauseaAndVomiting = totalsTableTwo.totalNumOfNauseaAndVomiting();
        long totalNumOfDizziness = totalsTableTwo.totalNumOfDizziness();
        long totalNumOfNauseaAndVomitingAndDizziness = totalsTableTwo.totalNumOfNauseaAndVomitingAndDizziness();
        long totalNumOfItching = totalsTableTwo.totalNumOfItching();
        long totalNumOfAllergicRash = totalsTableTwo.totalNumOfAllergicRash();
        long totalNumOfProlongedAnestheticRecovery = totalsTableTwo.totalNumOfProlongedAnestheticRecovery();
        long totalNumOfPunctureSiteAbnormality = totalsTableTwo.totalNumOfPunctureSiteAbnormality();
        long totalNumOfAbdominalDistension = totalsTableTwo.totalNumOfAbdominalDistension();
        long totalNumOfEndotrachealIntubationDiscomfort = totalsTableTwo.totalNumOfEndotrachealIntubationDiscomfort();
        long totalNumOfEpigastricPain = totalsTableTwo.totalNumOfEpigastricPain();
        long totalNumOfDelirium = totalsTableTwo.totalNumOfDelirium();
        long totalNumOfChestDiscomfort = totalsTableTwo.totalNumOfChestDiscomfort();
        long totalNumOfTourniquetReactionnumOfTourniquetReaction = totalsTableTwo.totalNumOfTourniquetReactionnumOfTourniquetReaction();
        long totalNumOfOther = totalsTableTwo.totalNumOfOther();


        float proportionOfNauseaAndVomiting = (totalAdverse == 0) ? 0f : (float) totalNumOfNauseaAndVomiting / totalAdverse;
        float proportionOfDizziness = (totalAdverse == 0) ? 0f : (float) totalNumOfDizziness / totalAdverse;
        float proportionOfNauseaAndVomitingAndDizziness = (totalAdverse == 0) ? 0f : ((float) totalNumOfNauseaAndVomitingAndDizziness / totalAdverse);
        float proportionOfAllergicRash = (totalAdverse == 0) ? 0f : (float) totalNumOfAllergicRash / totalAdverse;
        float proportionOfProlongedAnestheticRecovery = (totalAdverse == 0) ? 0f : (float) totalNumOfProlongedAnestheticRecovery / totalAdverse;
        float proportionOfPunctureSiteAbnormality = (totalAdverse == 0) ? 0f : ((float) totalNumOfPunctureSiteAbnormality / totalAdverse);
        float proportionOfAbdominalDistension = (totalAdverse == 0) ? 0f : ((float) totalNumOfAbdominalDistension / totalAdverse);
        float proportionOfEndotrachealIntubationDiscomfort = (totalAdverse == 0) ? 0f : (float) totalNumOfEndotrachealIntubationDiscomfort / totalAdverse;
        float proportionOfEpigastricPain = (totalAdverse == 0) ? 0f : (float) totalNumOfEpigastricPain / totalAdverse;
        float proportionOfDelirium = (totalAdverse == 0) ? 0f : ((float) totalNumOfDelirium / totalAdverse);
        float proportionOfChestDiscomfort = (totalAdverse == 0) ? 0f : (float) totalNumOfChestDiscomfort / totalAdverse;
        float proportionOfTourniquetReaction = (totalAdverse == 0) ? 0f : (float) totalNumOfTourniquetReactionnumOfTourniquetReaction / totalAdverse;
        float proportionOfOther = (totalAdverse == 0) ? 0f : ((float) totalNumOfOther / totalAdverse);



        model.addAttribute("page", pageDataTableTwo);
        model.addAttribute("content", pageDataTableTwo.getContent());
        model.addAttribute("currentPage", pageDataTableTwo.getNumber());
        model.addAttribute("totalPages", pageDataTableTwo.getTotalPages());
        model.addAttribute("size", pageDataTableTwo.getSize());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir.name());

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("totalAdverse", totalAdverse);

        model.addAttribute("totalNumOfNauseaAndVomiting", totalNumOfNauseaAndVomiting);
        model.addAttribute("totalNumOfDizziness", totalNumOfDizziness);
        model.addAttribute("totalNumOfNauseaAndVomitingAndDizziness", totalNumOfNauseaAndVomitingAndDizziness);
        model.addAttribute("totalNumOfItching", totalNumOfItching);
        model.addAttribute("totalNumOfAllergicRash", totalNumOfAllergicRash);
        model.addAttribute("totalNumOfProlongedAnestheticRecovery", totalNumOfProlongedAnestheticRecovery);
        model.addAttribute("totalNumOfPunctureSiteAbnormality", totalNumOfPunctureSiteAbnormality);
        model.addAttribute("totalNumOfAbdominalDistension", totalNumOfAbdominalDistension);
        model.addAttribute("totalNumOfEndotrachealIntubationDiscomfort", totalNumOfEndotrachealIntubationDiscomfort);
        model.addAttribute("totalNumOfEpigastricPain", totalNumOfEpigastricPain);
        model.addAttribute("totalNumOfDelirium", totalNumOfDelirium);
        model.addAttribute("totalNumOfChestDiscomfort", totalNumOfChestDiscomfort);
        model.addAttribute("totalNumOfTourniquetReactionnumOfTourniquetReaction", totalNumOfTourniquetReactionnumOfTourniquetReaction);
        model.addAttribute("totalNumOfOther", totalNumOfOther);

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

    // Add this to the controller
    @GetMapping("/upload")
    public String showUploadForm() {
        return "uploadAfterSurgeryTableTwo";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        List<AfterSurgeryTableTwo> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip header
                    continue;
                }
                String[] fields = line.split(",");

                AfterSurgeryTableTwo record = new AfterSurgeryTableTwo();
                record.setDate(LocalDate.parse(fields[0].trim()));

                record.setNumOfNauseaAndVomiting(parseInt(fields[1]));
                record.setNumOfDizziness(parseInt(fields[2]));
                record.setNumOfNauseaAndVomitingAndDizziness(parseInt(fields[3]));
                record.setNumOfItching(parseInt(fields[4]));
                record.setNumOfAllergicRash(parseInt(fields[5]));
                record.setNumOfProlongedAnestheticRecovery(parseInt(fields[6]));
                record.setNumOfPunctureSiteAbnormality(parseInt(fields[7]));
                record.setNumOfAbdominalDistension(parseInt(fields[8]));
                record.setNumOfEndotrachealIntubationDiscomfort(parseInt(fields[9]));
                record.setNumOfEpigastricPain(parseInt(fields[10]));
                record.setNumOfDelirium(parseInt(fields[11]));
                record.setNumOfChestDiscomfort(parseInt(fields[12]));
                record.setNumOfTourniquetReaction(parseInt(fields[13]));
                record.setNumOfOther(parseInt(fields[14]));
                record.setOtherComments(fields[15]);

                records.add(record);
            }

            afterSurgeryTableTwoRepository.saveAll(records);
            model.addAttribute("message", "Successfully uploaded " + records.size() + " records.");

        } catch (IOException | NumberFormatException | DateTimeException e) {
            model.addAttribute("message", "Failed to upload CSV: " + e.getMessage());
            e.printStackTrace();
        }

        return "uploadAfterSurgeryTableTwo";
    }
}


