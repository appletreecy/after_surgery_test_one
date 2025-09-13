package com.example.welcome;
import com.example.welcome.dto.MonthlyTotalsTableThree;
import com.example.welcome.dto.MonthlyTotalsTableTwo;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.model.AfterSurgeryTableTwo;
import com.example.welcome.repository.AfterSurgeryTableTwoRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;

import java.time.YearMonth;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

@Controller
@RequestMapping("afterSurgeryTableTwo")
public class AfterSurgeryTableTwoController {

    private static final int MIN_YEAR = 2015;
    private static final int MAX_YEAR = 2035;

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

    @GetMapping("/export")
    public void exportToExcel(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpServletResponse response) throws IOException {

        LocalDate start = (startDate != null && !startDate.isEmpty())
                ? LocalDate.parse(startDate) : LocalDate.now().minusDays(30);
        LocalDate endD = (endDate != null && !endDate.isEmpty())
                ? LocalDate.parse(endDate) : LocalDate.now();

        List<AfterSurgeryTableTwo> records = afterSurgeryTableTwoRepository.findByDateBetween(start, endD);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_two.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("术后表二");

            // Header row
            String[] headers = {"ID", "日期", "恶心呕吐", "头晕", "恶心呕吐头晕", "皮肤瘙痒",
                    "过敏性皮疹", "麻醉恢复迟滞", "穿刺部位异常", "腹胀",
                    "气插不适", "胃脘痛", "瞻望", "心胸不适",
                    "止血带反应", "其他", "其他备注"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Data rows (null-safe)
            int rowIdx = 1;
            for (AfterSurgeryTableTwo r : records) {
                Row row = sheet.createRow(rowIdx++);
                setString(row, 0, r.getId() == null ? "" : r.getId().toString());
                setString(row, 1, r.getDate() == null ? "" : r.getDate().toString());
                setNumber(row, 2, r.getNumOfNauseaAndVomiting());
                setNumber(row, 3, r.getNumOfDizziness());
                setNumber(row, 4, r.getNumOfNauseaAndVomitingAndDizziness());
                setNumber(row, 5, r.getNumOfItching());
                setNumber(row, 6, r.getNumOfAllergicRash());
                setNumber(row, 7, r.getNumOfProlongedAnestheticRecovery());
                setNumber(row, 8, r.getNumOfPunctureSiteAbnormality());
                setNumber(row, 9, r.getNumOfAbdominalDistension());
                setNumber(row, 10, r.getNumOfEndotrachealIntubationDiscomfort());
                setNumber(row, 11, r.getNumOfEpigastricPain());
                setNumber(row, 12, r.getNumOfDelirium());
                setNumber(row, 13, r.getNumOfChestDiscomfort());
                setNumber(row, 14, r.getNumOfTourniquetReaction());
                setNumber(row, 15, r.getNumOfOther());
                setString(row, 16, r.getOtherComments());

            }

            // Auto-size
            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            workbook.write(response.getOutputStream());
        }
    }

    @GetMapping("/export/csv")
    public void exportToCsv(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpServletResponse response) throws IOException {

        LocalDate start = (startDate != null && !startDate.isEmpty())
                ? LocalDate.parse(startDate) : LocalDate.now().minusDays(30);
        LocalDate endD = (endDate != null && !endDate.isEmpty())
                ? LocalDate.parse(endDate) : LocalDate.now();

        List<AfterSurgeryTableTwo> records = afterSurgeryTableTwoRepository.findByDateBetween(start, endD);

        // Set CSV headers
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_two.csv");

        // Write CSV to response
        try (var writer = new java.io.PrintWriter(response.getOutputStream())) {
            // CSV Header
            writer.println("ID,日期,恶心呕吐,头晕,恶心呕吐头晕,皮肤瘙痒,过敏性皮疹,麻醉恢复迟滞,穿刺部位异常,腹胀,气插不适,胃脘痛,瞻望,心胸不适,止血带反应,其他,其他备注");

            // CSV Rows
            for (AfterSurgeryTableTwo record : records) {
                writer.printf(
                        "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%s,%s,%s,%s,%s,%s%n",
                        record.getId() == null ? "" : record.getId(),
                        record.getDate() == null ? "" : record.getDate(),
                        record.getNumOfNauseaAndVomiting() == null ? "" : record.getNumOfNauseaAndVomiting(),
                        record.getNumOfDizziness() == null ? "" : record.getNumOfDizziness(),
                        record.getNumOfNauseaAndVomitingAndDizziness() == null ? "" : record.getNumOfNauseaAndVomitingAndDizziness(),
                        record.getNumOfItching() == null ? "" : record.getNumOfItching(),
                        record.getNumOfAllergicRash() == null ? "" : record.getNumOfAllergicRash(),
                        record.getNumOfProlongedAnestheticRecovery() == null ? "" : record.getNumOfProlongedAnestheticRecovery(),
                        record.getNumOfPunctureSiteAbnormality() == null ? "" : record.getNumOfPunctureSiteAbnormality(),
                        record.getNumOfAbdominalDistension() == null ? "" : record.getNumOfAbdominalDistension(),
                        record.getNumOfEndotrachealIntubationDiscomfort() == null ? "" : record.getNumOfEndotrachealIntubationDiscomfort(),
                        record.getNumOfEpigastricPain() == null ? "" : record.getNumOfEpigastricPain(),
                        record.getNumOfDelirium() == null ? "" : record.getNumOfDelirium(),
                        record.getNumOfChestDiscomfort() == null ? "" : record.getNumOfChestDiscomfort(),
                        record.getNumOfTourniquetReaction() == null ? "" : record.getNumOfTourniquetReaction(),
                        record.getNumOfOther() == null ? "" : record.getNumOfOther(),
                        record.getOtherComments() == null ? "" : record.getOtherComments()
                );
            }
            writer.flush();
        }
    }

    @GetMapping("/monthly-totals")
    public String monthlyTotals(
            @RequestParam(required = false) Integer year,
            Model model
    ) {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        // Pick the selected year (default = current year) and clamp to [2015..2035]
        int selectedYear = (year == null) ? currentYear : year;
        if (selectedYear < MIN_YEAR) selectedYear = MIN_YEAR;
        if (selectedYear > MAX_YEAR) selectedYear = MAX_YEAR;

        // Build start/end for the query:
        // - If selected year is current year, end at "today" (YTD)
        // - Otherwise, show the whole year (Jan..Dec)
        LocalDate start = LocalDate.of(selectedYear, 1, 1);
        LocalDate end   = (selectedYear == currentYear)
                ? today
                : LocalDate.of(selectedYear, 12, 31);

        // Raw aggregated rows from DB (might skip months with no data)
        List<MonthlyTotalsTableTwo> raw = afterSurgeryTableTwoRepository.computeMonthlyTotals(start, end);

        // Index by YearMonth for easy fill
        Map<YearMonth, MonthlyTotalsTableTwo> byYm = raw.stream().collect(Collectors.toMap(
                mt -> YearMonth.of(mt.year(), mt.month()),
                Function.identity()
        ));

        // Build complete series:
        // - If selected year is current year: Jan..current month
        // - Else: Jan..Dec of that year
        List<MonthlyTotalsTableTwo> series = new ArrayList<>();
        YearMonth cursor = YearMonth.of(selectedYear, 1);
        YearMonth last   = (selectedYear == currentYear)
                ? YearMonth.from(today)
                : YearMonth.of(selectedYear, 12);

        while (!cursor.isAfter(last)) {
            MonthlyTotalsTableTwo mt = byYm.getOrDefault(
                    cursor,
                    new MonthlyTotalsTableTwo(cursor.getYear(), cursor.getMonthValue(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,0L, 0L, 0L, 0L, 0L)
            );
            series.add(mt);
            cursor = cursor.plusMonths(1);
        }

        // Years dropdown data
        List<Integer> years = IntStream.rangeClosed(MIN_YEAR, MAX_YEAR)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("monthlyTotals", series);
        model.addAttribute("year", selectedYear);   // used in title & selecting the dropdown
        model.addAttribute("years", years);         // for the <select> options
        return "afterSurgeryTableTwoMonthlyTotals";
    }

    // Helper: Write a string value into a cell safely
    private void setString(Row row, int idx, String val) {
        if (val == null) {
            row.createCell(idx).setCellValue("");
        } else {
            row.createCell(idx).setCellValue(val);
        }
    }

    // Helper: Write a numeric value into a cell safely
    private void setNumber(Row row, int idx, Integer val) {
        if (val == null) {
            row.createCell(idx).setBlank();
        } else {
            row.createCell(idx).setCellValue(val);
        }
    }
}


