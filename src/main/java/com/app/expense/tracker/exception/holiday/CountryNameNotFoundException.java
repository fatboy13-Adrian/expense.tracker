package com.app.expense.tracker.exception.holiday;

public class CountryNameNotFoundException extends RuntimeException {
    public CountryNameNotFoundException 
    (String name) {
        super(name + "do not exists");
    }
}