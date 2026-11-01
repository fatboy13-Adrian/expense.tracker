package com.app.expense.tracker.exception.account;

public class AccountNumberNotFoundException extends RuntimeException {
    public AccountNumberNotFoundException 
    (String account_number) {
        super(account_number + " not found");
    }
}