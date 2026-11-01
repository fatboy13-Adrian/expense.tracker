package com.app.expense.tracker.exception.holiday;

public class CityStateNotFoundException extends RuntimeException {
    public CityStateNotFoundException
    (Long id) {
        super(id + " not found");
    }
}