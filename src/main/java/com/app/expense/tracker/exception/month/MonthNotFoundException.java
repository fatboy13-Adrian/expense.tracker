package com.app.expense.tracker.exception.month;
import java.time.YearMonth;

public class MonthNotFoundException extends RuntimeException {
    public MonthNotFoundException 
    (YearMonth month) {
        super("Monthly expense record not found for: " 
        + month);
    }
}