package com.app.expense.tracker.exception.holiday;

public class CityStateAlreadyExistsException extends RuntimeException {
    public CityStateAlreadyExistsException
    (Long id) {
        super(id + " already exists");
    }
}