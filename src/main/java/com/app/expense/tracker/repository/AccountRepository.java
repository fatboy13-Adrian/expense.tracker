package com.app.expense.tracker.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.expense.tracker.Enum.AccountStatus;
import com.app.expense.tracker.Enum.AccountType;
import com.app.expense.tracker.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository 
<Account, Long> {
    Optional <Account> findByName 
    (String name);
    List <Account> findByType 
    (AccountType type);
    List <Account> findByStatus 
    (AccountStatus status);
}