package com.app.expense.tracker.exception.year;

public class YearAlreadyExistsException extends RuntimeException {
    public YearAlreadyExistsException 
    (int year) {
        super(year + " already exists");
    }
}