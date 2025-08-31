package com.example.welcome;
import com.example.welcome.model.AfterSurgery;
import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("afterSurgeryTableOne")
public class AfterSurgeryTableOneController {

    @Autowired
    private AfterSurgeryTableOneRepository afterSurgeryTableOneRepository;

//    @GetMapping({"/", ""})
//    public String showTableOne(Model model) {
//
//        List<AfterSurgeryTableOne> records = afterSurgeryTableOneRepository.findAll();
//        int totalVisits = records.stream()
//                .filter(r -> r.getNumOfPostoperativeVisits() != null)
//                .mapToInt(AfterSurgeryTableOne::getNumOfPostoperativeVisits)
//                .sum();
//
//        int totalPostoperativeAnalgesia = records.stream()
//                .filter(r -> r.getNumOfPostoperativeAnalgesiaCases() != null)
//                .mapToInt(AfterSurgeryTableOne::getNumOfPostoperativeAnalgesiaCases)
//                .sum();
//
//        int totalAdverseReactions = records.stream()
//                .filter(r -> r.getNumOfAdverseReactionCases() != null)
//                .mapToInt(AfterSurgeryTableOne::getNumOfAdverseReactionCases)
//                .sum();
//
//        int totalInadequateAnalgesia = records.stream()
//                .filter(r -> r.getNumOfInadequateAnalgesia() != null)
//                .mapToInt(AfterSurgeryTableOne::getNumOfInadequateAnalgesia)
//                .sum();
//
//        float proportionOfTotalPostoperativeAnalgesiaCases = (float) totalPostoperativeAnalgesia / totalVisits;
//
//        float proportionOfTotalAdverseReactions = (float) totalAdverseReactions / totalPostoperativeAnalgesia;
//
//        float proportionOfTotalInadequateAnalgesia = 1 - ((float) totalInadequateAnalgesia / totalPostoperativeAnalgesia);
//
//
//        model.addAttribute("afterSurgeryTableOne", records);
//        model.addAttribute("totalVisits", totalVisits);
//        model.addAttribute("totalPostoperativeAnalgesia", totalPostoperativeAnalgesia);
//        model.addAttribute("totalAdverseReactions", totalAdverseReactions);
//        model.addAttribute("totalInadequateAnalgesia", totalInadequateAnalgesia);
//
//        model.addAttribute("proportionOfTotalPostoperativeAnalgesiaCases", proportionOfTotalPostoperativeAnalgesiaCases);
//        model.addAttribute("proportionOfTotalAdverseReactions", proportionOfTotalAdverseReactions);
//        model.addAttribute("proportionOfTotalInadequateAnalgesia", proportionOfTotalInadequateAnalgesia);
//
//
//        return "afterSurgeryTableOne";
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

        // Whitelist sort field (only "date" allowed)
        if (!"date".equalsIgnoreCase(sort)){
            sort = "date";
        }

        PageRequest pr = PageRequest.of(page, size, Sort.by(dir, sort));
        Page<AfterSurgeryTableOne> pageData =
                afterSurgeryTableOneRepository.findByDateBetween(startDate, endDate, pr);

        TableOneTotals totals =
                afterSurgeryTableOneRepository.computeTotalsInRange(startDate, endDate);

        long totalVisits = totals.totalVisits();
        long totalAnalgesia = totals.totalAnalgesia();
        long totalAdverse = totals.totalAdverse();
        long totalInadequate = totals.totalInadequate();

        float proportionAnalgesia = (totalVisits == 0) ? 0f : (float) totalAnalgesia / totalVisits;
        float proportionAdverse = (totalAnalgesia == 0) ? 0f : (float) totalAdverse / totalAnalgesia;
        float proportionInadequate = (totalAnalgesia == 0) ? 0f : 1 - ((float) totalInadequate / totalAnalgesia);

        model.addAttribute("page", pageData);
        model.addAttribute("content", pageData.getContent());
        model.addAttribute("currentPage", pageData.getNumber());
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("size", pageData.getSize());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir.name());  // "ASC" or "DESC"

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        model.addAttribute("totalVisits", totalVisits);
        model.addAttribute("totalPostoperativeAnalgesia", totalAnalgesia);
        model.addAttribute("totalAdverseReactions", totalAdverse);
        model.addAttribute("totalInadequateAnalgesia", totalInadequate);

        model.addAttribute("proportionOfTotalPostoperativeAnalgesiaCases", proportionAnalgesia);
        model.addAttribute("proportionOfTotalAdverseReactions", proportionAdverse);
        model.addAttribute("proportionOfTotalInadequateAnalgesia", proportionInadequate);

        return "afterSurgeryTableOne";
    }



    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("afterSurgeryTableOne", new AfterSurgeryTableOne());
        return "addAfterSurgeryTableOne";
    }

    @PostMapping("/add")
    public String submitForm(@ModelAttribute AfterSurgeryTableOne afterSurgeryTableOne) {
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

    // Add this to the controller
    @GetMapping("/upload")
    public String showUploadForm() {
        return "uploadAfterSurgeryTableOne";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        List<AfterSurgeryTableOne> records = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        if (file == null || file.isEmpty()) {
            model.addAttribute("message", "No file uploaded.");
            return "uploadAfterSurgeryTableOne";
        }

        int lineNo = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                lineNo++;

                // Skip header
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Skip blank/whitespace-only lines
                if (line == null || line.isBlank()) {
                    continue;
                }

                // Split with trimming around commas
                String[] rawFields = line.split("\\s*,\\s*", -1); // -1 keeps empty trailing fields

                // Ensure minimum columns
                if (rawFields.length < 5) {
                    errors.add("Line " + lineNo + ": expected 5 columns, found " + rawFields.length + ". Line skipped.");
                    continue;
                }

                // Defensively strip BOM from the very first character of the first field if present
                rawFields[0] = stripBom(rawFields[0]);

                try {
                    AfterSurgeryTableOne record = new AfterSurgeryTableOne();

                    // Expecting ISO date (YYYY-MM-DD). If your CSV uses another format, adjust the parser.
                    String dateStr = rawFields[0].trim();
                    if (dateStr.isEmpty()) {
                        throw new IllegalArgumentException("date is empty");
                    }
                    record.setDate(LocalDate.parse(dateStr));

                    // Numeric columns: treat empty as null
                    record.setNumOfPostoperativeVisits(parseNullableInt(rawFields[1]));
                    record.setNumOfPostoperativeAnalgesiaCases(parseNullableInt(rawFields[2]));
                    record.setNumOfAdverseReactionCases(parseNullableInt(rawFields[3]));
                    record.setNumOfInadequateAnalgesia(parseNullableInt(rawFields[4]));

                    records.add(record);
                } catch (Exception rowEx) {
                    errors.add("Line " + lineNo + ": " + rowEx.getMessage() + ". Raw: \"" + line + "\"");
                }
            }

            if (!records.isEmpty()) {
                afterSurgeryTableOneRepository.saveAll(records);
            }

            String summary = "Uploaded " + records.size() + " record(s)"
                    + (errors.isEmpty() ? "." : " with " + errors.size() + " warning(s).");
            model.addAttribute("message", summary);
            if (!errors.isEmpty()) {
                model.addAttribute("errors", errors);
            }

        } catch (IOException ex) {
            model.addAttribute("message", "Failed to read CSV: " + ex.getMessage());
        }

        return "uploadAfterSurgeryTableOne";
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

        List<AfterSurgeryTableOne> records = afterSurgeryTableOneRepository.findByDateBetween(start, endD);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_one.xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("术后表一");

            // Header row
            String[] headers = {"ID", "日期", "术后访视例数", "术后镇痛例数", "不良反应例数", "镇痛效果欠佳"};
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Data rows (null-safe)
            int rowIdx = 1;
            for (AfterSurgeryTableOne r : records) {
                Row row = sheet.createRow(rowIdx++);
                setString(row, 0, r.getId() == null ? "" : r.getId().toString());
                setString(row, 1, r.getDate() == null ? "" : r.getDate().toString());
                setNumber(row, 2, r.getNumOfPostoperativeVisits());
                setNumber(row, 3, r.getNumOfPostoperativeAnalgesiaCases());
                setNumber(row, 4, r.getNumOfAdverseReactionCases());
                setNumber(row, 5, r.getNumOfInadequateAnalgesia());
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

        List<AfterSurgeryTableOne> records = afterSurgeryTableOneRepository.findByDateBetween(start, endD);

        // Set CSV headers
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=after_surgery_table_one.csv");

        // Write CSV to response
        try (var writer = new java.io.PrintWriter(response.getOutputStream())) {
            // CSV Header
            writer.println("ID,日期,术后访视例数,术后镇痛例数,不良反应例数,镇痛效果欠佳");

            // CSV Rows
            for (AfterSurgeryTableOne record : records) {
                writer.printf(
                        "%s,%s,%s,%s,%s,%s%n",
                        record.getId() == null ? "" : record.getId(),
                        record.getDate() == null ? "" : record.getDate(),
                        record.getNumOfPostoperativeVisits() == null ? "" : record.getNumOfPostoperativeVisits(),
                        record.getNumOfPostoperativeAnalgesiaCases() == null ? "" : record.getNumOfPostoperativeAnalgesiaCases(),
                        record.getNumOfAdverseReactionCases() == null ? "" : record.getNumOfAdverseReactionCases(),
                        record.getNumOfInadequateAnalgesia() == null ? "" : record.getNumOfInadequateAnalgesia()
                );
            }
            writer.flush();
        }
    }




    /** Return null if s is empty/blank; otherwise parse an Integer (throws on non-numeric). */
    private Integer parseNullableInt(String s) {
        if (s == null) return null;
        String t = s.trim();
        if (t.isEmpty()) return null;
        return Integer.valueOf(t);
    }

    /** Strip a leading UTF-8 BOM if present. */
    private String stripBom(String s) {
        if (s != null && !s.isEmpty() && s.charAt(0) == '\uFEFF') {
            return s.substring(1);
        }
        return s;
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

