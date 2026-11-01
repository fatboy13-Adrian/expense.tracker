package com.app.expense.tracker.exception.holiday;

public class CountryNameAlreadyExistsException extends RuntimeException {
    public CountryNameAlreadyExistsException 
    (String name) {
        super(name + "already exists");
    }
}