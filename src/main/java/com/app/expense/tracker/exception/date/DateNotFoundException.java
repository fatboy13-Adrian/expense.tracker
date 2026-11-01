package com.app.expense.tracker.exception.date;
import java.time.LocalDate;

public class DateNotFoundException extends RuntimeException {
    public DateNotFoundException 
    (LocalDate date) {
        super("Record not found for date: " 
        + date);
    }
}