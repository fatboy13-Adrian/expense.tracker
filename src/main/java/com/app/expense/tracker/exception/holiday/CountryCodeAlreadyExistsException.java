package com.app.expense.tracker.exception.holiday;

public class CountryCodeAlreadyExistsException extends RuntimeException {
    public CountryCodeAlreadyExistsException 
    (String code) {
        super(code + " already exists");
    }
}