package com.app.expense.tracker.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.app.expense.tracker.Enum.AccountStatus;
import com.app.expense.tracker.dto.AccountDTO;
import com.app.expense.tracker.entity.Account;
import com.app.expense.tracker.exception.account.AccountNotFoundException;
import com.app.expense.tracker.mapper.AccountMapper;
import com.app.expense.tracker.repository.AccountRepository;
import com.app.expense.tracker.service.account.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @InjectMocks
    private AccountServiceImpl svc;

    @Mock
    private AccountRepository repository;

    @Mock
    private AccountMapper mapper;

    private Account a;
    private Long id;
    private AccountDTO dto;

    @BeforeEach
    public void setUp 
    () {
        id = 1L;
        dto = 
        new AccountDTO();
        dto.setId(id);
        dto.setName("DBS");
        dto.setAccount_number("1234567890");
        dto.setStatus(AccountStatus.IN_USE);
        dto.setAmount(BigDecimal
        .valueOf(1119.99));
        a = new Account();
        a.setId(id);
        a.setName("DBS");
        a.setAccount_number("1234567890");
        a.setStatus(AccountStatus.IN_USE);
        a.setAmount(BigDecimal
        .valueOf(1119.99));
    }

    @Test
    void testSave 
    () {
        //Arrange
        when(repository
        .findById(id))
        .thenReturn(Optional
        .of(a));
        when(repository
        .save(a))
        .thenReturn(a);
        when(mapper
        .toDTO(a))
        .thenReturn(dto);

        //Act
        AccountDTO 
        result = 
        svc
        .save(dto);

        //Assert
        assertNotNull(result);
        assertEquals(id, 
        result
        .getId());
        verify(repository)
        .findById(id);
        verify(mapper)
        .update(a, 
        dto);
        verify(repository)
        .save(a);
        verify(mapper)
        .toDTO(a);
    }

    @Test
    void testSave_RecordAlreadyExists 
    () {
        //Arrange
        when(repository
        .findById(id))
        .thenReturn(Optional
        .of(a));
        when(repository
        .save(a))
        .thenReturn(a);
        when(mapper
        .toDTO(a))
        .thenReturn(dto);

        //Act
        AccountDTO result = 
        svc
        .save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(id, 
        result
        .getId());

        verify(repository)
        .findById(id);
        verify(mapper)
        .update(a, dto);
        verify(repository)
        .save(a);
        verify(mapper)
        .toDTO(a);
    }
    
    @Test
    void testRetrieve 
    () {
        //Mock: expense exists for given date
        when(repository
        .findById(id))
        .thenReturn(Optional
        .of(a));
        when(mapper
        .toDTO(a))
        .thenReturn(dto);
        AccountDTO result = 
        svc
        .retrieve(id);
        assertNotNull(result);
        assertEquals(dto
        .getId(), 
        result
        .getId());
        verify(repository, 
        times(1))
        .findById(id);
    }

    @Test
    void testRetrieveNotFound 
    () {
        //Mock: no expense found
        when(repository
        .findById(id))
        .thenReturn(Optional
        .empty());

        //Expect exception when not found
        AccountNotFoundException exception =
        assertThrows(AccountNotFoundException.class,
        () -> svc
        .retrieve(id));
        assertNotNull(exception);
        verify(repository, 
        times(1))
        .findById(id);
    }

    @Test
    void testRetrieveAll 
    () {
        //Arrange
        Account account1 = 
        new Account();
        account1
        .setId(1L);
        account1
        .setName("DBS");
        Account account2 = 
        new Account();
        account2
        .setId(2L);
        account2
        .setName("OCBC");
        AccountDTO dto1 = 
        new AccountDTO();
        dto1
        .setId(1L);
        dto1
        .setName("DBS");
        AccountDTO dto2 = 
        new AccountDTO();
        dto2
        .setId(2L);
        dto2
        .setName("OCBC");
        List <Account> 
        accounts = 
        List
        .of(account1, 
        account2);
        when(repository
        .findAll())
        .thenReturn(accounts);
        when(mapper
        .toDTO(account1))
        .thenReturn(dto1);
        when(mapper
        .toDTO(account2))
        .thenReturn(dto2);

        //Act
        List <AccountDTO> 
        result = 
        svc
        .retrieveAll();

        //Assert
        assertNotNull(result);
        assertEquals(2, 
        result
        .size());
        assertEquals(1L, 
        result
        .get(0)
        .getId());
        assertEquals("DBS", 
        result
        .get(0)
        .getName());
        assertEquals(2L, 
        result
        .get(1)
        .getId());
        assertEquals("OCBC", 
        result
        .get(1)
        .getName());
        verify(repository)
        .findAll();
        verify(mapper)
        .toDTO(account1);
        verify(mapper)
        .toDTO(account2);
    }

    @Test
    void testRetrieveAllWhenNoAccountsExist 
    () {
        //Arrange
        when(repository
        .findAll())
        .thenReturn(List
        .of());

        //Act
        List <AccountDTO> 
        result = 
        svc
        .retrieveAll();

        //Assert
        assertNotNull(result);
        assertTrue(result
        .isEmpty());

        verify(repository)
        .findAll();
        verifyNoInteractions(mapper);
    }

    @Test
    void testDelete 
    () {
        //Arrange
        when(repository
        .existsById(id))
        .thenReturn(true);

        //Act
        svc
        .delete(id);

        //Assert
        verify(repository, 
        times(1))
        .existsById(id);
        verify(repository, 
        times(1))
        .deleteById(id);
    }

    @Test
    void testDeleteNotFound 
    () {
        //Arrange
        Long missingId = 
        2L;
        when(repository
        .existsById(missingId))
        .thenReturn(false);

        //Act & Assert
        assertThrows(
        AccountNotFoundException.class,
        () -> svc
        .delete(missingId));
        verify(repository)
        .existsById(missingId);
        verify(repository, 
        never())
        .deleteById(missingId);
    }
}