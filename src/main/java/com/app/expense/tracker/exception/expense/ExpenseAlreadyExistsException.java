package com.app.expense.tracker.exception.expense;

public class ExpenseAlreadyExistsException extends RuntimeException {
    public ExpenseAlreadyExistsException 
    (String message) {
        super(message);
    }
}