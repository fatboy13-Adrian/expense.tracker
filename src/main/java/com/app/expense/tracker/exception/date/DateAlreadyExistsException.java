package com.app.expense.tracker.exception.date;
import java.time.LocalDate;

public class DateAlreadyExistsException extends RuntimeException {
    public DateAlreadyExistsException 
    (LocalDate date) {
        super("Record already exists for date: " 
        + date);
    }
}