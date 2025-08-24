// src/main/java/com/example/welcome/AfterSurgeryJoinController.java
package com.example.welcome;

import com.example.welcome.dto.AfterSurgeryJoinDto;
import com.example.welcome.repository.AfterSurgeryJoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

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
}
