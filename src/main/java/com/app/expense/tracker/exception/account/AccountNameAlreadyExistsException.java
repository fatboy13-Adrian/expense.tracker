package com.app.expense.tracker.exception.account;

public class AccountNameAlreadyExistsException extends RuntimeException {
    public AccountNameAlreadyExistsException 
    (String name) {
        super(name + "already exists");
    }
}