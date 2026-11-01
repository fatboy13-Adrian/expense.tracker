package com.app.expense.tracker.controller.holiday;
import java.time.LocalDate;
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
import com.app.expense.tracker.dto.holiday.ItenaryDTO;
import com.app.expense.tracker.service.holiday.ItenaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/itenary")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = 
"Itenary Info",
description = 
"Endpoints for managing itenary info")
public class ItenaryController {
    @Autowired
    private ItenaryService svc;

    @PostMapping("/save")
    @Operation(summary = 
    "Add or update itenary info")
    public ResponseEntity <ItenaryDTO> 
    save(@RequestBody ItenaryDTO dto) {
        return ResponseEntity
        .ok(svc
        .save(dto));
    }

    @GetMapping("/date/{date}")
    @Operation(summary = 
    "Retrieve itenary by id")
    public ResponseEntity <ItenaryDTO> 
    retrieve (@PathVariable 
    LocalDate date) {
        return ResponseEntity
        .ok(svc
        .retrieve(date));
    }

    @GetMapping
    @Operation(summary = 
    "Retrieve a list of itenary")
    public ResponseEntity <List <ItenaryDTO>> 
    retrieveAll () {
        return ResponseEntity
        .ok(svc
        .retrieveAll());
    }

    @DeleteMapping("/date/{date}")
    @Operation(summary = 
    "Delete itenary by date")
    public ResponseEntity <Void> 
    delete (@PathVariable LocalDate date) {
        svc
        .delete(date);
        return ResponseEntity
        .noContent()
        .build();
    }
}