package com.app.expense.tracker.controller.monthly;
import com.app.expense.tracker.service.monthly.YearlyReportSvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayInputStream;
import java.time.Year;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YearlyReportControllerTest {
    @Mock
    private YearlyReportSvc svc;

    @InjectMocks
    private YearlyReportController controller;

    private Year year;
    private ByteArrayInputStream file;

    @BeforeEach
    void setUp () {
        year = 
        Year
        .of(2026);

        //Simulates generated Excel file content
        byte [] content = 
        "Test Excel Content"
        .getBytes();
        file = 
        new ByteArrayInputStream(content);
    }

    @Test
    void testExportYearlyRecords () {
        //Arrange
        when(svc
        .exportYearlyRecords(year))
        .thenReturn(file);

        //Act
        ResponseEntity <InputStreamResource> response =
        controller
        .exportYearlyRecords(year);

        //Assert
        assertEquals(HttpStatus
        .OK, 
        response
        .getStatusCode());
        assertNotNull(response
        .getBody());

        verify(svc)
        .exportYearlyRecords(year);
    }
}