package com.example.welcome;
import com.example.welcome.dto.MonthlyTotalsTableFive;
import com.example.welcome.dto.MonthlyTotalsTableFour;
import com.example.welcome.model.AfterSurgery;
import com.example.welcome.model.AfterSurgeryTableFive;
import com.example.welcome.model.AfterSurgeryTableFour;
import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableFiveRepository;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

import static java.lang.Integer.parseInt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import com.example.welcome.dto.MonthlyTotals;
import java.time.YearMonth;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.util.stream.IntStream;


@Controller
@RequestMapping("afterSurgeryTableFive")
public class AfterSurgeryTableFiveController {

    private static final int MIN_YEAR = 2015;
    private static final int MAX_YEAR = 2035;


    @Autowired
    private AfterSurgeryTableFiveRepository afterSurgeryTableFiveRepository;


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

        // Whitelist sort field (only "date" allowed)
        if (!"date".equalsIgnoreCase(sort)) {
            sort = "date";
        }

        PageRequest pr = PageRequest.of(page, size, Sort.by(dir, sort));
        Page<AfterSurgeryTableFive> pageData =
                afterSurgeryTableFiveRepository.findByDateBetween(startDate, endDate, pr);

        TableFiveTotals totals =
                afterSurgeryTableFiveRepository.computeTotalsInRange(startDate, endDate);

        long totalNumberOfFollowUpsForCriticallyIllPatients = totals.totalNumberOfFollowUpsForCriticallyIllPatients();
        long totalNumberOfCirticalRescueCases = totals.totalNumberOfCirticalRescueCases();
        long totalNumberOfDeaths = totals.totalNumberOfDeaths();

        float proportionOfStableWithoutComplications = (totalNumberOfFollowUpsForCriticallyIllPatients == 0) ? 0f : (float) (totalNumberOfFollowUpsForCriticallyIllPatients - totalNumberOfCirticalRescueCases - totalNumberOfDeaths) / totalNumberOfFollowUpsForCriticallyIllPatients;
        float proportionOfCriticalRescueCases = (totalNumberOfFollowUpsForCriticallyIllPatients == 0) ? 0f : (float) (totalNumberOfCirticalRescueCases) / totalNumberOfFollowUpsForCriticallyIllPatients;
        float proportionOfDeaths = (totalNumberOfFollowUpsForCriticallyIllPatients == 0) ? 0f : (float) (totalNumberOfDeaths) / totalNumberOfFollowUpsForCriticallyIllPatients;

        model.addAttribute("page", pageData);
        model.addAttribute("content", pageData.getContent());
        model.addAttribute("currentPage", pageData.getNumber());
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("size", pageData.getSize());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir.name());  // "ASC" or "DESC"

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("totalNumberOfFollowUpsForCriticallyIllPatients", totalNumberOfFollowUpsForCriticallyIllPatients);
        model.addAttribute("totalNumberOfCirticalRescueCases", totalNumberOfCirticalRescueCases);
        model.addAttribute("totalNumberOfDeaths", totalNumberOfDeaths);

        model.addAttribute("proportionOfStableWithoutComplications", proportionOfStableWithoutComplications);
        model.addAttribute("proportionOfCriticalRescueCases", proportionOfCriticalRescueCases);
        model.addAttribute("proportionOfDeaths", proportionOfDeaths);

        return "afterSurgeryTableFive";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("afterSurgeryTableFive", new AfterSurgeryTableFive());
        return "addAfterSurgeryTableFive";
    }

    @PostMapping("/add")
    public String submitForm(@ModelAttribute AfterSurgeryTableFive afterSurgeryTableFive) {
        afterSurgeryTableFiveRepository.save(afterSurgeryTableFive);
        return "redirect:/afterSurgeryTableFive";
    }

    // delete
    @GetMapping("/delete")
    public String showDeleteForm() {
        return "deleteAfterSurgeryTableFive";
    }

    @PostMapping("/delete")
    public String deleteRecord(@RequestParam("id") Long id, Model model) {
        boolean exists = afterSurgeryTableFiveRepository.existsById(id);
        if (exists) {
            afterSurgeryTableFiveRepository.deleteById(id);
            model.addAttribute("message", "Record with ID " + id + " has been deleted.");
        } else {
            model.addAttribute("message", "No record found with ID " + id + ".");
        }
        return "deleteAfterSurgeryTableFive";
    }

    // Get: Show update form one
    @GetMapping("/editone")
    public String showEditFormOne() {
        return "editAfterSurgeryTableFiveOne";
    }

    // GET: Show update form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AfterSurgeryTableFive record = afterSurgeryTableFiveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));

        System.out.println("Loaded date:... " + record.getDate()); // 🔍 Check if null
        model.addAttribute("afterSurgeryTableFive", record);
        return "editAfterSurgeryTableFive";
    }

    // POST: Handle update form submission
    @PostMapping("/edit")
    public String updateAfterSurgery(@ModelAttribute AfterSurgeryTableFive record) {
        afterSurgeryTableFiveRepository.save(record);
        return "redirect:/afterSurgeryTableFive"; // Redirect to dashboard
    }

    // Add this to the controller
    @GetMapping("/upload")
    public String showUploadForm() {
        return "uploadAfterSurgeryTableFive";
    }

    @PostMapping("/upload")
    @Transactional // so either everything valid saves, or nothing
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        List<AfterSurgeryTableFive> rows = new ArrayList<>();
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
                if (f.length !=6) {
                    badColumnLines.add(lineNo);
                    continue;
                }

                LocalDate date = LocalDate.parse(f[0].trim());
                if (!seenInFile.add(date)) {
                    dupInFile.add(date);
                }

                AfterSurgeryTableFive r = new AfterSurgeryTableFive();
                r.setDate(date);
                r.setCriticalPatientsName(f[1].trim());
                r.setNumberOfFollowUpsForCriticallyIllPatients(Integer.parseInt(f[2].trim()));
                r.setVisitFindingsForCriticalPatient(f[3].trim());
                r.setNumberOfCriticalRescueCases(Integer.parseInt(f[4].trim()));
                r.setNumberOfDeaths(Integer.parseInt(f[5].trim()));

                rows.add(r);
            }
        } catch (Exception e) {
            model.addAttribute("message", "Failed to read CSV: " + e.getMessage());
            return "uploadAfterSurgeryTableFive";
        }

        // Column count errors
        if (!badColumnLines.isEmpty()) {
            model.addAttribute("message",
                    "Error: some lines are missing columns (need 6). Problem lines: " + badColumnLines);
            return "uploadAfterSurgeryTableFive";
        }

        // Duplicates *within* the file
        if (!dupInFile.isEmpty()) {
            model.addAttribute("message",
                    "Error: duplicate dates found in the file: " + dupInFile);
            return "uploadAfterSurgeryTableFive";
        }

        // Conflicts with DB
        Set<LocalDate> dates = rows.stream().map(AfterSurgeryTableFive::getDate).collect(java.util.stream.Collectors.toSet());
        Set<LocalDate> exists = afterSurgeryTableFiveRepository.findExistingDates(dates);
        if (!exists.isEmpty()) {
            model.addAttribute("message",
                    "Error: these dates already exist in the system: " + exists +
                            ". Please remove them or switch to 'update existing' mode.");
            return "uploadAfterSurgeryTableFive";
        }

        // All good → save
        afterSurgeryTableFiveRepository.saveAll(rows);
        model.addAttribute("message", "Successfully uploaded " + rows.size() + " records.");
        return "uploadAfterSurgeryTableFive";
    }

    // Export as Excel
    @GetMapping("/export")
    public void exportToExcel(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpServletResponse response) throws IOException {

        LocalDate start = (startDate != null && !startDate.isEmpty())
                ? LocalDate.parse(startDate) : LocalDate.now().minusDays(30);
        LocalDate endD = (endDate != null && !endDate.isEmpty())
                ? LocalDate.parse(endDate) : LocalDate.now();

        List<AfterSurgeryTableFive> records = afterSurgeryTableFiveRepository.findByDateBetween(start, endD);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_five.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("术后表五");

            // Header row
            String[] headers = {"ID", "日期", "危重病人姓名", "危重病人追访数", "危重病人访视结果", "转危重抢救例数", "死亡例数"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Data rows (null-safe)
            int rowIdx = 1;
            for (AfterSurgeryTableFive r : records) {
                Row row = sheet.createRow(rowIdx++);
                setString(row, 0, r.getId() == null ? "" : r.getId().toString());
                setString(row, 1, r.getDate() == null ? "" : r.getDate().toString());
                setString(row, 2, r.getCriticalPatientsName());
                setNumber(row, 3, r.getNumberOfFollowUpsForCriticallyIllPatients());
                setString(row, 4, r.getVisitFindingsForCriticalPatient());
                setNumber(row, 5, r.getNumberOfCriticalRescueCases());
                setNumber(row, 6, r.getNumberOfDeaths());
            }

            // Auto-size
            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);

            workbook.write(response.getOutputStream());
        }
    }

    // Export as CSV
    @GetMapping("/export/csv")
    public void exportToCsv(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpServletResponse response) throws IOException {

        LocalDate start = (startDate != null && !startDate.isEmpty())
                ? LocalDate.parse(startDate) : LocalDate.now().minusDays(30);
        LocalDate endD = (endDate != null && !endDate.isEmpty())
                ? LocalDate.parse(endDate) : LocalDate.now();

        List<AfterSurgeryTableFive> records = afterSurgeryTableFiveRepository.findByDateBetween(start, endD);

        // Set CSV headers
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_five.csv");

        // Write CSV to response
        try (var writer = new java.io.PrintWriter(response.getOutputStream())) {
            // CSV Header
            writer.println("ID,日期,危重病人姓名,危重病人追访数,危重病人访视结果,转危重抢救例数,死亡例数");

            // CSV Rows
            for (AfterSurgeryTableFive record : records) {
                writer.printf(
                        "%s,%s,%s,%s,%s%n",
                        record.getId() == null ? "" : record.getId(),
                        record.getDate() == null ? "" : record.getDate(),
                        record.getCriticalPatientsName() == null ? "" : record.getCriticalPatientsName(),
                        record.getNumberOfFollowUpsForCriticallyIllPatients() == null ? "" : record.getNumberOfFollowUpsForCriticallyIllPatients(),
                        record.getVisitFindingsForCriticalPatient() == null ? "" : record.getVisitFindingsForCriticalPatient(),
                        record.getNumberOfCriticalRescueCases() == null ? "" : record.getNumberOfCriticalRescueCases(),
                        record.getNumberOfDeaths() == null ? "" : record.getNumberOfDeaths()
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
        List<MonthlyTotalsTableFive> raw = afterSurgeryTableFiveRepository.computeMonthlyTotals(start, end);

        // Index by YearMonth for easy fill
        Map<YearMonth, MonthlyTotalsTableFive> byYm = raw.stream().collect(Collectors.toMap(
                mt -> YearMonth.of(mt.year(), mt.month()),
                Function.identity()
        ));

        // Build complete series:
        // - If selected year is current year: Jan..current month
        // - Else: Jan..Dec of that year
        List<MonthlyTotalsTableFive> series = new ArrayList<>();
        YearMonth cursor = YearMonth.of(selectedYear, 1);
        YearMonth last   = (selectedYear == currentYear)
                ? YearMonth.from(today)
                : YearMonth.of(selectedYear, 12);

        while (!cursor.isAfter(last)) {
            MonthlyTotalsTableFive mt = byYm.getOrDefault(
                    cursor,
                    new MonthlyTotalsTableFive(cursor.getYear(), cursor.getMonthValue(), 0L, 0L, 0L)
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
        return "afterSurgeryTableFiveMonthlyTotals";
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
