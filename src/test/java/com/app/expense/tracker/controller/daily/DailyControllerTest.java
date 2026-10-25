package com.app.expense.tracker.controller.daily;
import com.app.expense.tracker.dto.DailyDTO;
import com.app.expense.tracker.service.daily.DailyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyControllerTest {
    @Mock
    private DailyService svc;

    @InjectMocks
    private DailyController controller;

    private DailyDTO dto;
    private LocalDate date;

    @BeforeEach
    void setUp () {
        date = 
        LocalDate
        .of(2026, 
        10, 
        1);

        dto = 
        new DailyDTO();
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
        ResponseEntity <DailyDTO> response = 
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
        ResponseEntity <DailyDTO> response = 
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
    void testRetrieveAll () {
        //Arrange
        Page<DailyDTO> expectedPage =
        new PageImpl <>(Collections
        .singletonList(dto));

        when(svc
        .retrieveAll(1, 
        10))
        .thenReturn(expectedPage);

        //Act
        ResponseEntity <Page <DailyDTO>> response =
        controller
        .retrieveAll(1, 
        10);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());
        assertEquals(1, 
        response
        .getBody()
        .getContent()
        .size());

        verify(svc)
        .retrieveAll(1, 
        10);
    }

    @Test
    void testDelete () {
        //Act
        ResponseEntity <Void> response = 
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