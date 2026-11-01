package com.app.expense.tracker.exception.holiday;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException
    (Long id) {
        super(id + " not found");
    }
}