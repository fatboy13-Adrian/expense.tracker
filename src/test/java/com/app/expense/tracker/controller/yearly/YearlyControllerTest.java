package com.app.expense.tracker.controller.yearly;
import com.app.expense.tracker.dto.YearlyDTO;
import com.app.expense.tracker.service.yearly.YearlyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    void testRetrieve_noRecordsFound () {
        //Arrange
        int page = 0;
        int size = 10;

        List<YearlyDTO> records = 
        List
        .of(
        new YearlyDTO());

        Page <YearlyDTO> expected = 
        new PageImpl<>(
        records,
        PageRequest
        .of(page, size),
        records
        .size());

        when(svc
        .retrieveAll(page, 
        size))
        .thenReturn(expected);

        //Act
        ResponseEntity <Page <YearlyDTO>> response =
        controller
        .retrieveAll(page, 
        size);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());

        Page <YearlyDTO> result = 
        response
        .getBody();

        assertNotNull(result);
        assertEquals(1, 
        result
        .getTotalElements());

        verify(svc)
        .retrieveAll(page, 
        size);
    }
}