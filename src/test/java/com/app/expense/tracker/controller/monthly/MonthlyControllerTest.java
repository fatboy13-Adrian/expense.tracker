package com.app.expense.tracker.controller.monthly;
import com.app.expense.tracker.dto.MonthlyDTO;
import com.app.expense.tracker.service.monthly.MonthlyService;
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
import java.time.YearMonth;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MonthlyControllerTest {
    @Mock
    private MonthlyService svc;

    @InjectMocks
    private MonthlyController controller;

    private MonthlyDTO dto;
    private YearMonth month;

    @BeforeEach
    void setUp () {
        month = 
        YearMonth
        .of(2026,
        10);

        //Create DTO for testing
        dto = 
        new MonthlyDTO();
    }

    @Test
    void testRetrieve () {
        //Arrange
        when(svc
        .retrieve(month))
        .thenReturn(dto);

        //Act
        ResponseEntity <MonthlyDTO> response =
        controller
        .retrieve(month);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());

        verify(svc)
        .retrieve(month);
    }

    @Test
    void testRetrieveAll () {
        //Arrange
        Page <MonthlyDTO> expectedPage =
        new PageImpl <>(Collections
        .singletonList(dto));

        when(svc
        .retrieveAll(1, 
        10))
        .thenReturn(expectedPage);

        //Act
        ResponseEntity <Page<MonthlyDTO>> response =
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
}