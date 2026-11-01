package com.app.expense.tracker.dto;
import java.math.BigDecimal;
import com.app.expense.tracker.Enum.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String name;
    private AccountType type;
    private String account_number;
    private AccountStatus status;
    private BigDecimal amount = 
    BigDecimal.ZERO;
}
