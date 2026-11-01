package com.app.expense.tracker.controller.holiday;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.app.expense.tracker.dto.holiday.ItenaryDTO;
import com.app.expense.tracker.service.holiday.ItenaryService;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItenaryControllerTest {
    @Mock
    private ItenaryService svc;

    @InjectMocks
    private ItenaryController controller;

    private ItenaryDTO dto;
    private LocalDate date;

    @BeforeEach
    void setUp () {
        date = 
        LocalDate
        .of(2026, 
        10, 
        1);

        dto = 
        new ItenaryDTO();
        dto
        .setDate(date);
    }

    @Test
    void testSave () {
        //Arrange
        when(svc
        .save(dto))
        .thenReturn(dto);

        //Act
        ResponseEntity <ItenaryDTO> 
        response = 
        controller
        .save(dto);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());
        assertEquals(date, 
        response
        .getBody()
        .getDate());

        verify(svc)
        .save(dto);
    }

    @Test
    void testRetrieve () {
        //Arrange
        when(svc
        .retrieve(date))
        .thenReturn(dto);

        //Act
        ResponseEntity <ItenaryDTO> 
        response = 
        controller
        .retrieve(date);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());
        assertEquals(date, 
        response
        .getBody()
        .getDate());

        verify(svc)
        .retrieve(date);
    }

    @Test
    void testRetrieveAll 
    () {
        //Arrange
        LocalDate secondDate = 
        LocalDate
        .of(2026, 
        10, 
        2);

        ItenaryDTO secondDto = 
        new ItenaryDTO();
        secondDto
        .setDate(secondDate);

        List <ItenaryDTO> 
        itenary = 
        List
        .of(dto, 
        secondDto);

        when(svc
        .retrieveAll())
        .thenReturn(itenary);

        //Act
        ResponseEntity <List <ItenaryDTO>> 
        response =
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
        assertEquals(date, 
        response
        .getBody()
        .get(0)
        .getDate());
        assertEquals(secondDate, 
        response
        .getBody()
        .get(1)
        .getDate());
        verify(svc)
        .retrieveAll();
    }

    @Test
    void testDelete () {
        //Act
        ResponseEntity <Void> 
        response = 
        controller
        .delete(date);

        //Assert
        assertEquals(HttpStatus
        .NO_CONTENT, 
        response
        .getStatusCode());
        assertNull(response
        .getBody());

        verify(svc)
        .delete(date);
    }
}