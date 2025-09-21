package com.example.welcome;
import com.example.welcome.dto.MonthlyTotalsTableFour;
import com.example.welcome.dto.MonthlyTotalsTableThree;
import com.example.welcome.dto.QuarterlyTotalsTableFour;
import com.example.welcome.model.AfterSurgeryTableFour;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.repository.AfterSurgeryTableFourRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Controller
@RequestMapping("afterSurgeryTableFour")
public class AfterSurgeryTableFourController {

    private static final int MIN_YEAR = 2015;
    private static final int MAX_YEAR = 2035;

    @Autowired
    private AfterSurgeryTableFourRepository afterSurgeryTableFourRepository;

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

        // TableFour
        Page<AfterSurgeryTableFour> pageDataTableThree =
                afterSurgeryTableFourRepository.findByDateBetween(startDate, endDate, pr);

        TableFourTotals totalsTableFour =
                afterSurgeryTableFourRepository.computeTotalsInRange(startDate, endDate);


        long totalAdverse = totalsTableOne.totalAdverse();

        long totalNumOfFormulationOne = totalsTableFour.totalNumOfFormulationOne();
        long totalNumOfFormulationTwo = totalsTableFour.totalNumOfFormulationTwo();
        long totalNumOfFormulationThree = totalsTableFour.totalNumOfFormulationThree();
        long totalNumOfFormulationFour = totalsTableFour.totalNumOfFormulationFour();
        long totalNumOfFormulationFive = totalsTableFour.totalNumOfFormulationFive();
        long totalNumOfFormulationSix = totalsTableFour.totalNumOfFormulationSix();

        long totalCasesTableFour = LongStream.of(
                totalNumOfFormulationOne,
                totalNumOfFormulationTwo,
                totalNumOfFormulationThree,
                totalNumOfFormulationFour,
                totalNumOfFormulationFive,
                totalNumOfFormulationSix
        ).sum();


        float proportionOfFormulationOne = (totalAdverse == 0) ? 0f : (float) totalNumOfFormulationOne / totalCasesTableFour;
        float proportionOfFormulationTwo = (totalAdverse == 0) ? 0f : (float) totalNumOfFormulationTwo / totalCasesTableFour;
        float proportionOfFormulationThree = (totalAdverse == 0) ? 0f : ((float) totalNumOfFormulationThree / totalCasesTableFour);
        float proportionOfFormulationFour = (totalAdverse == 0) ? 0f : (float) totalNumOfFormulationFour / totalCasesTableFour;
        float proportionOfFormulationFive = (totalAdverse == 0) ? 0f : (float) totalNumOfFormulationFive / totalCasesTableFour;
        float proportionOfFormulationSix = (totalAdverse == 0) ? 0f : ((float) totalNumOfFormulationSix / totalCasesTableFour);


        model.addAttribute("page", pageDataTableThree);
        model.addAttribute("content", pageDataTableThree.getContent());
        model.addAttribute("currentPage", pageDataTableThree.getNumber());
        model.addAttribute("totalPages", pageDataTableThree.getTotalPages());
        model.addAttribute("size", pageDataTableThree.getSize());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir.name());

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("totalCasesTableFour", totalCasesTableFour);

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

    @GetMapping("/export")
    public void exportToExcel(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpServletResponse response) throws IOException {

        LocalDate start = (startDate != null && !startDate.isEmpty())
                ? LocalDate.parse(startDate) : LocalDate.now().minusDays(30);
        LocalDate endD = (endDate != null && !endDate.isEmpty())
                ? LocalDate.parse(endDate) : LocalDate.now();

        List<AfterSurgeryTableFour> records = afterSurgeryTableFourRepository.findByDateBetween(start, endD);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_four.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("术后表四");

            // Header row
            String[] headers = {"ID", "日期", "配方一数量", "配方二数量", "配方三数量", "配方四数量","配方五数量", "配方六数量"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Data rows (null-safe)
            int rowIdx = 1;
            for (AfterSurgeryTableFour r : records) {
                Row row = sheet.createRow(rowIdx++);
                setString(row, 0, r.getId() == null ? "" : r.getId().toString());
                setString(row, 1, r.getDate() == null ? "" : r.getDate().toString());
                setNumber(row, 2, r.getNumOfFormulationOne());
                setNumber(row, 3, r.getNumOfFormulationTwo());
                setNumber(row, 4, r.getNumOfFormulationThree());
                setNumber(row, 5, r.getNumOfFormulationFour());
                setNumber(row, 6, r.getNumOfFormulationFive());
                setNumber(row, 7, r.getNumOfFormulationSix());
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

        List<AfterSurgeryTableFour> records = afterSurgeryTableFourRepository.findByDateBetween(start, endD);

        // Set CSV headers
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_four.csv");

        // Write CSV to response
        try (var writer = new java.io.PrintWriter(response.getOutputStream())) {
            // CSV Header
            writer.println("ID,日期,配方一数量,配方二数量,配方三数量,配方四数量,配方五数量,配方六数量");

            // CSV Rows
            for (AfterSurgeryTableFour record : records) {
                writer.printf(
                        "%s,%s,%s,%s,%s,%s,%s,%s%n",
                        record.getId() == null ? "" : record.getId(),
                        record.getDate() == null ? "" : record.getDate(),
                        record.getNumOfFormulationOne() == null ? "" : record.getNumOfFormulationOne(),
                        record.getNumOfFormulationTwo() == null ? "" : record.getNumOfFormulationTwo(),
                        record.getNumOfFormulationThree() == null ? "" : record.getNumOfFormulationThree(),
                        record.getNumOfFormulationFour() == null ? "" : record.getNumOfFormulationFour(),
                        record.getNumOfFormulationFive() == null ? "" : record.getNumOfFormulationFive(),
                        record.getNumOfFormulationSix() == null ? "" : record.getNumOfFormulationSix()
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
        List<MonthlyTotalsTableFour> raw = afterSurgeryTableFourRepository.computeMonthlyTotals(start, end);

        // Index by YearMonth for easy fill
        Map<YearMonth, MonthlyTotalsTableFour> byYm = raw.stream().collect(Collectors.toMap(
                mt -> YearMonth.of(mt.year(), mt.month()),
                Function.identity()
        ));

        // Build complete series:
        // - If selected year is current year: Jan..current month
        // - Else: Jan..Dec of that year
        List<MonthlyTotalsTableFour> series = new ArrayList<>();
        YearMonth cursor = YearMonth.of(selectedYear, 1);
        YearMonth last   = (selectedYear == currentYear)
                ? YearMonth.from(today)
                : YearMonth.of(selectedYear, 12);

        while (!cursor.isAfter(last)) {
            MonthlyTotalsTableFour mt = byYm.getOrDefault(
                    cursor,
                    new MonthlyTotalsTableFour(cursor.getYear(), cursor.getMonthValue(), 0L, 0L, 0L, 0L, 0L, 0L)
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
        return "afterSurgeryTableFourMonthlyTotals";
    }

    @GetMapping("/quarterly-totals")
    public String quarterlyTotals(
            @RequestParam(required = false) Integer year,
            Model model
    ) {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        // Pick selected year (default = current) and clamp
        int selectedYear = (year == null) ? currentYear : year;
        if (selectedYear < MIN_YEAR) selectedYear = MIN_YEAR;
        if (selectedYear > MAX_YEAR) selectedYear = MAX_YEAR;

        // Query window: full year, or YTD for current year
        LocalDate start = LocalDate.of(selectedYear, 1, 1);
        LocalDate end   = (selectedYear == currentYear) ? today : LocalDate.of(selectedYear, 12, 31);

        // Fetch aggregated rows (may skip quarters with no data)
        List<QuarterlyTotalsTableFour> raw =
                afterSurgeryTableFourRepository.computeQuarterlyTotals(start, end);

        // Build map keyed by quarter number with a MERGE function (guards duplicates)
        Map<Integer, QuarterlyTotalsTableFour> byQ = raw.stream().collect(
                Collectors.toMap(
                        QuarterlyTotalsTableFour::quarter,
                        Function.identity(),
                        (a, b) -> new QuarterlyTotalsTableFour(
                                a.year(), a.quarter(),
                                a.totalNumOfFormulationOne() + b.totalNumOfFormulationOne(),
                                a.totalNumOfFormulationTwo()  + b.totalNumOfFormulationTwo(),
                                a.totalNumOfFormulationThree()+ b.totalNumOfFormulationThree(),
                                a.totalNumOfFormulationFour() + b.totalNumOfFormulationFour(),
                                a.totalNumOfFormulationFive() + b.totalNumOfFormulationFive(),
                                a.totalNumOfFormulationSix()  + b.totalNumOfFormulationSix()
                        )
                )
        );

        // Last quarter to display
        int lastQuarter = (selectedYear == currentYear)
                ? ((today.getMonthValue() - 1) / 3) + 1
                : 4;

        // Fill Q1..lastQuarter, defaulting missing quarters to zeros
        List<QuarterlyTotalsTableFour> series = new ArrayList<>();
        for (int q = 1; q <= lastQuarter; q++) {
            QuarterlyTotalsTableFour qt = byQ.getOrDefault(
                    q,
                    new QuarterlyTotalsTableFour(selectedYear, q, 0L, 0L, 0L, 0L, 0L, 0L)
            );
            series.add(qt);
        }

        // Years dropdown
        List<Integer> years = IntStream.rangeClosed(MIN_YEAR, MAX_YEAR)
                .boxed()
                .collect(Collectors.toList());

        model.addAttribute("quarterlyTotals", series);
        model.addAttribute("year", selectedYear);
        model.addAttribute("years", years);

        return "afterSurgeryTableFourQuarterlyTotals";
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
