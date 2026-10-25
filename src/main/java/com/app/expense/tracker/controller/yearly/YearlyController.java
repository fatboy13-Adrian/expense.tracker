package com.app.expense.tracker.controller.yearly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.tracker.dto.YearlyDTO;
import com.app.expense.tracker.service.yearly.YearlyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/yearly")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = "Yearly Expenses", 
description = 
"Endpoints for viewing yearly expenses")
public class YearlyController {
    @Autowired
    private YearlyService svc;

    @GetMapping("/year/{year}")
    @Operation(summary = 
    "Retrieve expense record by year")
    public ResponseEntity <YearlyDTO> retrieve 
    (@PathVariable int year) {
        return ResponseEntity
        .ok(svc
        .retrieve(year));
    }

    @GetMapping
    @Operation(summary = 
    "Retrieve a paginated list of year expenses")
    public ResponseEntity <Page <YearlyDTO>> retrieveAll
    (@RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
        .ok(svc
        .retrieveAll(page, 
        size));
    }
}