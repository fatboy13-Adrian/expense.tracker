package com.app.expense.tracker.mapper;
import org.springframework.stereotype.Component;
import com.app.expense.tracker.dto.AccountDTO;
import com.app.expense.tracker.entity.Account;

@Component
public class AccountMapper {
    public Account toEntity 
    (AccountDTO dto) {
        return Account
        .builder()
        .id(dto
        .getId())
        .name(dto
        .getName())
        .type(dto
        .getType())
        .account_number(dto
        .getAccount_number())
        .status(dto
        .getStatus())
        .amount(dto
        .getAmount())
        .build();
    }

    public AccountDTO toDTO 
    (Account a) {
        return new AccountDTO(
            a.getId(),
            a.getName(),
            a.getType(),
            a.getAccount_number(),
            a.getStatus(),
            a.getAmount()
        );
    }

    public void update 
    (Account a, 
    AccountDTO dto) {
        a.setName(dto
        .getName());
        a.setType(dto
        .getType());
        a.setAccount_number(dto
        .getAccount_number());
        a.setStatus(dto
        .getStatus());
        a.setAmount(dto
        .getAmount());
    }
}