package com.app.expense.tracker.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyDTO {
    private LocalDate date;
    private BigDecimal income = 
    BigDecimal.ZERO;
    private BigDecimal debt = 
    BigDecimal.ZERO;
    private BigDecimal emergency = 
    BigDecimal.ZERO;

    //Investments
    private BigDecimal srs = 
    BigDecimal.ZERO;
    private BigDecimal ssb = 
    BigDecimal.ZERO;

    //Bills
    private BigDecimal cdac = 
    BigDecimal.ZERO;
    private BigDecimal electricity = 
    BigDecimal.ZERO;
    private BigDecimal internet = 
    BigDecimal.ZERO;
    private BigDecimal insurances = 
    BigDecimal.ZERO;
    private BigDecimal mobile = 
    BigDecimal.ZERO;
    private BigDecimal mortgage = 
    BigDecimal.ZERO;
    private BigDecimal tax = 
    BigDecimal.ZERO;

    //Needs
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

    //Wants
    private BigDecimal eating_out = 
    BigDecimal.ZERO;
    private BigDecimal holiday = 
    BigDecimal.ZERO;
    private BigDecimal recreational = 
    BigDecimal.ZERO;
    private BigDecimal shopping = 
    BigDecimal.ZERO;
    private BigDecimal sports = 
    BigDecimal.ZERO;
    private BigDecimal tech = 
    BigDecimal.ZERO;

    //Others
    private BigDecimal tithes = 
    BigDecimal.ZERO;
    private BigDecimal wake = 
    BigDecimal.ZERO;
    private BigDecimal wedding = 
    BigDecimal.ZERO;
}