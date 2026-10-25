package com.app.expense.tracker.controller.monthly;
import java.io.ByteArrayInputStream;
import java.time.Year;
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
import com.app.expense.tracker.service.monthly.YearlyReportSvc;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("monthly")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = "Yearly Expenses",
description = 
"Download expense records for current or previous years")
public class YearlyReportController {
    @Autowired
    private YearlyReportSvc svc;

    @GetMapping("/export/{year}")
    public ResponseEntity<InputStreamResource> exportYearlyRecords 
    (@PathVariable 
    @DateTimeFormat(pattern = "yyyy") Year year) {
        /*
         * Calls service to generate record for selected year
         * The returned ByteArrayInputStreamcontains Excel file
         */
        ByteArrayInputStream file =
        svc
        .exportYearlyRecords(year);

        /*
         * Creates HTTP headers to control how browser
         * handles downloaded Excel file
         */
        HttpHeaders headers = 
        new HttpHeaders();
        headers
        .add(
        HttpHeaders
        .CONTENT_DISPOSITION,
        "attachment; filename=\"Expense Records for Year "
        + year 
        + ".xlsx\"");

        /*
         * Sets Content-Disposition header to attachment,
         * prompting browser to download file
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