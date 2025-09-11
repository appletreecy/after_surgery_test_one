package com.example.welcome;
import com.example.welcome.dto.MonthlyTotals;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.repository.AfterSurgeryTableThreeRepository;
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
import java.util.stream.LongStream;

import com.example.welcome.dto.MonthlyTotalsTableThree;
import java.time.YearMonth;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

import java.util.stream.IntStream;


@Controller
@RequestMapping("afterSurgeryTableThree")
public class AfterSurgeryTableThreeController {

    private static final int MIN_YEAR = 2015;
    private static final int MAX_YEAR = 2035;

    @Autowired
    private AfterSurgeryTableThreeRepository afterSurgeryTableThreeRepository;

    @Autowired
    private AfterSurgeryTableOneRepository afterSurgeryTableOneRepository;


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
        Page<AfterSurgeryTableThree> pageDataTableThree =
                afterSurgeryTableThreeRepository.findByDateBetween(startDate, endDate, pr);

        TableThreeTotals totalsTableThree =
                afterSurgeryTableThreeRepository.computeTotalsInRange(startDate, endDate);


        long totalAdverse = totalsTableOne.totalAdverse();

        long totalNumOfJointComplicationCount = totalsTableThree.totalNumOfJointComplicationCount();
        long totalNumOfMotorDysfunctionCount = totalsTableThree.totalNumOfMotorDysfunctionCount();
        long totalNumOfTraumaComplicationCount = totalsTableThree.totalNumOfTraumaComplicationCount();
        long totalNumOfAnkleComplicationCount = totalsTableThree.totalNumOfAnkleComplicationCount();
        long totalNumOfPediatricAdverseEventCount = totalsTableThree.totalNumOfPediatricAdverseEventCount();
        long totalNumOfSpinalComplicationCount = totalsTableThree.totalNumOfSpinalComplicationCount();
        long totalNumOfHandSurgeryComplicationCount = totalsTableThree.totalNumOfHandSurgeryComplicationCount();

        long totalCasesTableThree = LongStream.of(
                totalNumOfJointComplicationCount,
                totalNumOfMotorDysfunctionCount,
                totalNumOfTraumaComplicationCount,
                totalNumOfAnkleComplicationCount,
                totalNumOfPediatricAdverseEventCount,
                totalNumOfSpinalComplicationCount,
                totalNumOfHandSurgeryComplicationCount
        ).sum();


        float proportionOfJointComplicationCount = (totalCasesTableThree == 0) ? 0f : (float) totalNumOfJointComplicationCount / totalCasesTableThree;
        float proportionOfMotorDysfunctionCount = (totalCasesTableThree == 0) ? 0f : (float) totalNumOfMotorDysfunctionCount / totalCasesTableThree;
        float proportionOfTraumaComplicationCount = (totalCasesTableThree == 0) ? 0f : ((float) totalNumOfTraumaComplicationCount / totalCasesTableThree);
        float proportionOfAnkleComplicationCount = (totalCasesTableThree == 0) ? 0f : (float) totalNumOfAnkleComplicationCount / totalCasesTableThree;
        float proportionOfPediatricAdverseEventCount = (totalCasesTableThree == 0) ? 0f : (float) totalNumOfPediatricAdverseEventCount / totalCasesTableThree;
        float proportionOfSpinalComplicationCount = (totalCasesTableThree == 0) ? 0f : ((float) totalNumOfSpinalComplicationCount / totalCasesTableThree);
        float proportionOfHandSurgeryComplicationCount = (totalCasesTableThree == 0) ? 0f : ((float) totalNumOfHandSurgeryComplicationCount / totalCasesTableThree);

        model.addAttribute("page", pageDataTableThree);
        model.addAttribute("content", pageDataTableThree.getContent());
        model.addAttribute("currentPage", pageDataTableThree.getNumber());
        model.addAttribute("totalPages", pageDataTableThree.getTotalPages());
        model.addAttribute("size", pageDataTableThree.getSize());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir.name());

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("totalCasesTableThree", totalCasesTableThree);

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
                if (f.length != 11) {
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
                r.setNumOfSurgicalTreatmentCount(Integer.parseInt(f[10].trim()));

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

    @GetMapping("/export")
    public void exportToExcel(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpServletResponse response) throws IOException {

        LocalDate start = (startDate != null && !startDate.isEmpty())
                ? LocalDate.parse(startDate) : LocalDate.now().minusDays(30);
        LocalDate endD = (endDate != null && !endDate.isEmpty())
                ? LocalDate.parse(endDate) : LocalDate.now();

        List<AfterSurgeryTableThree> records = afterSurgeryTableThreeRepository.findByDateBetween(start, endD);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_three.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("术后表三");

            // Header row
            String[] headers = {"ID", "日期", "关节不良数", "运动不良数", "创伤不良数", "足踝不良数",
                    "小儿不良数", "脊柱不良数", "手外不良数", "儿科不良数",
                    "妇科不良数", "外科数"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Data rows (null-safe)
            int rowIdx = 1;
            for (AfterSurgeryTableThree r : records) {
                Row row = sheet.createRow(rowIdx++);
                setString(row, 0, r.getId() == null ? "" : r.getId().toString());
                setString(row, 1, r.getDate() == null ? "" : r.getDate().toString());

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

        List<AfterSurgeryTableThree> records = afterSurgeryTableThreeRepository.findByDateBetween(start, endD);

        // Set CSV headers
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_three.csv");

        // Write CSV to response
        try (var writer = new java.io.PrintWriter(response.getOutputStream())) {
            // CSV Header
            writer.println("ID,日期,关节不良数,运动不良数,创伤不良数,足踝不良数,小儿不良数,脊柱不良数,手外不良数,儿科不良数,妇科不良数,外科数");

            // CSV Rows
            for (AfterSurgeryTableThree record : records) {
                writer.printf(
                        "%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        record.getId() == null ? "" : record.getId(),
                        record.getDate() == null ? "" : record.getDate(),
                        record.getNumOfJointComplicationCount() == null ? "" : record.getNumOfJointComplicationCount(),
                        record.getNumOfMotorDysfunctionCount() == null ? "" : record.getNumOfMotorDysfunctionCount(),
                        record.getNumOfTraumaComplicationCount() == null ? "" : record.getNumOfTraumaComplicationCount(),
                        record.getNumOfAnkleComplicationCount() == null ? "" : record.getNumOfAnkleComplicationCount(),
                        record.getNumOfPediatricAdverseEventCount() == null ? "" : record.getNumOfPediatricAdverseEventCount(),
                        record.getNumOfSpinalComplicationCount() == null ? "" : record.getNumOfSpinalComplicationCount(),
                        record.getNumOfHandSurgeryComplicationCount() == null ? "" : record.getNumOfHandSurgeryComplicationCount(),
                        record.getNumOfObstetricAdverseEventCount() == null ? "" : record.getNumOfObstetricAdverseEventCount(),
                        record.getNumOfGynecologicalAdverseEventCount() == null ? "" : record.getNumOfGynecologicalAdverseEventCount(),
                        record.getNumOfSurgicalTreatmentCount() == null ? "" : record.getNumOfSurgicalTreatmentCount()
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
        List<MonthlyTotalsTableThree> raw = afterSurgeryTableThreeRepository.computeMonthlyTotals(start, end);

        // Index by YearMonth for easy fill
        Map<YearMonth, MonthlyTotalsTableThree> byYm = raw.stream().collect(Collectors.toMap(
                mt -> YearMonth.of(mt.year(), mt.month()),
                Function.identity()
        ));

        // Build complete series:
        // - If selected year is current year: Jan..current month
        // - Else: Jan..Dec of that year
        List<MonthlyTotalsTableThree> series = new ArrayList<>();
        YearMonth cursor = YearMonth.of(selectedYear, 1);
        YearMonth last   = (selectedYear == currentYear)
                ? YearMonth.from(today)
                : YearMonth.of(selectedYear, 12);

        while (!cursor.isAfter(last)) {
            MonthlyTotalsTableThree mt = byYm.getOrDefault(
                    cursor,
                    new MonthlyTotalsTableThree(cursor.getYear(), cursor.getMonthValue(), 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L, 0L,0L)
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
        return "afterSurgeryTableThreeMonthlyTotals";
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

