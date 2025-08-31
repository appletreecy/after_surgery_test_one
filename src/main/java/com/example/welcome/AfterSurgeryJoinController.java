// src/main/java/com/example/welcome/AfterSurgeryJoinController.java
package com.example.welcome;

import com.example.welcome.dto.AfterSurgeryJoinDto;
import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryJoinRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class AfterSurgeryJoinController {

    @Autowired
    private AfterSurgeryJoinRepository joinRepository;

    @GetMapping("/afterSurgery/joined")
    public String showJoinedTable(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "date") String sort,
            @RequestParam(defaultValue = "DESC") Sort.Direction dir,
            Model model
    ) {
        // Defaults: last 30 days (inclusive), like TableOne
        LocalDate today = LocalDate.now();
        if (endDate == null) endDate = today;
        if (startDate == null) startDate = endDate.minusDays(29);

        if (startDate.isAfter(endDate)) {
            model.addAttribute("error", "Start date must be on or before end date. Showing last 30 days.");
            endDate = today;
            startDate = endDate.minusDays(29);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));
        var pageData = joinRepository.fetchJoinedData(startDate, endDate, pageable);

        model.addAttribute("page", pageData);
        model.addAttribute("joinedData", pageData.getContent());
        model.addAttribute("currentPage", pageData.getNumber());
        model.addAttribute("totalPages", pageData.getTotalPages());
        model.addAttribute("size", pageData.getSize());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir.name());

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "afterSurgeryJoinedTable"; // your Thymeleaf page
    }

    // ===== CSV export =====
    @GetMapping("/afterSurgery/joined/export/csv")
    public void exportJoinedCsv(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "date") String sort,
            @RequestParam(defaultValue = "DESC") Sort.Direction dir,
            HttpServletResponse response
    ) throws IOException {
        var range = normalizeRange(startDate, endDate);
        List<AfterSurgeryJoinDto> rows = fetchAll(range.start, range.end, sort, dir);

        String filename = "afterSurgery-joined-" + range.start + "_to_" + range.end + ".csv";
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename*=UTF-8''" + URLEncoder.encode(filename, StandardCharsets.UTF_8));

        var out = response.getWriter();

        // header
        out.println(String.join(",",
                "日期",
                "术后访视例数",
                "术后镇痛例数",
                "不良反应例数",
                "镇痛效果欠佳",
                "恶心呕吐",
                "头晕",
                "恶心呕吐头晕",
                "皮肤瘙痒",
                "过敏性皮疹",
                "麻醉恢复迟滞",
                "穿刺部位异常",
                "腹胀",
                "气插不适",
                "胃脘痛",
                "瞻望",
                "心胸不适",
                "止血带反应",
                "其他",
                "其他备注",
                "关节不良数",
                "运动不良数",
                "创伤不良数",
                "足踝不良数",
                "小儿不良数",
                "脊柱不良数",
                "手外不良数",
                "产科",
                "妇科",
                "配方一",
                "配方二",
                "配方三",
                "配方四",
                "配方五",
                "配方六"
        ));

        for (var r : rows) {
            out.println(String.join(",",
                    q(r.getDate().toString()),
                    n(r.getNumOfPostoperativeVisits()),
                    n(r.getNumOfPostoperativeAnalgesiaCases()),
                    n(r.getNumOfAdverseReactionCases()),
                    n(r.getNumOfInadequateAnalgesia()),
                    n(r.getNumOfNauseaAndVomiting()),
                    n(r.getNumOfDizziness()),
                    n(r.getNumOfNauseaAndVomitingAndDizziness()),
                    n(r.getNumOfItching()),
                    n(r.getNumOfAllergicRash()),
                    n(r.getNumOfProlongedAnestheticRecovery()),
                    n(r.getNumOfPunctureSiteAbnormality()),
                    n(r.getNumOfAbdominalDistension()),
                    n(r.getNumOfEndotrachealIntubationDiscomfort()),
                    n(r.getNumOfEpigastricPain()),
                    n(r.getNumOfDelirium()),
                    n(r.getNumOfChestDiscomfort()),
                    n(r.getNumOfTourniquetReaction()),
                    n(r.getNumOfOther()),
                    q(r.getOtherComments()),
                    n(r.getNumOfJointComplicationCount()),
                    n(r.getNumOfMotorDysfunctionCount()),
                    n(r.getNumOfTraumaComplicationCount()),
                    n(r.getNumOfAnkleComplicationCount()),
                    n(r.getNumOfPediatricAdverseEventCount()),
                    n(r.getNumOfSpinalComplicationCount()),
                    n(r.getNumOfHandSurgeryComplicationCount()),
                    n(r.getNumOfObstetricAdverseEventCount()),
                    n(r.getNumOfGynecologicalAdverseEventCount()),
                    n(r.getNumOfFormulationOne()),
                    n(r.getNumOfFormulationTwo()),
                    n(r.getNumOfFormulationThree()),
                    n(r.getNumOfFormulationFour()),
                    n(r.getNumOfFormulationFive()),
                    n(r.getNumOfFormulationSix())
            ));
        }
        out.flush();
    }

    // ===== Excel export (XLSX) =====
    @GetMapping("/afterSurgery/joined/export/xlsx")
    public void exportJoinedExcel(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "date") String sort,
            @RequestParam(defaultValue = "DESC") Sort.Direction dir,
            HttpServletResponse response
    ) throws IOException {
        var range = normalizeRange(startDate, endDate);
        List<AfterSurgeryJoinDto> rows = fetchAll(range.start, range.end, sort, dir);

        String filename = "afterSurgery-joined-" + range.start + "_to_" + range.end + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename*=UTF-8''" + URLEncoder.encode(filename, StandardCharsets.UTF_8));

        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet("Joined");
            int r = 0;

            // header
            String[] headers = {
                    "日期",
                    "术后访视例数",
                    "术后镇痛例数",
                    "不良反应例数",
                    "镇痛效果欠佳",
                    "恶心呕吐",
                    "头晕",
                    "恶心呕吐头晕",
                    "皮肤瘙痒",
                    "过敏性皮疹",
                    "麻醉恢复迟滞",
                    "穿刺部位异常",
                    "腹胀",
                    "气插不适",
                    "胃脘痛",
                    "瞻望",
                    "心胸不适",
                    "止血带反应",
                    "其他",
                    "其他备注",
                    "关节不良数",
                    "运动不良数",
                    "创伤不良数",
                    "足踝不良数",
                    "小儿不良数",
                    "脊柱不良数",
                    "手外不良数",
                    "产科",
                    "妇科",
                    "配方一",
                    "配方二",
                    "配方三",
                    "配方四",
                    "配方五",
                    "配方六"
            };
            Row hr = sheet.createRow(r++);
            for (int c = 0; c < headers.length; c++) {
                hr.createCell(c).setCellValue(headers[c]);
            }

            // rows
            for (var x : rows) {
                Row row = sheet.createRow(r++);
                int c = 0;
                row.createCell(c++).setCellValue(x.getDate().toString());
                row.createCell(c++).setCellValue(x.getNumOfPostoperativeVisits());
                row.createCell(c++).setCellValue(x.getNumOfPostoperativeAnalgesiaCases());
                row.createCell(c++).setCellValue(x.getNumOfAdverseReactionCases());
                row.createCell(c++).setCellValue(x.getNumOfInadequateAnalgesia());
                row.createCell(c++).setCellValue(x.getNumOfNauseaAndVomiting());
                row.createCell(c++).setCellValue(x.getNumOfDizziness());
                row.createCell(c++).setCellValue(x.getNumOfNauseaAndVomitingAndDizziness());
                row.createCell(c++).setCellValue(x.getNumOfItching());
                row.createCell(c++).setCellValue(x.getNumOfAllergicRash());
                row.createCell(c++).setCellValue(x.getNumOfProlongedAnestheticRecovery());
                row.createCell(c++).setCellValue(x.getNumOfPunctureSiteAbnormality());
                row.createCell(c++).setCellValue(x.getNumOfAbdominalDistension());
                row.createCell(c++).setCellValue(x.getNumOfEndotrachealIntubationDiscomfort());
                row.createCell(c++).setCellValue(x.getNumOfEpigastricPain());
                row.createCell(c++).setCellValue(x.getNumOfDelirium());
                row.createCell(c++).setCellValue(x.getNumOfChestDiscomfort());
                row.createCell(c++).setCellValue(x.getNumOfTourniquetReaction());
                row.createCell(c++).setCellValue(x.getNumOfOther());
                row.createCell(c++).setCellValue(safe(x.getOtherComments()));
                row.createCell(c++).setCellValue(x.getNumOfJointComplicationCount());
                row.createCell(c++).setCellValue(x.getNumOfMotorDysfunctionCount());
                row.createCell(c++).setCellValue(x.getNumOfTraumaComplicationCount());
                row.createCell(c++).setCellValue(x.getNumOfAnkleComplicationCount());
                row.createCell(c++).setCellValue(x.getNumOfPediatricAdverseEventCount());
                row.createCell(c++).setCellValue(x.getNumOfSpinalComplicationCount());
                row.createCell(c++).setCellValue(x.getNumOfHandSurgeryComplicationCount());
                row.createCell(c++).setCellValue(x.getNumOfObstetricAdverseEventCount());
                row.createCell(c++).setCellValue(x.getNumOfGynecologicalAdverseEventCount());
                row.createCell(c++).setCellValue(x.getNumOfFormulationOne());
                row.createCell(c++).setCellValue(x.getNumOfFormulationTwo());
                row.createCell(c++).setCellValue(x.getNumOfFormulationThree());
                row.createCell(c++).setCellValue(x.getNumOfFormulationFour());
                row.createCell(c++).setCellValue(x.getNumOfFormulationFive());
                row.createCell(c++).setCellValue(x.getNumOfFormulationSix());
            }

            for (int c = 0; c < headers.length; c++) sheet.autoSizeColumn(c);
            wb.write(response.getOutputStream());
        }
    }

    // ---- helpers ----

    private record Range(LocalDate start, LocalDate end) {}

    private Range normalizeRange(LocalDate startDate, LocalDate endDate) {
        LocalDate today = LocalDate.now();
        if (endDate == null) endDate = today;
        if (startDate == null) startDate = endDate.minusDays(29);
        if (startDate.isAfter(endDate)) {
            endDate = today;
            startDate = endDate.minusDays(29);
        }
        return new Range(startDate, endDate);
    }

    /** Fetch ALL rows for the range and sort by making a first call to learn the total. */
    private List<AfterSurgeryJoinDto> fetchAll(LocalDate start, LocalDate end, String sort, Sort.Direction dir) {
        // First call just to get total count
        Page<AfterSurgeryJoinDto> first = joinRepository.fetchJoinedData(
                start, end, PageRequest.of(0, 1, Sort.by(dir, sort)));
        int total = (int) first.getTotalElements();
        if (total <= 1) return first.getContent();

        Page<AfterSurgeryJoinDto> full = joinRepository.fetchJoinedData(
                start, end, PageRequest.of(0, Math.min(total, 100_000), Sort.by(dir, sort)));
        return full.getContent();
    }

    private static String n(Integer i) { return i == null ? "" : i.toString(); }

    // quote + escape CSV field (handles commas, quotes, newlines)
    private static String q(String s) {
        if (s == null) return "";
        String v = s.replace("\"", "\"\"");
        return "\"" + v + "\"";
    }

    private static String safe(String s) { return s == null ? "" : s; }
}



