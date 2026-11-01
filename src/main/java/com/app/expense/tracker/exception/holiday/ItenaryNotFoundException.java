package com.app.expense.tracker.exception.holiday;
import java.time.LocalDate;

public class ItenaryNotFoundException extends RuntimeException  {
    public ItenaryNotFoundException 
    (LocalDate date) {
        super("Record not found for date: " 
        + date);
    }
}