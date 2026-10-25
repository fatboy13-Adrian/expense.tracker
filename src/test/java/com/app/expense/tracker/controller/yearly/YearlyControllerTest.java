package com.app.expense.tracker.controller.yearly;
import com.app.expense.tracker.dto.YearlyDTO;
import com.app.expense.tracker.service.yearly.YearlyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YearlyControllerTest {
    @Mock
    private YearlyService svc;

    @InjectMocks
    private YearlyController controller;

    private YearlyDTO dto;
    private int year;

    @BeforeEach
    void setUp () {
        year = 
        2026;

        //Create  DTO for testing
        dto = 
        new YearlyDTO();
    }

    @Test
    void testRetrieve () {
        //Arrange
        when(svc
        .retrieve(year))
        .thenReturn(dto);

        //Act
        ResponseEntity <YearlyDTO> response =
        controller
        .retrieve(year);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());

        verify(svc)
        .retrieve(year);
    }

    @Test
    void testRetrieveAll () {
        // Arrange
        List <YearlyDTO> expectedList =
        Arrays
        .asList(dto, 
        new YearlyDTO());

        when(svc
        .retrieveAll())
        .thenReturn(expectedList);

        //Act
        ResponseEntity <List <YearlyDTO>> response =
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

        verify(svc)
        .retrieveAll();
    }
}