package com.app.expense.tracker.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.expense.tracker.dto.AccountDTO;
import com.app.expense.tracker.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/account_info")
@CrossOrigin(origins = 
"http://localhost:3000")
@Tag(name = 
"Account Info",
description = 
"Endpoints for managing account info")
public class AccountController {
    @Autowired
    private AccountService svc;

    @PostMapping("/save")
    @Operation(summary = 
    "Add or update an account info")
    public ResponseEntity <AccountDTO> 
    save(@RequestBody AccountDTO dto) {
        return ResponseEntity
        .ok(svc
        .save(dto));
    }

    @GetMapping("/id/{id}")
    @Operation(summary = 
    "Retrieve account info by id")
    public ResponseEntity <AccountDTO> 
    retrieve (@PathVariable Long id) {
        return ResponseEntity
        .ok(svc
        .retrieve(id));
    }

    @GetMapping
    @Operation(summary = 
    "Retrieve a list of account info")
    public ResponseEntity <List <AccountDTO>> 
    retrieveAll () {
        return ResponseEntity
        .ok(svc
        .retrieveAll());
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = 
    "Delete an account info by id")
    public ResponseEntity <Void> 
    delete (@PathVariable Long id) {
        svc
        .delete(id);
        return ResponseEntity
        .noContent()
        .build();
    }
}