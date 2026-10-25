package com.app.expense.tracker.exception;
import java.time.Year;

public class YearNotFoundException extends RuntimeException {
    public YearNotFoundException 
    (Year year) {
        super("Yearly expense record not found for: " 
        + year);
    }
}