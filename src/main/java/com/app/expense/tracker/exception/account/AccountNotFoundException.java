package com.app.expense.tracker.exception.account;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException 
    (Long id) {
        super(id + " not found");
    }
}