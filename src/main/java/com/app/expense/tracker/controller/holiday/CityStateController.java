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
import com.app.expense.tracker.dto.holiday.CityStateDTO;
import com.app.expense.tracker.service.holiday.CityStateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cityState")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = 
"Country Info",
description = 
"Endpoints for managing city state info")
public class CityStateController {
    @Autowired
    private CityStateService svc;

    @PostMapping("/save")
    @Operation(summary = 
    "Add or update city state info")
    public ResponseEntity <CityStateDTO> 
    save(@RequestBody CityStateDTO dto) {
        return ResponseEntity
        .ok(svc
        .save(dto));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = 
    "Retrieve city state by id")
    public ResponseEntity <CityStateDTO> 
    retrieve (@PathVariable Long id) {
        return ResponseEntity
        .ok(svc
        .retrieve(id));
    }

    @GetMapping
    @Operation(summary = 
    "Retrieve a list of city states")
    public ResponseEntity <List <CityStateDTO>> 
    retrieveAll () {
        return ResponseEntity
        .ok(svc
        .retrieveAll());
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = 
    "Delete city state by id")
    public ResponseEntity <Void> 
    delete (@PathVariable Long id) {
        svc
        .delete(id);
        return ResponseEntity
        .noContent()
        .build();
    }
}