package com.app.expense.tracker.exception.account;

public class AccountNumberAlreadyExistsException extends RuntimeException {
    public AccountNumberAlreadyExistsException 
    (String account_number) {
        super(account_number + " already exists");
    }
}