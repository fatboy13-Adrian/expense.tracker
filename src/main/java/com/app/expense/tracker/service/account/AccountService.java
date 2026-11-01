package com.app.expense.tracker.service.account;
import java.util.List;
import com.app.expense.tracker.dto.AccountDTO;

public interface AccountService {
    AccountDTO save 
    (AccountDTO dto);
    AccountDTO retrieve 
    (Long id);
    List <AccountDTO> retrieveAll 
    ();
    void delete 
    (Long id);
}