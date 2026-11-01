package com.app.expense.tracker.exception.account;

public class AccountNameNotFoundException extends RuntimeException {
    public AccountNameNotFoundException 
    (String name) {
        super(name + "do not exists");
    }
}