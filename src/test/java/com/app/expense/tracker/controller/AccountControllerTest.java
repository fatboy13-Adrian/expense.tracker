package com.app.expense.tracker.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.app.expense.tracker.dto.AccountDTO;
import com.app.expense.tracker.service.account.AccountService;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
    @Mock
    private AccountService svc;

    @InjectMocks
    private AccountController controller;

    private AccountDTO dto;
    private Long id;

    @BeforeEach
    void setUp () {
        id = 1L;
        dto = new AccountDTO();
        dto.setId(id);
    }

    @Test
    void testSave () {
        //Arrange
        when(svc
        .save(dto))
        .thenReturn(dto);

        //Act
        ResponseEntity <AccountDTO> response = 
        controller
        .save(dto);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());
        assertEquals(id, 
        response
        .getBody()
        .getId());

        verify(svc)
        .save(dto);
    }

    @Test
    void testRetrieve () {
        //Arrange
        when(svc
        .retrieve(id))
        .thenReturn(dto);

        //Act
        ResponseEntity <AccountDTO> response = 
        controller
        .retrieve(id);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());
        assertEquals(id, 
        response
        .getBody()
        .getId());

        verify(svc)
        .retrieve(id);
    }

    @Test
    void testRetrieveAll() {
        //Arrange
        List<AccountDTO> accounts = 
        List
        .of(dto,
        new AccountDTO());
        accounts
        .get(1)
        .setId(2L);
        when(svc
        .retrieveAll())
        .thenReturn(accounts);

        //Act
        ResponseEntity<List <AccountDTO>> response =
        controller
        .retrieveAll();

        //Assert
        assertEquals(HttpStatus
        .OK,
        response
        .getStatusCode());

        assertNotNull(response
        .getBody());

        assertEquals(2,
        response
        .getBody()
        .size());

        assertEquals(id,
        response
        .getBody()
        .get(0)
        .getId());

        assertEquals(2L,
        response
        .getBody()
        .get(1)
        .getId());

        verify(svc)
        .retrieveAll();
    }

    @Test
    void testDelete () {
        //Act
        ResponseEntity <Void> response = 
        controller
        .delete(id);

        //Assert
        assertEquals(HttpStatus
        .NO_CONTENT, 
        response
        .getStatusCode());
        assertNull(response
        .getBody());

        verify(svc)
        .delete(id);
    }
}