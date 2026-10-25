package com.app.expense.tracker.exception;

public class ExportExcelFailedException extends RuntimeException {
	public ExportExcelFailedException 
    (String message, 
    Throwable cause) {
        super(message);
    }
}