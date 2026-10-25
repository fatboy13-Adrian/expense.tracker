package com.app.expense.tracker.controller.yearly;
import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.tracker.service.yearly.AllYearsReportSvc;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/yearly")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = 
"Yearly Expenses", 
description = 
"Download yearly expense records for all years")
public class AllYearsReportController {
    @Autowired
    private AllYearsReportSvc svc;

    @GetMapping("/export")
    public ResponseEntity <InputStreamResource> 
    exportYearlyExpenseRecords () {
        /*
         * Generate Excel file from report service
         * & wrap it as an input stream resource
         */
        ByteArrayInputStream file = 
        svc
        .exportYearlyRecords();

        //Set download filename
        HttpHeaders headers = 
        new HttpHeaders();
        headers
        .add(HttpHeaders
        .CONTENT_DISPOSITION,
        "attachment; filename=\"Expense Records for All Years.xlsx\"");

        /*
         * Return Excel file as downloadable response
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