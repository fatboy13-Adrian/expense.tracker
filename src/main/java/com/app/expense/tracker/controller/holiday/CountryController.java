package com.app.expense.tracker.controller.holiday;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.tracker.dto.holiday.CountryDTO;
import com.app.expense.tracker.service.holiday.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/country")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = 
"Country Info",
description = 
"Endpoints for managing country info")
public class CountryController {
    @Autowired
    private CountryService svc;

    @PostMapping("/save")
    @Operation(summary = 
    "Add or update country info")
    public ResponseEntity <CountryDTO> 
    save(@RequestBody CountryDTO dto) {
        return ResponseEntity
        .ok(svc
        .save(dto));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = 
    "Retrieve country by id")
    public ResponseEntity <CountryDTO> 
    retrieve (@PathVariable Long id) {
        return ResponseEntity
        .ok(svc
        .retrieve(id));
    }

    @GetMapping
    @Operation(summary = 
    "Retrieve a list of countries")
    public ResponseEntity <List <CountryDTO>> 
    retrieveAll () {
        return ResponseEntity
        .ok(svc
        .retrieveAll());
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = 
    "Delete country by id")
    public ResponseEntity <Void> 
    delete (@PathVariable Long id) {
        svc
        .delete(id);
        return ResponseEntity
        .noContent()
        .build();
    }
}