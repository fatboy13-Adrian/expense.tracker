package com.app.expense.tracker.exception.account;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException 
    (Long id) {
        super(id + " already exists");
    }
}