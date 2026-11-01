package com.app.expense.tracker.service.account;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app.expense.tracker.dto.AccountDTO;
import com.app.expense.tracker.entity.Account;
import com.app.expense.tracker.exception.account.AccountNotFoundException;
import com.app.expense.tracker.mapper.AccountMapper;
import com.app.expense.tracker.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    @Transactional
    public AccountDTO save 
    (AccountDTO dto) {
        Account a;
        if (dto
        .getId() 
        != null) { 
            a = 
            repository
            .findById(dto
            .getId())
            .orElseThrow(() -> 
            new AccountNotFoundException(dto
            .getId())); 
            mapper
            .update(a, 
            dto); 
        } else { 
            a = 
            mapper
            .toEntity(dto); 
        }

        Account saved = 
        repository
        .save(a);
        return mapper
        .toDTO(saved);
    }

    @Override
    public AccountDTO retrieve 
    (Long id) {
        Account a = 
        repository
        .findById(id)
        .orElseThrow(() -> 
        new AccountNotFoundException(id)); 
        return mapper
        .toDTO(a);
    }

    @Override
    public List <AccountDTO> retrieveAll 
    () {
        return repository
        .findAll()
        .stream()
        .map(mapper::toDTO)
        .toList();
    }

    @Override 
    @Transactional 
    public void delete 
    (Long id) { 
        if (!repository
        .existsById(id))
        throw new AccountNotFoundException(id); 
        repository
        .deleteById(id); 
    }
}