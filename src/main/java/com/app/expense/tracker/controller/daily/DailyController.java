package com.app.expense.tracker.controller.daily;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.tracker.dto.DailyDTO;
import com.app.expense.tracker.service.daily.DailyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/daily")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = 
"Daily Expenses", 
description = 
"Endpoints for managing daily expenses")
public class DailyController {
    @Autowired	
    private DailyService svc;

    @PostMapping("/save")
    @Operation(summary = 
    "Add or update an expense record")
    public ResponseEntity <DailyDTO> save 
    (@RequestBody DailyDTO dto) {
        return ResponseEntity
        .ok(svc
        .save(dto));
    }

    @GetMapping("/date/{date}")
    @Operation(summary = 
    "Retrieve expense record by date")
    public ResponseEntity <DailyDTO> retrieve 
    (@PathVariable @DateTimeFormat
    (iso = DateTimeFormat
    .ISO.DATE) LocalDate date) {
        return ResponseEntity
        .ok(svc
        .retrieve(date));
    }

    @GetMapping
    @Operation(summary = 
    "Retrieve a paginated list of expense records")
    public ResponseEntity <Page <DailyDTO>> retrieveAll
    (@RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity
        .ok(svc
        .retrieveAll(page, size));
    }

    @DeleteMapping("/date/{date}")
    @Operation(summary = 
    "Delete an expense record by date")
    public ResponseEntity <Void> delete 
    (@PathVariable @DateTimeFormat(iso = 
    DateTimeFormat
    .ISO.DATE) LocalDate date) {
        svc
        .delete(date);
        return ResponseEntity
        .noContent()
        .build();
    }
}