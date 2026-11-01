package com.app.expense.tracker.exception.holiday;

public class CountryCodeNotFoundException extends RuntimeException {
    public CountryCodeNotFoundException 
    (String code) {
        super(code + " not found");
    }
}