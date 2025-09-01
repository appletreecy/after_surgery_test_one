package com.example.welcome;
import com.example.welcome.model.AfterSurgery;
import com.example.welcome.model.AfterSurgeryTableFive;
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

import com.example.welcome.dto.MonthlyTotals;
import java.time.YearMonth;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Map;

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

}
