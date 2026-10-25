package com.app.expense.tracker.controller.daily;
import java.io.ByteArrayInputStream;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.tracker.service.daily.MonthlyReportSvc;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/daily")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = 
"Monthly Expenses",
description = 
"Download expense records for current or previous months")
public class MonthlyReportController {
    @Autowired
    private MonthlyReportSvc svc;

    @GetMapping("/export/{yearMonth}")
    public ResponseEntity<InputStreamResource> exportMonthlyRecords 
    (@PathVariable 
    @DateTimeFormat(pattern = "yyyy-MM") 
    YearMonth yearMonth) {
        /*
         * Calls service to generate record for selected month
         * The returned ByteArrayInputStreamcontains Excel file
         */
        ByteArrayInputStream file =
        svc
        .exportMonthlyRecords(yearMonth);

        //Converts "YYYY-MM" to "month & year in full (March 2025)"
        String monthYear = 
        yearMonth
        .getMonth()
        .getDisplayName(TextStyle
        .FULL, 
        Locale
        .ENGLISH)
        + " " 
        + yearMonth
        .getYear();

        /*
         * Creates HTTP headers to control how browser
         * handles downloaded Excel file
         */
        HttpHeaders headers = 
        new HttpHeaders();
        headers.add(
        HttpHeaders
        .CONTENT_DISPOSITION,
        "attachment; filename=\"Expense Records for "
        + monthYear 
        + ".xlsx\"");

        /*
         * Sets the Content-Disposition header to attachment,
         * prompting the browser to download the file
         */
        return ResponseEntity
        .ok()
        .headers(headers)
        .contentType(MediaType
        .parseMediaType(
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(new InputStreamResource(file));
    }
}