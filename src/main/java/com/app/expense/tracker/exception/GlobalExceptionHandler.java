package com.app.expense.tracker.exception;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.app.expense.tracker.exception.account.AccountAlreadyExistsException;
import com.app.expense.tracker.exception.account.AccountNameAlreadyExistsException;
import com.app.expense.tracker.exception.account.AccountNameNotFoundException;
import com.app.expense.tracker.exception.account.AccountNotFoundException;
import com.app.expense.tracker.exception.account.AccountNumberAlreadyExistsException;
import com.app.expense.tracker.exception.account.AccountNumberNotFoundException;
import com.app.expense.tracker.exception.date.DateAlreadyExistsException;
import com.app.expense.tracker.exception.date.DateNotFoundException;
import com.app.expense.tracker.exception.holiday.CityStateAlreadyExistsException;
import com.app.expense.tracker.exception.holiday.CityStateNotFoundException;
import com.app.expense.tracker.exception.holiday.CountryAlreadyExistsException;
import com.app.expense.tracker.exception.holiday.CountryCodeAlreadyExistsException;
import com.app.expense.tracker.exception.holiday.CountryCodeNotFoundException;
import com.app.expense.tracker.exception.holiday.CountryNameAlreadyExistsException;
import com.app.expense.tracker.exception.holiday.CountryNameNotFoundException;
import com.app.expense.tracker.exception.holiday.CountryNotFoundException;
import com.app.expense.tracker.exception.holiday.ItenaryNotFoundException;
import com.app.expense.tracker.exception.expense.ExpenseAlreadyExistsException;
import com.app.expense.tracker.exception.expense.ExpenseNotFoundException;
import com.app.expense.tracker.exception.month.MonthAlreadyExistsException;
import com.app.expense.tracker.exception.month.MonthNotFoundException;
import com.app.expense.tracker.exception.year.YearAlreadyExistsException;
import com.app.expense.tracker.exception.year.YearNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler 
    (AccountAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleAccountAlreadyExists 
    (AccountAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler 
    (AccountNameAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleAccountNameAlreadyExists 
    (AccountNameAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (AccountNameNotFoundException.class)
    public ResponseEntity <Object> 
    handleAccountNameNotFound 
    (AccountNameNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (AccountNotFoundException.class)
    public ResponseEntity <Object> 
    handleAccountNotFound 
    (AccountNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (AccountNumberAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleAccountNumberAlreadyExists 
    (AccountNumberAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (AccountNumberNotFoundException.class)
    public ResponseEntity <Object> 
    handleAccountNumberNotFound 
    (AccountNumberNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (DateAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleDateAlreadyExists 
    (DateAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (DateNotFoundException.class)
    public ResponseEntity <Object> 
    handleDateNotFound 
    (DateNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (CityStateAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleCityStateAlreadyExists 
    (CityStateAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (CityStateNotFoundException.class)
    public ResponseEntity <Object> 
    handleCityStateNotFound 
    (CityStateNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (CountryAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleCountryAlreadyExists 
    (CountryAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (CountryCodeAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleCountryCodeAlreadyExists 
    (CountryCodeAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (CountryCodeNotFoundException.class)
    public ResponseEntity <Object> 
    handleCountryCodeNotFound 
    (CountryCodeNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (CountryNameAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleCountryNameAlreadyExists 
    (CountryNameAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (CountryNameNotFoundException.class)
    public ResponseEntity <Object> 
    handleCountryNameNotFound 
    (CountryNameNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (CountryNotFoundException.class)
    public ResponseEntity <Object> 
    handleCountryNotFound 
    (CountryNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (ItenaryNotFoundException.class)
    public ResponseEntity <Object> 
    handleItenaryNotFound 
    (ItenaryNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (ExpenseAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleExpenseAlreadyExists 
    (ExpenseAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (ExpenseNotFoundException.class)
    public ResponseEntity <Object> 
    handleExpenseNotFound 
    (ExpenseNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (MonthAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleMonthAlreadyExists 
    (MonthAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (MonthNotFoundException.class)
    public ResponseEntity <Object> 
    handleMonthNotFound 
    (MonthNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (YearAlreadyExistsException.class)
    public ResponseEntity <Object> 
    handleYearAlreadyExists 
    (YearAlreadyExistsException ex) {
        return buildResponse(HttpStatus
        .CONFLICT, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (YearNotFoundException.class)
    public ResponseEntity <Object> 
    handleYearNotFound 
    (YearNotFoundException ex) {
        return buildResponse(HttpStatus
        .NOT_FOUND, 
        ex
        .getMessage());
    }

    @ExceptionHandler
    (ExportExcelFailedException.class)
    public ResponseEntity <Object> 
    handleExportExcelFailed 
    (ExportExcelFailedException ex) {
        return buildResponse(HttpStatus
        .INTERNAL_SERVER_ERROR, 
        ex
        .getMessage());
    }


    @ExceptionHandler
    (Exception.class)
    public ResponseEntity <Object> 
    handleGenericException 
    (Exception ex) {
        return buildResponse(
        HttpStatus
        .INTERNAL_SERVER_ERROR,
        "Unexpected error: " 
        + ex
        .getMessage());
    }

    private ResponseEntity <Object> 
    buildResponse 
    (HttpStatus status, 
    String message) {
        Map <String, 
        Object> 
        body = 
        new HashMap<>();
        
        body
        .put("timestamp", 
        LocalDateTime
        .now());
        body
        .put("status", 
        status
        .value());
        body
        .put("error", 
        status
        .getReasonPhrase());
        body
        .put("message",
        message);

        return new ResponseEntity 
        <>(body, 
        status);
    }
}