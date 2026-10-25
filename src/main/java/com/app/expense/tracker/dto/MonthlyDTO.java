package com.app.expense.tracker.dto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.YearMonth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyDTO {
    private YearMonth month;
    private BigDecimal income = 
    BigDecimal.ZERO;
    private BigDecimal debt = 
    BigDecimal.ZERO;
    private BigDecimal emergency = 
    BigDecimal.ZERO;
    private BigDecimal investments = 
    BigDecimal.ZERO;
    private BigDecimal bills = 
    BigDecimal.ZERO;
    private BigDecimal food = 
    BigDecimal.ZERO;
    private BigDecimal groceries = 
    BigDecimal.ZERO;
    private BigDecimal haircut = 
    BigDecimal.ZERO;
    private BigDecimal household = 
    BigDecimal.ZERO;
    private BigDecimal medical = 
    BigDecimal.ZERO;
    private BigDecimal parents = 
    BigDecimal.ZERO;
    private BigDecimal transport = 
    BigDecimal.ZERO;
    private BigDecimal wants = 
    BigDecimal.ZERO;
    private BigDecimal others = 
    BigDecimal.ZERO;
    private BigDecimal savings = 
    BigDecimal.ZERO;
}