package com.app.expense.tracker.exception.month;
import java.time.YearMonth;

public class MonthAlreadyExistsException extends RuntimeException {
    public MonthAlreadyExistsException 
    (YearMonth month) {
        super ("Monthly expense record already exists for " 
        + month);
    }
}