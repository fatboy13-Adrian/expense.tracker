package com.app.expense.tracker.exception.year;
import java.time.Year;

public class YearNotFoundException extends RuntimeException {
    public YearNotFoundException 
    (Year year) {
        super("Yearly record not found for: " 
        + year);
    }
}