package com.app.expense.tracker.exception.holiday;

public class CountryAlreadyExistsException extends RuntimeException {
    public CountryAlreadyExistsException 
    (Long id) {
        super(id + " already exists");
    }
}